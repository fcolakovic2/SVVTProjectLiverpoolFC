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

public class ArticlePage {

    private final WebDriver driver;
    private WebDriverWait wait;

    private final By articleTitleH1 = By.tagName("h1");
    private final By articleType = By.xpath("//h1//span");
    private final By articleTimePublished = By.xpath("//span[contains(text(),'Published')]//span");
    private final By articleImage = By.xpath("//h1/parent::div/preceding-sibling::div//img");

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //region Validation

    public void validateCorrectArticleTitle(String expectedTitle){
        WebElement h1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(articleTitleH1)
        );

        String actualTitle = h1.getText();

        Assertions.assertEquals(
                expectedTitle,
                actualTitle,
                "Article title in <h1> does not match expected title!"
        );
    }

    public void validateCorrectArticleType(String expectedType){
        WebElement actualTypeElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(articleType)
        );

        String actualType = actualTypeElement.getText();

        Assertions.assertEquals(
                expectedType,
                actualType,
                "Actual article type on article page does not match expected type!"
        );
    }

    public void validateCorrectArticlePublishedTime(String expectedTime){
        WebElement timeElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(articleTimePublished)
        );

        String actualTime = timeElement.getText().toLowerCase();

        Assertions.assertEquals(
                expectedTime,
                actualTime,
                "Article published time in <span> does not match expected published time!"
        );
    }

    public void validateCorrectImage(String expectedImage){
        WebElement timeElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(articleImage)
        );

        String actualImage = timeElement.getAttribute("alt");
        Assertions.assertEquals(
                expectedImage,
                actualImage,
                "Article image alt does not match expected image alt from home page!"
        );
    }

    //endregion
}
