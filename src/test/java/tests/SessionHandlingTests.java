package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SessionHandlingTests extends BaseTestSetup {

    private boolean loggedOut = false;

    @BeforeEach
    public  void loginBeforeAll() {
        home.clickSignIn();
        login.correctLoginForRefactoring();
    }

    @AfterEach
    public void logoutAfterAll() {
        if (!loggedOut) home.logOut();
    }

    @Test
    public void testSessionCookieExistsAfterLogin() {
        home.validateUserIsCorrectlyLoggedInAfterRegisteringWithoutLogout();
        Cookie sessionCookie = driver.manage().getCookieNamed("lfc_sso_access_token");
        Assertions.assertNotNull(sessionCookie, "Session cookie should exist after login.");
    }

    @Test
    public void testSessionPersistsAcrossNavigation() {
        home.validateUserIsCorrectlyLoggedInAfterRegisteringWithoutLogout();
        // Navigate elsewhere
        driver.get("https://www.liverpoolfc.com/news");
        Cookie sessionCookie = driver.manage().getCookieNamed("lfc_sso_access_token");
        Assertions.assertNotNull(sessionCookie, "Session cookie should exist after login.");
        driver.get("https://www.liverpoolfc.com");
        Cookie sessionCookieSecondTry = driver.manage().getCookieNamed("lfc_sso_access_token");
        Assertions.assertNotNull(sessionCookieSecondTry, "Session cookie should still exist after login.");
    }

    @Test
    public void testSessionCookieRemovedAfterLogout() {
        home.validateUserIsCorrectlyLoggedInAfterRegistering();
        Cookie sessionCookie = driver.manage().getCookieNamed("lfc_sso_access_token");
        Assertions.assertNull(sessionCookie, "Session cookie should not exist after logout.");
        loggedOut=true;
    }
}
