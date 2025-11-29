package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    private final By signIn = By.xpath("//button[text()='Login']");
    private final By homePageLogo = By.xpath("//a[@class='Skip to main content']");
    private final By newsSection = By.xpath("//button[text()='News']");
    private final By fixturesTeamsSection = By.xpath("//button[text()='Fixtures & Teams']");
    private final By ticketsBookingSection = By.xpath("//button[text()='Tickets & Booking']");
    private final By shopSection = By.xpath("//button[text()='Shop']");
    private final By videoSection = By.xpath("//button[text()='Video']");
    private final By moreSection = By.xpath("//button[text()='More']");
    private final By joinButton = By.xpath("//button[text()='Join']");
    private final By languageButton = By.xpath("//button[text()='en']");
    private final By sponsorshipLogo = By.xpath("//img[@src='/standard-chartered.webp']//following-sibling::span[text()='Standard Chartered']");

    public HomePage(WebDriver driver) { this.driver = driver; }

    public void clickSignIn() { driver.findElement(signIn).click(); }

}
