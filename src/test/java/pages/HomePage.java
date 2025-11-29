package pages;

import base.BaseTestSetup;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BaseTestSetup {
    private final WebDriver driver;

    private final By signIn = By.xpath("//button[text()='Login']");
    private final By homePageLogo = By.xpath("//a[@class='Skip to main content']");
    private final By newsSection = By.xpath("//button[text()='News']");
    private final By fixturesTeamsSection = By.xpath("//button[text()='Fixtures & Teams']");
    private final By ticketsBookingSection = By.xpath("//button[text()='Tickets & Booking']");
    private final By shopSection = By.xpath("//button[text()='Shop']");
    private final By videoSection = By.xpath("//button[text()='Video']");
    private final By moreSection = By.xpath("//button[text()='More']");
    private final By joinButton = By.xpath("//button[text()='Join']");
    private final By languageButton = By.xpath("//button[text()='en']");
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
        driver.findElement(signIn).click();
    }

    public void logOut() {
        driver.findElement(logoutButton).click();
    }

    public void openRegistrationPage() {
        driver.findElement(joinButton).click();
        driver.findElement(languageButton).click();
    }

    //region ValidationMethods

    public void validateUserIsCorrectlyRegistered(){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement account = wait.until(ExpectedConditions.visibilityOfElementLocated(accountButton));
       WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

       Assertions.assertTrue(account.isDisplayed(), "Account button not displayed!");
       Assertions.assertTrue(logout.isDisplayed(), "Logout button not displayed!");
    }

    //endregion


}
