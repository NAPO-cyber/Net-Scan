import gui.ScannerGui;
import model.ScanResult;
import scanner.IPScan;
import scanner.JsonSaver;
import scanner.PortScan;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        IPScan ipScan = new IPScan();
        PortScan portScan = new PortScan();

        while (true) {

            System.out.println("\n===== Network Scanner =====");
            System.out.println("1. Scan Single IP");
            System.out.println("2. Scan Subnet");
            System.out.println("3. Scan Single Port");
            System.out.println("4. Scan Port Range");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            System.out.print("\nenter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter IP: ");
                    String ip = sc.nextLine();

                    ipScan.scanSingleIP(ip);
                    break;

                case 2:
                    System.out.print("\nEnter subnet (example: 192.168.1.0/24): ");
                    String subnet = sc.nextLine();

                    ipScan.scanSubnet(subnet);
                    break;

                case 3:
                    System.out.print("\nEnter IP: ");
                    String portIP = sc.nextLine();

                    System.out.print("Enter port: ");
                    int port = sc.nextInt();

                    portScan.scanPort(portIP, port);
                    break;

                case 4:
                    System.out.print("\nEnter IP: ");
                    String targetIP = sc.nextLine();

                    System.out.print("Start port: ");
                    int startPort = sc.nextInt();

                    System.out.print("End port: ");
                    int endPort = sc.nextInt();

                    List<ScanResult> results = portScan.scanPorts(targetIP, startPort, endPort);

                    for (ScanResult result: results) {
                        System.out.println(
                                "Port " + result.getPort()
                                + " OPEN - "
                                + result.getService()
                        );
                    }

                    ipScan = new IPScan();

                    String hostname = ipScan.getHostName(targetIP);
                    String os = ipScan.detectOS(targetIP);

                    JsonSaver.save(
                            targetIP,
                            hostname,
                            os,
                            results
                    );
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("invalid choice");
            }
        }
    }
}
