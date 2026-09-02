package model;

public class HostResult {

    private String ip;
    private String hostname;
    private String os;

    public HostResult(String ip, String hostname, String os) {
        this.ip = ip;
        this.hostname = hostname;
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public String getHostname() {
        return hostname;
    }

    public String getOs() {
        return os;
    }
}