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
    // Page objects (static)
    protected static HomePage home;
    protected static LoginPage login;
    protected static RegistrationPage registration;

    @BeforeAll
    public static void setUpDriverAndPages() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initialize page objects
        home = new HomePage(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
    }

    @BeforeEach
    public void goToHome() {
        driver.get(BASE_URL);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

}
