package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By email = By.xpath("//input[@name='email']");
    private final By password = By.xpath("//input[@id='password']");
    private final By forgotPassword = By.xpath("//a[@data-testid='SIGN_IN_FORGOT_PASSWORD_BUTTON']");
    private final By signInBtn = By.xpath("//button[@data-testid='SIGN_IN_BUTTON']");
    private final By registerNowBtn = By.xpath("//a[@data-testid='SIGN_IN_CREATE_ACCOUNT_BUTTON_BOTTOM']");
    private final By redRegisterNowBtn = By.xpath("//a[@data-testid='SIGN_IN_CREATE_ACCOUNT_BUTTON_TOP']");
    private final By userPwNotRecognised = By.xpath("//span[text()='Username/password not recognised']");
    private final By resetPasswordPageText = By.xpath("//h1[text()='Reset your password']");
    public LoginPage(WebDriver driver) { this.driver = driver; }

    public void login(String user, String pass) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInBtn)).click();
    }

    public void choseRegistrationPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerNowBtn)).click();
    }

    public void clickForgetPassword(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPassword)).click();
    }

    //region Validation

    public void validateUserCredentialsAreWrong(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(userPwNotRecognised));
    }

    public void validateUserIsOnResetPasswordPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(resetPasswordPageText));
    }

    //endregion
}
