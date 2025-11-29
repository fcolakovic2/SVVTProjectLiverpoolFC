package pages;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
    private final By backToSiteButton = By.xpath("//a[@data-testid='BACK_TO_MAIN_SITE_BUTTON']");
    private final By logOutButtonAfterRegistration =  By.xpath("//a[@data-testid='SIGN_OUT_BUTTON']");
    private final By alreadyExistingEmailError = By.xpath("//div[text()='An account with this email address already exists']");
    private final By requiredFieldError(String fieldName) {
        return By.xpath("//input[@name='" + fieldName + "']/parent::div/following-sibling::p[text()='This field is required']");
    }

    public RegistrationPage(WebDriver driver) { this.driver = driver; }

    public void finalizeRegistration(boolean correctRegistrationForm){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitRegistration)).click();

        if (correctRegistrationForm){
            wait.until(ExpectedConditions.visibilityOfElementLocated(logOutButtonAfterRegistration));
            wait.until(ExpectedConditions.visibilityOfElementLocated(backToSiteButton)).click();
        }
    }


    public void selectCountry(String countryName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Open the dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(country));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        dropdown.click();

        // 2. Target the option
        By countryOption = By.xpath("//li[contains(text(),'" + countryName + "')]");

        // 3. Wait for clickable + scroll + JS click
        for (int i = 0; i < 5; i++) {
            try {
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(countryOption));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                return; // success
            } catch (Exception e) {
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }

        throw new RuntimeException("Could not select country: " + countryName);
    }



    public void setBirthDate(String birthDate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement birthDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfBirth));

        birthDateInput.click();
        birthDateInput.clear();
        birthDateInput.click();
        birthDateInput.sendKeys(birthDate);
        birthDateInput.sendKeys(Keys.ENTER);
    }


    public void fillRegistrationForm(
            String fName,
            String lName,
            String dateOfBirthValue,
            String genderOption,
            String emailAddress,
            String countryName,
            String pass,
            String confirmPass,
            boolean subscribe
    ) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // First Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName))
                .sendKeys(fName);

        // Last Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName))
                .sendKeys(lName);

        // Date Of Birth
        setBirthDate(dateOfBirthValue);

        // Gender dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(gender))
                .click();
        By genderOptionLocator = By.xpath("//li[contains(text(),'" + genderOption + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(genderOptionLocator)).click();

        // Email
        wait.until(ExpectedConditions.visibilityOfElementLocated(email))
                .sendKeys(emailAddress);

        selectCountry(countryName);

        // Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(password))
                .sendKeys(pass);

        // Confirm Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword))
                .sendKeys(confirmPass);

        // Subscribe Yes/No
        if (subscribe) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(subscribeYes))
                    .click();
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(subscribeNo))
                    .click();
        }
    }

    //region Validation methods

    public void validateDuplicateEmailError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(alreadyExistingEmailError)).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    public void validateFirstNameEmptyError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError("firstName"))).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    public void validateLastNameEmptyError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError("lastName"))).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    public void validateEmailEmptyError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError("email"))).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    public void validatePasswordEmptyError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError("password"))).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    public void validateConfirmPasswordEmptyError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError("confirmPassword"))).isDisplayed();
        Assertions.assertTrue(errorVisible);
    }

    //endregion
}
