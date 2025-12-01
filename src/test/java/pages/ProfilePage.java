package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ProfilePage {

    private final WebDriver driver;
    private WebDriverWait wait;

    private final By subscription = By.xpath("//h1[text()='My All Red Account']");
    private final By firstNameOfUser = By.xpath("//dt[text()='First Name']/following-sibling::dd");
    private final By lastNameOfUser = By.xpath("//dt[text()='Last Name']/following-sibling::dd");
    private final By dateOfBirthOfUser = By.xpath("//dt[text()='Date of Birth']/following-sibling::dd");
    private final By usersFAQ = By.xpath("//a[text()='Please see our FAQs']");
    private final By upgradeButton = By.xpath("//a[text()='Upgrade']");


    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openUserFAQ(){
        WebElement userFAQ = driver.findElement(usersFAQ);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usersFAQ)).click();
    }

    public void openUpgradePage(){
        WebElement upgradeButtonElement = driver.findElement(upgradeButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(upgradeButton)).click();
    }

    public String getDateOfBirth(){
        WebElement dobElement = driver.findElement(dateOfBirthOfUser);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfBirthOfUser)).getText();
    }

    //region Validation

    public void validateCurrentUrl() {
        String expectedUrl = "profile";
        String actualUrl = driver.getCurrentUrl(); // get the current URL from the browser
        Assertions.assertTrue(actualUrl.contains(expectedUrl), "Current URL is not as expected!");
    }

    private void validateUrlInNewTab(String expectedUrlPart) {
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        // Wait for new tab
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.getWindowHandles().size() > existingWindows.size());

        // Switch to new window
        for (String window : driver.getWindowHandles()) {
            if (!existingWindows.contains(window)) {
                driver.switchTo().window(window);
                break;
            }
        }

        // Validate URL
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(
                currentUrl.contains(expectedUrlPart),
                "Expected URL part '"+ expectedUrlPart +"' not found in: " + currentUrl
        );

        // Close the new tab
        driver.close();

        // Switch back to original
        driver.switchTo().window(originalWindow);
    }

    public void validateCurrentFAQUrl() {
        validateUrlInNewTab("faq");
    }

    public void validateCurrentUpgradeUrl() {
        validateUrlInNewTab("allred");
    }


    public void validateCorrectSubscription(){
        WebElement subscriptions = wait.until(ExpectedConditions.visibilityOfElementLocated(subscription));
        Assertions.assertTrue(subscriptions.isDisplayed(), "No subscription element found!");
    }

    public void validateUserFirstName(String name){
       WebElement firstName =  wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameOfUser));
       String firstNameString = firstName.getText();
       Assertions.assertEquals(firstNameString, name, "First name is not as expected!");
    }

    public void validateUserLastName(String name){
        WebElement lastName =  wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameOfUser));
        String lastNameString = lastName.getText();
        Assertions.assertEquals(lastNameString, name, "Last name is not as expected!");
    }

    public static boolean isValidDateFormat(String date) {
        // Strict format: DD/MM/YYYY, leading zeros required
        String regex = "^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$";

        return date != null && date.matches(regex);
    }

    public void validateUserDateOfBirthFormat(String dateOfBirth){
       Assertions.assertTrue(isValidDateFormat(dateOfBirth), "Date of birth is not as expected!");
    }

    //endregion
}
