package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(By.name("log"), username);
        type(By.name("pwd"), password);
        click(By.name("wp-submit"));
    }
}
