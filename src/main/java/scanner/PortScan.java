package scanner;

import model.ScanResult;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScan {


    public List<ScanResult> scanPorts(String ip, int startPort, int endPort) {

        List<ScanResult> results = new ArrayList<>();

        int total = endPort - startPort + 1;
        AtomicInteger completed = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<Future<ScanResult>> tasks = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {

            int currentPort = port;

            Future<ScanResult> task = executor.submit(() -> {

                try (Socket socket = new Socket()) {

                    socket.connect(
                            new InetSocketAddress(ip, currentPort),
                            200
                    );

                    String service =
                            ServiceDetector.detectService(ip, currentPort);

                    return new ScanResult(
                            currentPort,
                            service
                    );

                } catch (IOException e) {
                    return null;

                } finally {
                    int current = completed.incrementAndGet();
                    PortProgress.showProgress(current, total);
                }
            });

            tasks.add(task);
        }

        for (Future<ScanResult> task : tasks) {

            try {
                ScanResult result = task.get();

                if (result != null) {
                    results.add(result);
                }

            } catch (Exception e) {
                // ignore failed task
            }
        }

        executor.shutdown();

        PortProgress.complete();

        return results;
    }



    public void scanPort(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 200);

            int currPort = port;

            String service =
                    ServiceDetector.detectService(ip, currPort);

            System.out.println("-----------------------");
            System.out.println("Port " + port + " OPEN");
            System.out.println("Service: " + service);

        } catch (IOException ignored) {
            System.out.println("Port " + port + " is closed.");
        }
        System.out.println("\nPort scan completed.");
    }
}