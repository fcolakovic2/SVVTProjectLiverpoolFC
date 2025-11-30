package pages;

import base.BaseTestSetup;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BaseTestSetup {
    private final WebDriver driver;

    private final By signIn = By.xpath("//button[text()='Login']");
    private final By homePageLogo = By.xpath("//a[@class='Skip to main content']");
    private By getSectionByName(String sectionName) {
        return By.xpath("//button[text()='" + sectionName + "']");
    }
    private final By joinButton = By.xpath("//button[text()='Join']");
    private final By languageButton = By.xpath("//button[text()='en']");
    private By languageOption(String langText) {
        return By.xpath("//a[text()='" + langText + "']");
    }
    private final By sponsorshipLogo = By.xpath("//img[@src='/standard-chartered.webp']//following-sibling::span[text()='Standard Chartered']");
    private final By acceptCookiesBtn = By.xpath("//button[text()='Accept All Cookies']");
    private final By maybeLaterPromoPopUp = By.xpath("//button[text()='Maybe Later']");
    private final By accountButton = By.xpath("//a[text()='Account']");
    private final By logoutButton = By.xpath("//a[text()='Logout']");

    public HomePage(WebDriver driver) { this.driver = driver; }

    public void acceptCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1. Wait for the cookie button to appear and be visible
            WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptCookiesBtn));

            // 2. Scroll it into view (handles animations)
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

            // 3. Small pause to allow fade-in / slide-in animation
            Thread.sleep(300);

            // 4. Click using JS to bypass overlay / animation issues
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        } catch (Exception ignored) {
            // Cookie banner did not appear — safe to ignore
        }
    }


    private void waitUntilElementStopsMoving(WebElement element) {
        try {
            long previousY = element.getLocation().getY();
            Thread.sleep(150);

            long currentY = element.getLocation().getY();

            int retries = 0;
            while (previousY != currentY && retries < 10) {
                previousY = currentY;
                Thread.sleep(150);
                currentY = element.getLocation().getY();
                retries++;
            }

        } catch (InterruptedException ignored) {
        }
    }

    public void closePopUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1. Wait until popup is visible
            WebElement popup = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(maybeLaterPromoPopUp)
            );

            // 2. Wait until animation finishes (element stops moving)
            waitUntilElementStopsMoving(popup);

            // 3. Ensure it's clickable
            wait.until(ExpectedConditions.elementToBeClickable(popup));

            // 4. JS click avoids hitbox issues during animation
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popup);

        } catch (Exception ignored) {
            // Popup did not appear — safe to ignore
        }
    }

    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn)).click();
    }

    public void logOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).click();
    }

    public void clickOnSection(String sectionName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getSectionByName(sectionName))).click();
    }

    public void hoverOverSection(String sectionName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(getSectionByName(sectionName)));
        Actions actions = new Actions(driver);
        actions.moveToElement(section).perform();
    }

    public void openLanguageDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        WebElement langDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(languageButton));
        actions.moveToElement(langDropDown).pause(Duration.ofMillis(150)).click().perform();
    }

    public void selectLanguage(String languageText) {
        // 1. Hover on EN button and keep it open
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement menuButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(languageButton)
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(menuButton).perform();

        // 2. Wait for the dropdown <a> to appear
        WebElement lang = wait.until(
                ExpectedConditions.visibilityOfElementLocated(languageOption(languageText))
        );

        // 3. Move to the actual language item (keeps dropdown open)
        actions.moveToElement(lang).pause(Duration.ofMillis(100)).click().perform();
    }

    //region ValidationMethods

    public void validateUserIsCorrectlyLoggedInAfterRegistering(){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement account = wait.until(ExpectedConditions.visibilityOfElementLocated(accountButton));
       WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

       Assertions.assertTrue(account.isDisplayed(), "Account button not displayed!");
       Assertions.assertTrue(logout.isDisplayed(), "Logout button not displayed!");
    }

    public void validateUserIsNotLoggedIn(){

        boolean accountNotVisible =
                driver.findElements(accountButton)
                        .stream()
                        .noneMatch(WebElement::isDisplayed);

        boolean logoutNotVisible =
                driver.findElements(logoutButton)
                        .stream()
                        .noneMatch(WebElement::isDisplayed);

        Assertions.assertTrue(accountNotVisible, "Account button IS visible!");
        Assertions.assertTrue(logoutNotVisible, "Logout button IS visible!");
    }

    public void validateUrlEndsWith(String languageCode) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/" + languageCode));
        Assertions.assertTrue(
                driver.getCurrentUrl().endsWith("/" + languageCode),
                "URL does not end with /" + languageCode
        );
    }

    public void validateAccountAndLoginButtons(String accountText, String loginText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='" + accountText + "']")));
        WebElement log = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='" + loginText + "']")));

        Assertions.assertTrue(acc.isDisplayed());
        Assertions.assertTrue(log.isDisplayed());
    }

    //endregion


}
