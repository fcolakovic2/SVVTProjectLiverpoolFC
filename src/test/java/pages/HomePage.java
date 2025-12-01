package pages;

import base.BaseTestSetup;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    private final By contentGridItems = By.xpath("//li[@class='content-grid-item']");
    private final By articleTitles = By.xpath("//li[@class='content-grid-item']//h2[not(contains(text(),'Video'))]");
    private final By articleTimes = By.xpath("//li[@class='content-grid-item']//time//span");
    private final By articleImages = By.xpath("//li[@class='content-grid-item']//img");
    public HomePage(WebDriver driver) { this.driver = driver; }

    public void acceptCookiesIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement btn = shortWait.until(ExpectedConditions.visibilityOfElementLocated(acceptCookiesBtn));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);


        } catch (TimeoutException ignored) {
            // No cookie banner within 2s → ignore
        }
    }

    public void closePopupIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement popup = shortWait.until(ExpectedConditions.visibilityOfElementLocated(maybeLaterPromoPopUp));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popup);

        } catch (TimeoutException ignored) {
            // Popup never appeared → fine
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

    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn)).click();
    }

    public void logOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logout.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }

    public void clickOnSection(String sectionName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getSectionByName(sectionName))).click();
    }

    public void openAccountSettings() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountButton)).click();
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

    public void openArticleByIndex(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Get all li elements
        List<WebElement> allItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(articleTitles));

        wait.until(ExpectedConditions.elementToBeClickable(allItems.get(index))).click();
        wait.until(ExpectedConditions.urlContains("news"));
    }


    public String getHomeArticleTitle(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> articleTitlesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(articleTitles));
        return articleTitlesElements.get(index).getText();
    }

    public String getHomeArticleTime(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> articleTimesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(articleTimes));
        return articleTimesElements.get(index).getText();
    }

    public String getHomeImageAlt(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> articleTimesElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(articleImages));
        return articleTimesElements.get(index).getAttribute("alt");
    }

    //region ValidationMethods

    public void validateUserIsCorrectlyLoggedInAfterRegistering() {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement account = wait.until(ExpectedConditions.visibilityOfElementLocated(accountButton));
       WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

       Assertions.assertTrue(account.isDisplayed(), "Account button not displayed!");
       Assertions.assertTrue(logout.isDisplayed(), "Logout button not displayed!");
       home.logOut();
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

    public void validateThereAreHomePageArticles(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> contentItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(contentGridItems));
        Assertions.assertFalse(contentItems.isEmpty(), "There are no home page articles");
    }

    //endregion


}
