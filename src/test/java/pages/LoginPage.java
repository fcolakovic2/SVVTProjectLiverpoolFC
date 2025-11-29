package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    private final By email = By.xpath("//input[@name='email']");
    private final By password = By.xpath("//input[@id='password']");
    private final By forgotPassword = By.xpath("//a[@data-testid='SIGN_IN_FORGOT_PASSWORD_BUTTON']");
    private final By signInBtn = By.xpath("//button[@data-testid='SIGN_IN_BUTTON']");
    private final By registerNowBtn = By.xpath("//a[@data-testid='SIGN_IN_CREATE_ACCOUNT_BUTTON_BOTTOM']");
    private final By redRegisterNowBtn = By.xpath("//a[@data-testid='SIGN_IN_CREATE_ACCOUNT_BUTTON_TOP']");
    public LoginPage(WebDriver driver) { this.driver = driver; }

    public void login(String user, String pass) {
        if (!driver.findElements(email).isEmpty()) driver.findElement(email).clear();
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(signInBtn).click();
    }

    public void choseRegistrationPage() {
        driver.findElement(redRegisterNowBtn).click();
    }

}
