package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Test;

public class NewsTests extends BaseTestSetup {

    @Test
    public void openNewsPageAndValidateFilterForAllNews() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.validateCorrectTabIsOpened("All news");
    }

    @Test
    public void openNewsPageAndValidateFilterForMen() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Men");
        news.validateCorrectTabIsOpened("Men");
    }

    @Test
    public void openNewsPageAndValidateFilterForWomen() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Women");
        news.validateCorrectTabIsOpened("Women");
    }

    @Test
    public void openNewsPageAndValidateFilterForAcademy() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Academy");
        news.validateCorrectTabIsOpened("Academy");
    }

    @Test
    public void openNewsPageAndValidateFilterForMediaWatch() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Media Watch");
        news.validateCorrectTabIsOpened("Media Watch");
    }

    @Test
    public void openNewsPageAndValidateFilterForClub() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Club");
        news.validateCorrectTabIsOpened("Club");
    }

    @Test
    public void openNewsPageAndValidateFilterForTickets() {
        home.clickOnSection("News");
        news.openNewsSubSection("All News");
        news.validateCurrentUrl();
        news.filterByCategory("Tickets");
        news.validateCorrectTabIsOpened("Tickets");
    }



}
