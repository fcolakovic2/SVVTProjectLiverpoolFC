package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.RandomDataGenerator;


public class RegistrationTests extends BaseTestSetup {


    @Test
    public void validRegistration() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(true);
        home.validateUserIsCorrectlyLoggedInAfterRegistering();
    }

    @Test
    public void registrationWithExistingEmailShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = "fariscolakovic00@gmail.com";
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateDuplicateEmailError();
    }

    @Test
    public void registrationWithIncorrectEmailShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = "fariscolakovic00gmail.com";
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateWrongEmailFormatError();
    }

    @Test
    public void registrationWithoutFirstNameShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateFirstNameEmptyError();
    }

    @Test
    public void registrationWithoutDateShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = "";
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateDateOfBirthEmptyError();
    }

    @Test
    public void registrationWithoutLastNameShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateLastNameEmptyError();
    }

    @Test
    public void registrationWithoutEmailShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = "";
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validateEmailEmptyError();
    }

    @Test
    public void registrationWithoutPasswordShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = "";
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, password, true);
        registration.finalizeRegistration(false);
        registration.validatePasswordEmptyError();
    }

    @Test
    public void registrationWithoutConfirmingPasswordShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, "", true);
        registration.finalizeRegistration(false);
        registration.validateConfirmPasswordEmptyError();
    }

    @Test
    public void registrationWithoutMatchingConfirmedPasswordShowsError() {
        home.clickSignIn();
        login.choseRegistrationPage();
        String email = RandomDataGenerator.generateEmail("gmail");
        String password = RandomDataGenerator.generatePassword(10);
        String gender = RandomDataGenerator.generateGender();
        String birthDate = RandomDataGenerator.generateBirthdate(18, 30);
        registration.fillRegistrationForm("Faris", "Colakovic", birthDate,
                gender, email, "United Kingdom", password, "test!ABC123", true);
        registration.finalizeRegistration(false);
        registration.validatePasswordsNotMatchingError();
    }


}
