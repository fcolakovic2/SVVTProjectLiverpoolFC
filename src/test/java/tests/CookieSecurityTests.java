package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.util.Set;

public class CookieSecurityTests extends BaseTestSetup {

    @BeforeEach
    public  void loginBeforeAll() {
        home.clickSignIn();
        login.correctLoginForRefactoring();
        home.validateUserIsCorrectlyLoggedInAfterRegisteringWithoutLogout();
    }

    @AfterEach
    public void logoutAfterAll() {
        home.logOut();
    }

    @Test
    public void testCookieHttpOnlyFlag() {
        Set<Cookie> cookies = driver.manage().getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lfc_sso_access_token")) {
                Assertions.assertTrue(cookie.isHttpOnly(),
                        cookie.getName() + " should have HttpOnly flag set.");
            }
        }
    }

    @Test
    public void testCookieSecureFlag() {
        Set<Cookie> cookies = driver.manage().getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lfc_sso_access_token")) {
                Assertions.assertTrue(cookie.isSecure(),
                        cookie.getName() + " should have Secure flag set.");
            }
        }
    }

    @Test
    public void testCookieExpiry() {
        Set<Cookie> cookies = driver.manage().getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lfc_sso_access_token")) {
                Assertions.assertNotNull(cookie.getExpiry(), cookie.getName() + " should have an expiry set.");
            }
        }
    }
}
