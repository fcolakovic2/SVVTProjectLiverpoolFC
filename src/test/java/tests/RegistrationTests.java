package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
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
        registration.finalizeRegistration();
        home.validateUserIsCorrectlyRegistered();
    }

    @Test
    public void registrationWithExistingEmailShowsError() {
        driver.get(BASE_URL + "register");
        RegistrationPage rp = new RegistrationPage(driver);

    }

    @Test
    public void registrationWithoutEmailShowsError() {
        driver.get(BASE_URL + "register");
        RegistrationPage rp = new RegistrationPage(driver);

    }

    @Test
    public void registrationWithoutPasswordShowsError() {
        driver.get(BASE_URL + "register");
        RegistrationPage rp = new RegistrationPage(driver);

    }

    @Test
    public void weakPasswordIsRejected() {
        driver.get(BASE_URL + "register");
        RegistrationPage rp = new RegistrationPage(driver);

    }
}
