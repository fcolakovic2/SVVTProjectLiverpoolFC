package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final By videosTitles = By.xpath("//section[@class='listing']//article//h3");
    private final By videoPlaying = By.xpath("//video[contains(@data-setup, '\"autoplay\":\"auto\"')]");
    private final By videoCategory = By.xpath("//p[@class='viewer-container__categories']");
    private final By categoryInInitialDropdownSubSection(String categoryName) {
        return By.xpath("//a[text()='" + categoryName + "']");
    }
    private final By videoSearchInput = By.xpath("//input[@class='video-filters__search-bar']");
    private final By videoFiltersOpen = By.xpath("//div[@class='video-filters__actions']//button[@class='video-filters__toggle']");
    private final By videoFilterLength(String length){
        return By.xpath("//button[@class='duration-menu__button']//p[text()='"+ length+"']");
    }
    private final By videoFilterLengthHighlighted(String length){
        return By.xpath(" //p[text()='"+ length +"']/parent::button[contains(@class, 'active')]");
    }
    private final By actualVideosLength = By.xpath("//p[@class='video-card__duration']");

    String[] categories = { "All Red Video", "Matches", "Players", "Browse", "Playlists", "Live" };

    public VideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openVideosSubSection(String subSection){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(categoryInInitialDropdownSubSection(subSection)))).click();
    }

    public void loadMoreVideos(){
        wait.until(ExpectedConditions.elementToBeClickable(loadMoreButton)).click();
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

    public void enterSearchVideosValue(String searchValue){
       WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(videoSearchInput));
       searchInput.click();
       searchInput.clear();
       searchInput.sendKeys(searchValue);
       searchInput.sendKeys(Keys.ENTER);
       wait.until(ExpectedConditions.urlContains("?s="));
    }

    public void openVideoLengthFilter(){
       driver.findElement(videoFiltersOpen).click();
       wait.until(ExpectedConditions.visibilityOfElementLocated(videoFilterLength("Long")));
    }

    public void selectVideoLengthFilter(String length) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(videoFilterLength(length))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(videoFilterLengthHighlighted(length)));
        Thread.sleep(2000);
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

    public void validateNumberOfAllVideos(int numOfVideos) {
        WebDriverWait waitForNumber = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement numberOfVideosElement = driver.findElement(numberOfVideoResults);
        int actualNumberToInt = Integer.parseInt(numberOfVideosElement.getText().trim());

        Assertions.assertTrue(actualNumberToInt > numOfVideos,
                "Expected actualNumber (" + actualNumberToInt + ") to be greater than numOfVideos (" + numOfVideos + ")");
    }

    public void validateExactNumberOfAllVideos(int numOfVideos) throws InterruptedException {
        WebDriverWait waitForNumber = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(2000);
        WebElement numberOfVideosElement = driver.findElement(numberOfVideoResults);
        int actualNumberToInt = Integer.parseInt(numberOfVideosElement.getText().trim());

        Assertions.assertEquals(actualNumberToInt, numOfVideos, "Expected actualNumber (" + actualNumberToInt + ") to be equal to numOfVideos (" + numOfVideos + ")");
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

    public void validateAllVideoTitlesContainInputString(String expectedTitle) {
        // Capture old titles text
        List<String> oldTitlesText = driver.findElements(By.xpath("//section[@class='listing']//article//h3"))
                .stream()
                .map(WebElement::getText)
                .toList();

        // Wait until results update (no need to type again)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            try {
                List<WebElement> currentTitles = driver.findElements(By.xpath("//section[@class='listing']//article//h3"));
                if (currentTitles.isEmpty()) return false;
                String firstText = currentTitles.get(0).getText();
                return !firstText.equals(oldTitlesText.get(0));
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        // Fetch fresh elements
        List<WebElement> newTitles = driver.findElements(By.xpath("//section[@class='listing']//article//h3"));

        for (WebElement title : newTitles) {
            String actual = title.getText().toLowerCase();
            Assertions.assertTrue(
                    actual.contains(expectedTitle.toLowerCase()),
                    "Title '" + actual + "' does not contain expected text '" + expectedTitle + "'."
            );
        }

    }

    public void validateAllVideoTitlesHaveSetLength(String length) {
       List<WebElement> videosLengths = driver.findElements(actualVideosLength);
       List<Integer> numberOfMinutes = new ArrayList<>();
       int minute;
        for (WebElement lengthOfVideoString : videosLengths) {
            String actual = lengthOfVideoString.getText();
            if (Objects.equals(length, "Short") || Objects.equals(length, "Medium")){
                String[] splitByMinutesSeconds = actual.split(":");
                Assertions.assertFalse(actual.isEmpty(), "Video duration is empty!");
                Assertions.assertTrue(splitByMinutesSeconds[0].charAt(0)=='0' || splitByMinutesSeconds[0].charAt(0) == '1');
                if (splitByMinutesSeconds[0].charAt(0) == '1'){
                    String result = "" + splitByMinutesSeconds[0].charAt(0)
                            + splitByMinutesSeconds[0].charAt(1);
                    minute = Integer.parseInt(result);
                }
                else{
                    minute = Integer.parseInt(String.valueOf(splitByMinutesSeconds[0].charAt(1)));
                }
                numberOfMinutes.add(minute);
            }
            else {
                if (actual.contains(":")){
                    String[] splitByMinutesSeconds = actual.split(":");
                    Assertions.assertFalse(actual.isEmpty(), "Video duration is empty!");
                    minute = Integer.parseInt(splitByMinutesSeconds[0]);
                    numberOfMinutes.add(minute);
                }
                else if (actual.contains("h")){
                    System.out.println(actual); // no need to add it to minutes and check, its definitely longer than 1h
                }
            }
        }

        switch(length) {
            case "Short":
                boolean allOkShort = numberOfMinutes.stream().allMatch(n -> n < 5);
                Assertions.assertTrue(allOkShort, "List contains values greater than 5");
                break;
            case "Medium":
                boolean allOMedium = numberOfMinutes.stream().allMatch(n -> n >= 5 && n <= 10);
                Assertions.assertTrue(allOMedium, "List contains values greater than 5");
                break;
            case "Long":
                boolean allOkLong = numberOfMinutes.stream().allMatch(n -> n > 10);
                Assertions.assertTrue(allOkLong, "List contains values smaller than 10");
                break;

        }
    }



    //endregion
}
