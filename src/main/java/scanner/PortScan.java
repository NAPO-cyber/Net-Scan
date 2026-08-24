package scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScan {

    public String scanPorts(String ip, int startPort, int endPort) {

        StringBuilder output = new StringBuilder();
        output.append("Scanning ").append(ip).append("\n\n");

        for (int port = startPort; port <= endPort; port++) {

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), 200);

                String service = ServiceDetector.detectService(port);

                output.append("Port ")
                        .append(port)
                        .append(" OPEN - ")
                        .append(service)
                        .append("\n");

            } catch (IOException ignored) {
                // port is closed
            }
        }
        output.append("\nScan Complete.");
        return output.toString();
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