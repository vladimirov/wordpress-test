package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PageSpeedHelper extends HelperBase{

    private final Properties properties;

    public PageSpeedHelper(WebDriver driver) {
        super(driver);
        properties = new Properties();
    }

    public void enterPageUrlToPageSpeed() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));

        type(By.name("url"), properties.getProperty("web.baseUrl"));
        System.out.println(properties.getProperty("web.baseUrl"));

    }

    public void analyzeButtonClick() {
        click(By.className("analyze-cell"));

    }




}
