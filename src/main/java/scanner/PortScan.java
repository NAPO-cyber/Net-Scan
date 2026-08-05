package scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScan {

    public void scanPorts(String ip, int startPort, int endPort) {
        System.out.println("\n Scanning ports on " + ip + "...");

        for (int port=startPort; port<=endPort; port++) {

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), 200);
                System.out.println("-----------------------");
                System.out.println("Port " + port + " OPEN");

            } catch (IOException ignored) {
                // closed port
            }
        }
        System.out.println("\nScanning of Ports completed...");
    }

    public void scanPort(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 200);
            System.out.println("-----------------------");
            System.out.println("Port " + port + " OPEN");

        } catch (IOException ignored) {
            // closed port
        }
        System.out.println("\nPort scan completed.");
    }
}
