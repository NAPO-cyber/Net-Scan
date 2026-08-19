package scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScan {

    public void scanPorts(String ip, int startPort, int endPort) {
        System.out.println("\nScanning ports on " + ip + "...");

        int total = endPort - startPort + 1;
        int current = 0;

        for (int port = startPort; port <= endPort; port++) {
            current++;
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), 200);

                String service = ServiceDetector.detectService(port);
                System.out.printf("\nPort %-4d OPEN   %-15s%n", port, service);
            } catch (IOException ignored) {
                // port is closed
            }
            PortProgress.showProgress(current, total);
        }
        PortProgress.complete();
    }

    public void scanPort(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 200);

            String service = ServiceDetector.detectService(port);

            System.out.println("-----------------------");
            System.out.println("Port " + port + " OPEN");
            System.out.println("Service: " + service);

        } catch (IOException ignored) {
            System.out.println("Port " + port + " is closed.");
        }
        System.out.println("\nPort scan completed.");
    }
}