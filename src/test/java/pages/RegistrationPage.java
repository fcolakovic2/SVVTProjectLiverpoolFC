package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private final WebDriver driver;

    private final By firstName = By.name("firstName");
    private final By lastName = By.name("lastName");
    private final By dateOfBirth = By.xpath("//label[text()='Date of Birth']/parent::div//input");
    private final By gender = By.xpath("//div[@id='gender']");
    private final By email = By.name("email");
    private final By country = By.xpath("//div[@data-testid='REGISTRATION_COUNTRY_DROPDOWN']");
    private final By password = By.name("password");
    private final By confirmPassword = By.name("confirmPassword");
    private final By submitRegistration = By.xpath("//button[@data-testid='REGISTRATION_SUBMIT_BUTTON']");
    private final By subscribeYes = By.xpath("//label[text()='Subscribe']/parent::div//span[text()='Yes']");
    private final By subscribeNo = By.xpath("//label[text()='Subscribe']/parent::div//span[text()='No']");

    public RegistrationPage(WebDriver driver) { this.driver = driver; }

}
