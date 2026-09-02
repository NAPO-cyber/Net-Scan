package scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServiceDetector {
    public static String detectService(String ip, int port) {

        try (Socket socket = new Socket()) {

            socket.connect(new InetSocketAddress(ip, port), 500);

            socket.setSoTimeout(500);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String banner = reader.readLine();

            if (banner == null || banner.isEmpty()) {
                return detectByPort(port);
            }

            banner = banner.toLowerCase();

            if (banner.contains("ssh")) {
                return "SSH";
            }

            if (banner.contains("ftp")) {
                return "FTP";
            }

            if (banner.contains("smtp")) {
                return "SMTP";
            }

            if (banner.contains("mysql")) {
                return "MySQL";
            }

            return "Unknown";

        } catch (Exception e) {
            return detectByPort(port);
        }
    }


    private static String detectByPort(int port) {
        return switch (port) {
            case 20, 21 -> "FTP";
            case 22 -> "SSH";
            case 23 -> "Telnet";
            case 25 -> "SMTP";
            case 53 -> "DNS";
            case 80 -> "HTTP";
            case 110 -> "POP3";
            case 135 -> "RPC";
            case 143 -> "IMAP";
            case 443 -> "HTTPS";
            case 445 -> "SMB";
            case 3306 -> "MySQL";
            case 5432 -> "PostgreSQL";
            case 6379 -> "Redis";
            case 8080 -> "HTTP Proxy";
            default -> "Unknown";
        };
    }
}
