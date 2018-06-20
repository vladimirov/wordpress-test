package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    public void loginToAdmin(String username, String password) {
        waitToBePresent(By.name("log"));
        type(By.name("log"), username);
        type(By.name("pwd"), password);
        click(By.name("wp-submit"));
    }

    public void loginToCrm(String username, String password) {
        type(By.name("log"), username);
        type(By.name("pwd"), password);
        click(By.cssSelector("button.btn.btn-danger.btn-block.btn-flat"));
    }


}
