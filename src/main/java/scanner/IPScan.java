package scanner;

import java.net.InetAddress;
import java.util.Scanner;

public class IPScan {

    public void scanSingleIP(String ip) {
        System.out.println("Scanning... " + ip);

        if (isHostAlive(ip)) {
            System.out.println("Host is up.");

            String hostname = getHostName(ip);
            System.out.println("Hostname: " + hostname);

            String os = detectOS(ip);
            System.out.println("OS: " + os);
        } else {
            System.out.println("Host is down.");
        }
    }

    public void scanSubnet(String subnet) {

        String[] parts = subnet.split("/");

        if (parts.length != 2) {
            System.out.println("Invalid subnet format.");
            return;
        }

        String baseIP = parts[0];
        int prefix;

        try {
            prefix = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid subnet prefix.");
            return;
        }

        if (prefix != 24) {
            System.out.println("Only /24 subnet is supported.");
            return;
        }

        String[] ipParts = baseIP.split("\\.");

        if (ipParts.length != 4) {
            System.out.println("Invalid IP address.");
            return;
        }

        String network = ipParts[0] + "."
                + ipParts[1] + "."
                + ipParts[2];

        System.out.println("\nScanning subnet " + subnet + "...\n");

        int found = 0;

        for (int i = 1; i <= 254; i++) {

            String ip = network + "." + i;

            if (isHostAlive(ip)) {

                found++;

                System.out.println("Host found: " + ip);

                String hostname = getHostName(ip);
                System.out.println("Hostname: " + hostname);
            }
        }

        System.out.println("\nSubnet scan completed.");
        System.out.println("Hosts found: " + found);
    }

    public String getHostName(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            String hostname = address.getHostName();

            if (hostname.equals(ip)) {
                return "Unknown";
            }

            return hostname;
        } catch (Exception e) {
            return "unknown";
        }
    }


    public String detectOS(String ip) {

        try {

            String osName = System.getProperty("os.name").toLowerCase();

            ProcessBuilder builder;

            if (osName.contains("win")) {
                builder = new ProcessBuilder("ping", "-n", "1", ip);
            } else {
                builder = new ProcessBuilder("ping", "-c", "1", ip);
            }

            Process process = builder.start();

            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.toUpperCase().contains("TTL=")) {

                    int start = line.toUpperCase().indexOf("TTL=") + 4;

                    String ttlText = line.substring(start)
                            .split("[^0-9]")[0];

                    int ttl = Integer.parseInt(ttlText);

                    scanner.close();

                    if (ttl <= 64) {
                        return "Linux/Unix (estimated)";
                    }

                    if (ttl <= 128) {
                        return "Windows (estimated)";
                    }

                    return "Network Device/Other (estimated)";
                }
            }

            scanner.close();

        } catch (Exception e) {
            return "Unknown";
        }

        return "Unknown";
    }


    private boolean isHostAlive(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHostAliveForGui(String ip) {
        return isHostAlive(ip);
    }
}