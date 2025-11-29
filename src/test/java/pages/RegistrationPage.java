package pages;

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

    public RegistrationPage(WebDriver driver) { this.driver = driver; }

    public void finalizeRegistration(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitRegistration)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logOutButtonAfterRegistration));
        wait.until(ExpectedConditions.visibilityOfElementLocated(backToSiteButton)).click();
    }


    public void selectCountry(String countryName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // --- STEP 1: Open the dropdown safely ---
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(country));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", dropdown
        );

        try {
            dropdown.click();
        } catch (Exception e) {
            // fallback if animation blocks click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
        }

        // --- STEP 2: Identify target option ---
        By optionLocator = By.xpath("//li[contains(text(),'" + countryName + "')]");

        // --- STEP 3: Wait for option to be visible and interactable ---
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", option
        );

        // --- STEP 4: Click option via JS to bypass overlay ---
        for (int i = 0; i < 5; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                return; // success
            } catch (Exception retry) {
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }

        throw new RuntimeException("Failed to select country: " + countryName);
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
}
