package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTestSetup {

    @Test
    public void validLogin() {
        home.clickSignIn();
        login.login("fariscolakovic00@gmail.com", "hBSBR!LKDsna1");
        home.validateUserIsCorrectlyLoggedInAfterRegistering();
    }

    @Test
    public void invalidPasswordShowsError() {
        home.clickSignIn();
        login.login("fariscolakovic00@gmail.com", "wrongpass");
        home.validateUserIsNotLoggedIn();
    }

    @Test
    public void emptySpacesPasswordShowsError() {
        home.clickSignIn();
        login.login("fariscolakovic00@gmail.com", "  ");
        home.validateUserIsNotLoggedIn();
    }

    @Test
    public void invalidEmailShowsError() {
        home.clickSignIn();
        login.login("fariscolakovic0@gmail.com", "hBSBR!LKDsna1");
        home.validateUserIsNotLoggedIn();
    }

    @Test
    public void emptySpacesEmailShowsError() {
        home.clickSignIn();
        login.login("   ", "hBSBR!LKDsna1");
        home.validateUserIsNotLoggedIn();
    }

    @Test
    public void navigatingToPasswordResetThroughLoginPage () {
        home.clickSignIn();
        login.clickForgetPassword();
        login.validateUserIsOnResetPasswordPage();
    }
}
