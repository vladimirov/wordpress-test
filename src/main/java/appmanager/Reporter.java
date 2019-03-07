package appmanager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Reporter {

    public static String id = System.getProperty("id");
    public static String path = "pdfngreport/pdfngreport.properties";

    static void reportPropertiesCreation() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("src/main/resources/report.properties")), StandardCharsets.UTF_8);
        String reportName = "pdfreport.file.name=" + "report-" + id + "\r\n";

        Files.write(
                Paths.get(path),
                input.getBytes(),
                StandardOpenOption.CREATE);

        Files.write(
                Paths.get(path),
                reportName.getBytes(),
                StandardOpenOption.APPEND);

        System.out.println("REPORT PROPERTIES FILE CREATED");
        System.out.println();
    }


}
