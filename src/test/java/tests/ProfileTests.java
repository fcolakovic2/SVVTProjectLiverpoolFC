package tests;

import base.BaseTestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProfileTests extends BaseTestSetup {
    @BeforeEach
    public void beforeEachTest(){
        home.clickSignIn();
        login.correctLoginForRefactoring();
    }

    @AfterEach
    public void afterEachTest(){
        home.logOutFromProfilePage();
    }

    @Test
    public void openProfilePageAndValidateCorrectUserName() {
        home.openAccountSettings();
        profile.validateUserFirstName("Faris");
    }

    @Test
    public void openProfilePageAndValidateCorrectLastName() {
        home.openAccountSettings();
        profile.validateUserLastName("Colakovic");
    }

    @Test
    public void openProfilePageAndValidateCorrectSubscription() {
        home.openAccountSettings();
        profile.validateCorrectSubscription();
    }

    @Test
    public void openProfilePageAndValidateCorrectDateOfBirthFormat() {
        home.openAccountSettings();
        String dateOfBirth = profile.getDateOfBirth();
        profile.validateUserDateOfBirthFormat(dateOfBirth);
    }
}
