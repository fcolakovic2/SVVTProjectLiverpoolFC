package pages;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewsPage {

    private final WebDriver driver;
    private WebDriverWait wait;

    private final By categoryTab(String categoryName) {
        return By.xpath("//div[@title='" + categoryName + "']");
    }
    private final By categoryTabInDropdown(String categoryName) {
        return By.xpath("//button[text()='" + categoryName + "']");
    }
    private final By categoryInInitialDropdownSubSection(String categoryName) {
        return By.xpath("//a[text()='" + categoryName + "']");
    }
    private final By loadMoreButton = By.xpath("//button[text()='Load more']");
    private final By filterButton = By.xpath("//button[text()='Filter by topic']");

    public NewsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void filterByCategory(String categoryName) {
        WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        filterBtn.click();

        WebElement category = wait.until(ExpectedConditions.elementToBeClickable(categoryTabInDropdown(categoryName)));
        category.click();
    }

    public void validateCorrectTabIsOpened(String categoryName) {
        WebElement category = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryTab(categoryName)));
        boolean visibleCorrectCategory = category.isDisplayed();
        Assertions.assertTrue(visibleCorrectCategory); //not strictly needed because I wait for it above but I added it just to show how an assert would work
    }

    public void validateCurrentUrl() {
        String expectedUrl = "https://www.liverpoolfc.com/news";
        String actualUrl = driver.getCurrentUrl(); // get the current URL from the browser
        Assertions.assertEquals(expectedUrl, actualUrl, "Current URL is not as expected!");
    }

    public void openNewsSubSection(String subSection){
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryInInitialDropdownSubSection(subSection))).click();
    }
}
