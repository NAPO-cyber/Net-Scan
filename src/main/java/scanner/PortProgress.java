package scanner;

public class PortProgress {

    public static void showProgress(int current, int total) {
        int percent = (current * 100) / total;
        System.out.print("\rProgress: " + percent + "% ");
    }

    public static void complete() {
//        System.out.println("\nProgress: 100% - ");
        System.out.println("\nScan complete.");
    }
}