package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.time.Duration;

public class BaseTestSetup {

    protected static WebDriver driver;
    protected static final String BASE_URL = "https://www.liverpoolfc.com/";
    private static boolean cookiesHandled = false;
    private static boolean loggedIn = false;

    // Page objects (static)
    protected static HomePage home;
    protected static LoginPage login;
    protected static RegistrationPage registration;
    protected static NewsPage news;
    protected static ArticlePage articles;
    protected static VideoPage videos;

    public static void setLoggedIn(boolean state) {
        loggedIn = state;
    }
    public static void setCookiesHandled(boolean state) {
        cookiesHandled = state;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public void safeGet(WebDriver driver, String url) {
        int maxRetries = 3;

        for (int i = 1; i <= maxRetries; i++) {
            try {
                driver.get(url);

                // If the page loads enough to have a title, assume success
                if (!driver.getTitle().isEmpty()) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("Attempt " + i + " failed: " + e.getMessage());
            }

            // If stuck or failed, try to refresh before next attempt
            try {
                driver.navigate().refresh();
            } catch (Exception ignored) {}

            // Small wait between attempts
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        }

        // If all attempts failed
        throw new RuntimeException("Failed to load URL after retries: " + url);
    }

    @BeforeEach
    public void setupTest() {
        driver.manage().deleteAllCookies();
        driver.navigate().to(BASE_URL);

        home = new HomePage(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
        news = new NewsPage(driver);
        articles = new ArticlePage(driver);
        videos = new VideoPage(driver);

        home.acceptCookiesIfPresent();
        home.closePopupIfPresent();
    }

    @BeforeAll
    public static void setUpDriverAndPages() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

}
