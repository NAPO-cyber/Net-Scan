package scanner;

public class ScanResult {

    private String ip ;
    private int port;
    private String service;

    public ScanResult(String ip, int port, String service) {
        this.ip = ip;
        this.port = port;
        this.service = service;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getService() {
        return service;
    }
}
