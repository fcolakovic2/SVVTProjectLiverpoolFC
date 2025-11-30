package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoPage {

    private final WebDriver driver;
    private WebDriverWait wait;
    private final By categoriesTags = By.xpath("//a[contains(@href, 'video')]");
    private final By categoryName = By.xpath("//span[@class='video-filters__page-heading']");
    private final By numberOfVideoResults = By.xpath("//p[@class='listing__result-count']//span");
    private final By sectionForListOfVideos = By.xpath("//section[@class='listing']//article");
    private final By loadMoreButton = By.xpath("//div[@class='button-slice listing__more']//button");
    private final By categoryInInitialDropdownSubSection(String categoryName) {
        return By.xpath("//a[text()='" + categoryName + "']");
    }

    String[] categories = { "All Red Video", "Matches", "Players", "Browse", "Playlists", "Live" };

    public VideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openVideosSubSection(String subSection){
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryInInitialDropdownSubSection(subSection))).click();
    }

    public void loadMoreVideos(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(loadMoreButton)).click();
    }

    //region Validation

    public void validateCurrentUrl() {
        String expectedUrl = "video";
        String actualUrl = driver.getCurrentUrl(); // get the current URL from the browser
        assertTrue(actualUrl.contains(expectedUrl),  "Current URL is not as expected!");
    }

    public void validateAllCategories() {
        List<WebElement> categoryLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesTags));

        List<String> visibleTexts = new ArrayList<>();
        for (WebElement link : categoryLinks) {
            if (link.isDisplayed()) {
                visibleTexts.add(link.getText());
            }
        }

        for (String category : categories) {
            assertTrue(visibleTexts.contains(category),
                    "Expected category not visible: " + category);
        }
    }

    public void validateCorrectCategoryIsOpened(String nameOfCategory) {
        WebElement videoCategory = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryName));

        String actualCategory = videoCategory.getText();
        Assertions.assertEquals(nameOfCategory, actualCategory, "Name of category is not as expected!");
    }

    public void validateNumberOfAllVideos(int numOfVideosThreshold) {
        WebElement numberOfVideosElement = wait.until(ExpectedConditions.visibilityOfElementLocated(numberOfVideoResults));

        String actualNumber = numberOfVideosElement.getText();
        int actualNumberToInt = Integer.parseInt(actualNumber);
        Assertions.assertTrue(actualNumberToInt > numOfVideosThreshold,
                "Expected actualNumber (" + actualNumber + ") to be greater than numOfVideos (" + numOfVideosThreshold + ")");
        // I can't check the exact number of results because videos are being uploaded every few days. Therefore I will check that the current number of videos
        // is the lowest threshold and actual number is only larger than it since new videos are always added (every few days). In real time application I would
        // check this by fetching the actual number from APIs and use that number and check if actualNumber equals numOfVideos.
    }

    public void validateNumberOfVideosInTheList(int sizeOfVideoList){
        List<WebElement> listOfVideos =  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sectionForListOfVideos));
        Assertions.assertEquals(listOfVideos.size(), sizeOfVideoList, "There aren't 12 videos by default listed in the browse section");
    }

    //endregion
}
