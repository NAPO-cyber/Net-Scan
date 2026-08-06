import scanner.IPScan;
import scanner.PortScan;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IPScan scanner = new IPScan();
        PortScan portScan = new PortScan();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n========== NET SCAN ==========");
            System.out.println("1 Scan IP / Subnet");
            System.out.println("2 Port scan");
            System.out.println("3 Exit");

            System.out.print("\nenter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("""
                                          IP scan - 1
                                          Subnet scan - 2
                                          """);
                    System.out.print("\nSelect: ");
                    int select = sc.nextInt();
                    sc.nextLine();

                    if (select == 1) {
                        System.out.print("enter ip: ");
                        String ip = sc.nextLine();
                        scanner.scanSingleIP(ip);
                    } else if (select == 2) {
                        System.out.print("enter subnet: ");
                        String subnet = sc.nextLine();
                        scanner.scanSubnet(subnet);
                    } else {
                        System.out.println("invalid choice");
                    }
                    break;

                case 2:
                    System.out.print("""
                                          Single Port - 1
                                          Multiple Ports - 2
                                          """);
                    System.out.print("\nSelect: ");
                    select = sc.nextInt();
                    sc.nextLine();

                    if (select == 1) {
                        System.out.print("enter ip: ");
                        String ip = sc.nextLine();

                        System.out.print("enter port: ");
                        int port = sc.nextInt();
                        sc.nextLine();

                        portScan.scanPort(ip, port);

                    } else if (select == 2) {
                        System.out.print("enter ip: ");
                        String ip = sc.nextLine();

                        System.out.print("start port: ");
                        int start = sc.nextInt();
                        sc.nextLine();

                        System.out.print("end port: ");
                        int end = sc.nextInt();
                        sc.nextLine();

                        portScan.scanPorts(ip, start, end);

                    } else {
                        System.out.println("Invalid choice");
                    }
                    break;

                case 3:
                    System.out.println("exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("invalid choice");
            }
        }
    }
}
