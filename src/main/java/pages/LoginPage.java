package pages;

import appmanager.HelperBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends HelperBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By loginFormLocator = By.id("loginform");
    private By usernameLocator = By.name("log");
    private By passwordLocator = By.name("pwd");
    private By wpLoginButtonLocator = By.name("wp-submit");

    public void loginToAdmin(String username, String password) {
        waitToBePresent(loginFormLocator);
        click(loginFormLocator);
        type(usernameLocator, username);
        type(passwordLocator, password);
        click(wpLoginButtonLocator);
    }

    public void waitTillLoginPageLoading(){
        waitToBePresent(loginFormLocator);
    }

    public boolean loginFormIsDisplayed(){
        try{
            isElementPresent(loginFormLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
