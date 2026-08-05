package scanner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IPScanner {

    // to scan single ip
    public void scanSingleIP(String ip) {
        System.out.println("Scanning... " + ip);

        if (isHostAlive(ip)) {
            System.out.println("host is up...");
        } else {
            System.out.println("host is down...");
        }
    }

    // scan a subnet
    public void scanSubnet(String subnet) {
        String[] parts = subnet.split("/");

        if (parts.length != 2) {
            System.out.println("Invalid subnet format");
            return;
        }
        int prefix = Integer.parseInt(parts[1]);

        if (prefix != 24) {
            System.out.println("Only /24 subnet is supported.");
            return;
        }

        String base = parts[0].substring(0, parts[0].lastIndexOf("."));
        System.out.println("\n Scanning subnet " + subnet + "...\n");

        for (int i=1; i<=254; i++) {
            String ip = base + "." + i;

            if (isHostAlive(ip)) {
                System.out.println("Host found: " + ip);
                return;
            }
        }
        System.out.println("\nSubnet scan completed.");
    }


    // to check is host is up
    public boolean isHostAlive(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000);

        } catch (Exception e) {
            return false;
        }
    }

    // to scan port
    public void scanPort(String ip, int startPort, int endPort) {
        System.out.println("\n Scanning ports on " + ip + "...");

        for (int port=startPort; port<=endPort; port++) {

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), 200);
                System.out.println("Port " + port + " -> OPEN");

            } catch (IOException ignored) {
                // closed port
            }
        }
        System.out.println("\nPort scan completed...");
    }
}
