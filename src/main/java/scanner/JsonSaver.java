package scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonSaver {

    public static void save(List<ScanResult> results) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("scan-results.json"), results);
            System.out.println("results saved...");
        } catch (IOException e) {
            System.out.println("could not save results.");
        }
    }
}
