package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    protected static ProfilePage profile;

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
        int attempts = 0;
        int maxRetries = 2; // number of retries
        boolean pageLoaded = false;

        while (attempts < maxRetries && !pageLoaded) {
            try {
                driver.navigate().to(BASE_URL);
                pageLoaded = true; // success
            } catch (TimeoutException e) {
                attempts++;
                System.out.println("Page load timed out, retrying... Attempt " + attempts);
                if (attempts == maxRetries) {
                    throw e; // rethrow after final attempt
                }
            }
        }

        home = new HomePage(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
        news = new NewsPage(driver);
        articles = new ArticlePage(driver);
        videos = new VideoPage(driver);
        profile = new ProfilePage(driver);

        home.acceptCookiesIfPresent();
        home.closePopupIfPresent();
    }

    @BeforeAll public static void setUpDriverAndPages() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions  options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        // avoid bot detection
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");

// real window size
        options.addArguments("--window-size=1400,900");

// enable JavaScript, images, GPU
        options.addArguments("--enable-gpu");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

}
