package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.*;

public class UserDataUpdateTests extends BaseTestSetup{

    @BeforeEach
    public  void loginBeforeAll() {
        home.clickSignIn();
        login.correctLoginForRefactoring();
        home.openAccountSettings();
    }

    @AfterEach
    public void logoutAfterAll() {
        home.logOutFromProfilePage();
    }

    @Test
    public void updateWithValidPhoneNumber() {
        profile.selectCountryByDataValue("BA|+387");
        profile.enterPhoneNumber("61919711");
        profile.clickUpdateButton();
        Assertions.assertTrue(profile.validateSuccessfulUpdateNotificationDisplayed());
    }

    @Test
    public void updateWithEmptyPhoneNumber() {
        profile.selectCountryByDataValue("BA|+387");
        profile.enterPhoneNumber("");
        profile.clickUpdateButton();
        profile.validateRequiredFieldError();
    }

    @Test
    public void updateWithTooShortPhoneNumber() {
        profile.selectCountryByDataValue("BA|+387");
        profile.enterPhoneNumber("629");
        profile.clickUpdateButton();
        profile.validateInvalidPhoneNumberFieldError();
    }

    @Test
    public void updateWithTooLongPhoneNumber() {
        profile.selectCountryByDataValue("BA|+387");
        profile.enterPhoneNumber("6241059482");
        profile.clickUpdateButton();
        profile.validateInvalidPhoneNumberFieldError();
    }

    @Test
    public void updateWithSpecialCharsPhoneNumber() {
        profile.selectCountryByDataValue("BA|+387");
        profile.enterPhoneNumber("62410!*.-");
        profile.clickUpdateButton();
        profile.validateInvalidPhoneNumberFieldError();
    }



}
