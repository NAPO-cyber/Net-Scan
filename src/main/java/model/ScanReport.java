package model;

import java.util.List;

public class ScanReport {

    private String target;
    private String hostname;
    private String os;
    private List<ScanResult> results;

    public ScanReport(String target, String hostname,
                      String os, List<ScanResult> results) {

        this.target = target;
        this.hostname = hostname;
        this.os = os;
        this.results = results;
    }

    public String getTarget() {
        return target;
    }

    public String getHostname() {
        return hostname;
    }

    public String getOs() {
        return os;
    }

    public List<ScanResult> getResults() {
        return results;
    }
}