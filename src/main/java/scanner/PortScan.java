package scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScan {

    ServiceDetector service = new ServiceDetector();

    public void scanPorts(String ip, int startPort, int endPort) {
        System.out.println("\n Scanning ports on " + ip + "...");

        int total = endPort - startPort + 1;
        int current = 0;

        for (int port=startPort; port<=endPort; port++) {

            current++;

            try (Socket socket = new Socket()) {

                socket.connect(new InetSocketAddress(ip, port), 200);
                System.out.println("-----------------------");
//                System.out.println("Port " + port + " OPEN");

                if (socket.isConnected()) {
                    String s = service.detectService(port);
                    System.out.printf("Port %-5d OPEN   %-15s%n", port, s);
                }

            } catch (IOException ignored) {
            }
            PortProgress.showProgress(current, total);
        }
        PortProgress.complete();
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

// a helper class
class PortProgress {

    static void showProgress(int current, int total) {
        int percent = (current * 100) / total;
        System.out.print("\rProgress: " + percent + "%");
    }

    static void complete() {
        System.out.println("\nProgress: 100%");
        System.out.println("Scan complete.");
    }
}