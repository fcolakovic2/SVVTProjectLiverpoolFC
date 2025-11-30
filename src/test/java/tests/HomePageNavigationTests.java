package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HomePageNavigationTests extends BaseTestSetup {


    @Test
    public void openNewsPageAndValidateNavigation() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
    }

    @Test
    public void openVideoPageAndValidateNavigation() {
        home.clickOnSection("Video");
        news.openNewsSubSection("Video");
        videos.validateCurrentUrl();
    }


}
