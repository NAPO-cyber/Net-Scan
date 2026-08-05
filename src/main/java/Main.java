import scanner.IPScanner;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        IPScanner scanner = new IPScanner();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n========== NET SCAN ==========");
            System.out.println("1 Scan single IP");
            System.out.println("2 Scan subnet");
            System.out.println("3 Host discovery (ping scan)");
            System.out.println("4 port scan");
            System.out.println("5 Exit");

            System.out.print("\nenter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("enter ip: ");
                    String ip = sc.nextLine();
                    scanner.scanSingleIP(ip);
                    break;

                case 2:
                    System.out.print("enter subnet: ");
                    String subnet = sc.nextLine();
                    scanner.scanSubnet(subnet);
                    break;

                case 3:
                    System.out.print("enter ip: ");
                    ip = sc.nextLine();
                    if (scanner.isHostAlive(ip)) {
                        System.out.println("Host is up");
                    } else {
                        System.out.println("Host is down");
                    }
                    break;

                case 4:
                    System.out.print("enter ip: ");
                    ip = sc.nextLine();

                    System.out.print("start port: ");
                    int start = sc.nextInt();
                    sc.nextLine();

                    System.out.print("end port: ");
                    int end = sc.nextInt();
                    sc.nextLine();

                    scanner.scanPort(ip, start, end);
                    break;

                case 5:
                    System.out.println("exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("invalid choice");
            }
        }
    }
}
