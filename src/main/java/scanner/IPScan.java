package scanner;

import java.net.InetAddress;

public class IPScan {

    public void scanSingleIP(String ip) {
        System.out.println("Scanning... " + ip);

        if (isHostAlive(ip)) {
            System.out.println("Host is up.");
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

        String base = parts[0].substring(
                0,
                parts[0].lastIndexOf(".")
        );
        System.out.println("\nScanning subnet " + subnet + "...\n");

        for (int i = 1; i <= 254; i++) {
            String ip = base + "." + i;

            if (isHostAlive(ip)) {
                System.out.println("Host found: " + ip);
            }
        }
        System.out.println("\nSubnet scan completed.");
    }

    private boolean isHostAlive(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000);

        } catch (Exception e) {
            return false;
        }
    }
}