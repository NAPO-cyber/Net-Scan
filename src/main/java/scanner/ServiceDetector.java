package scanner;

public class ServiceDetector {
    public String detectService(int port) {

        return switch (port) {
            case 20, 21 -> "FTP";
            case 22 -> "SSH";
            case 23 -> "Telnet";
            case 25 -> "SMTP";
            case 53 -> "DNS";
            case 80 -> "HTTP";
            case 110 -> "POP3";
            case 135 -> "RPC";
            case 445 -> "SMB";
            case 143 -> "IMAP";
            case 443 -> "HTTPS";
            case 3306 -> "MySQL";
            case 5432 -> "PostgreSQL";
            case 6379 -> "Redis";
            case 8080 -> "HTTP Proxy";
            default -> "Unknown";
        };
    }
}
