package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ArticlePage {

    private final WebDriver driver;
    private WebDriverWait wait;

    private final By articleTitleH1 = By.tagName("h1");

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

    //endregion
}
