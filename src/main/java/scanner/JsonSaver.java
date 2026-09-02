package scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ScanReport;
import model.ScanResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonSaver {

    public static void save(
            String ip,
            String hostname,
            String os,
            List<ScanResult> results) {

        ObjectMapper mapper = new ObjectMapper();

        ScanReport report = new ScanReport(
                ip,
                hostname,
                os,
                results
        );

        String fileName =
                "scan-results-" + System.currentTimeMillis() + ".json";

        try {

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), report);

            System.out.println(
                    "Results saved to: " + fileName
            );

        } catch (IOException e) {

            System.out.println("Could not save results.");
        }
    }
}