package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDataUpdateTests extends BaseTestSetup{

    @BeforeAll
    public static void loginBeforeAll() {
        home.clickSignIn();
        login.correctLoginForRefactoring();
    }

}
