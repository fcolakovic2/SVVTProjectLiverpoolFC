package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HomePageNavigationTests extends BaseTestSetup {

    @BeforeAll
    public static void setCookiesAndLogin(){
        setLoggedIn(false);
        setCookiesHandled(false);
    }

    @Test
    public void openNewsPageAndValidateNavigation() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
    }


}
