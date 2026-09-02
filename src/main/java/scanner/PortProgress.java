package scanner;

public class PortProgress {

    public static void showProgress(int current, int total) {

        int percent = (current * 100) / total;

        System.out.print("\rScanning... " + percent + "%");

    }

    public static void complete() {
        System.out.println("\nScan complete.");
    }
}