package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import utils.RandomDataGenerator;

public class RegistrationTests extends BaseTestSetup {
    private static boolean cookiesHandled = false;
    private static boolean loggedIn = false;
    @BeforeEach
    public void setupRegistrationPage() {
        driver.get("https://www.liverpoolfc.com");

        // Only handle cookies and popup on the first test
        if (!cookiesHandled) {
            home.acceptCookies();
            home.closePopUp();
            cookiesHandled = true;
        }

        if (loggedIn) {
           home.logOut();
        }
    }

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
        loggedIn = true;
        home.validateUserIsCorrectlyRegistered();
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


}
