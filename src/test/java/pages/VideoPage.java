package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
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
    private final By closeMarketingPopUp = By.xpath("//*[@class='marketing-popup__close']");
    private final By videos = By.xpath("//section[@class='listing']//article");
    private final By videoPlaying = By.xpath("//video[contains(@data-setup, '\"autoplay\":\"auto\"')]");
    private final By videoCategory = By.xpath("//p[@class='viewer-container__categories']");
    private final By categoryInInitialDropdownSubSection(String categoryName) {
        return By.xpath("//a[text()='" + categoryName + "']");
    }

    String[] categories = { "All Red Video", "Matches", "Players", "Browse", "Playlists", "Live" };

    public VideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openVideosSubSection(String subSection){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(categoryInInitialDropdownSubSection(subSection)))).click();
    }

    public void loadMoreVideos(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(loadMoreButton)).click();
    }

    public void openVideoSubSectionURL(String subSection){
        driver.get("https://video.liverpoolfc.com/browse/");
    }

    public void closeMarketingPopUp() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(closeMarketingPopUp)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true}));",
                    closeBtn
            );        } catch (TimeoutException ignored) {}
    }

    public void openFreeAccessVideo(int index){
       List<WebElement> videosFound = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));
       List<WebElement> freeVideos = new ArrayList<>();
       for (WebElement video : videosFound){
           List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

           if (labels.isEmpty()) {
               freeVideos.add(video);
           }
       }
       wait.until(ExpectedConditions.elementToBeClickable(freeVideos.get(index))).click();
    }

    public void openFullAccessVideo(int index){
        List<WebElement> videosFound = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));
        List<WebElement> paidVideos = new ArrayList<>();
        for (WebElement video : videosFound){
            List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

            if (!labels.isEmpty()) {
                paidVideos.add(video);
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(paidVideos.get(index))).click();
    }

    public String getHomeFreeVideoTitle(int index){
        List<WebElement> videosFound = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));
        List<WebElement> freeVideos = new ArrayList<>();
        for (WebElement video : videosFound){
            List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

            if (labels.isEmpty()) {
                freeVideos.add(video);
            }
        }

        return freeVideos.get(index).findElement(By.xpath(".//h3")).getText();
    }

    public String getHomePaidVideoTitle(int index){
        List<WebElement> videosFound = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));
        List<WebElement> paidVideos = new ArrayList<>();
        for (WebElement video : videosFound){
            List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

            if (!labels.isEmpty()) {
                paidVideos.add(video);
            }
        }
        return paidVideos.get(index).findElement(By.xpath(".//h3")).getText();
    }

    public String getFreeVideoCategoryFromBrowsePage(int index){
        List<WebElement> videoElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));

        List<WebElement> freeVideos = new ArrayList<>();
        for (WebElement video : videoElements){
            List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

            if (labels.isEmpty()) {
                freeVideos.add(video);
            }
        }
        return freeVideos.get(index).findElement(By.xpath(".//p[@class='video-card__category']")).getText();
    }

    public String getPaidVideoCategoryFromBrowsePage(int index){
        List<WebElement> videoElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(videos));
        List<WebElement> paidVideos = new ArrayList<>();
        for (WebElement video : videoElements){
            List<WebElement> labels = video.findElements(By.xpath(".//p[normalize-space()='FULL / VIDEO']"));

            if (!labels.isEmpty()) {
                paidVideos.add(video);
            }
        }
        return paidVideos.get(index).findElement(By.xpath(".//p[@class='video-card__category']")).getText();
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
        WebDriverWait waitForNumber = new WebDriverWait(driver, Duration.ofSeconds(10));

        waitForNumber.until(driver -> {
            WebElement numberOfVideosElement = driver.findElement(numberOfVideoResults);
            String text = numberOfVideosElement.getText().trim();

            try {
                int number = Integer.parseInt(text);
                return number > 0;  //must check this because webpage first shows 0 and then populates the string with correct number/replaces 0
            } catch (NumberFormatException e) {
                return false;
            }
        });

        // Now get the actual number
        WebElement numberOfVideosElement = driver.findElement(numberOfVideoResults);
        int actualNumberToInt = Integer.parseInt(numberOfVideosElement.getText().trim());

        Assertions.assertTrue(actualNumberToInt > numOfVideosThreshold,
                "Expected actualNumber (" + actualNumberToInt + ") to be greater than numOfVideos (" + numOfVideosThreshold + ")");
    }


    public void validateNumberOfVideosInTheList(int sizeOfVideoList){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.findElements(sectionForListOfVideos).size() >= sizeOfVideoList);
        List<WebElement> listOfVideos =  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sectionForListOfVideos));
        Assertions.assertEquals(listOfVideos.size(), sizeOfVideoList, "There aren't " + sizeOfVideoList + " videos by default listed in the browse section");
    }

    public void validateVideoTitle(String expectedTitle) {
        String actualTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1"))).getText();
        Assertions.assertEquals(actualTitle, expectedTitle, "Title is not as expected!");
    }

    public void validateVideoPlays() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(videoPlaying));
    }

    public void validateVideoDoesntPlay() {
        List<WebElement> videos = driver.findElements(videoPlaying);

        // If the list is empty, video is not present
        boolean videoVisible = !videos.isEmpty() && videos.get(0).isDisplayed();
        Assertions.assertFalse(videoVisible, "Video is visible but shouldn't be because it's a paid video!");
    }

    public void validateVideoCategory(String expectedCategory){
        String actualCategory = wait.until(ExpectedConditions.visibilityOfElementLocated(videoCategory)).getText();
        Assertions.assertEquals(expectedCategory, actualCategory, "Category not correct!");
    }

    //endregion
}
