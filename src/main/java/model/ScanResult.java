package model;

public class ScanResult {

    private int port;
    private String service;

    public ScanResult(int port, String service) {
        this.port = port;
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public String getService() {
        return service;
    }
}