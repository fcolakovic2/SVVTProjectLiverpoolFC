package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTestSetup {

    @Test
    public void validLogin() {
        home.clickSignIn();
        login.login("validuser@example.com", "ValidPass123"); // testing purposes
        Assertions.assertTrue(driver.getCurrentUrl().contains("account") || driver.getTitle().toLowerCase().contains("account"));
    }

    @Test
    public void invalidPasswordShowsError() {
        home.clickSignIn();
        LoginPage login = new LoginPage(driver);
        login.login("validuser@example.com", "wrongpass");
    }

    @Test
    public void emptyFieldsValidation() {
        home.clickSignIn();
        login.login("", "");
    }
}
