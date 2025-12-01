package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VideoPlaybackTests extends BaseTestSetup {

    @Test
    public void openFreeVideoAndValidateTitle() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        String videoTitle = videos.getHomeFreeVideoTitle(1);
        videos.openFreeAccessVideo(1);
        videos.validateVideoTitle(videoTitle);
    }

    @Test
    public void openPaidVideoAndValidateTitle() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        String videoTitle = videos.getHomePaidVideoTitle(1);
        videos.openFullAccessVideo(1);
        videos.validateVideoTitle(videoTitle);
    }

    @Test
    public void openFreeVideoAndValidateItPlays() throws InterruptedException {
        home.clickSignIn();
        login.login("fariscolakovic00@gmail.com", "hBSBR!LKDsna1");
        Thread.sleep(5000);
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.openVideosSubSection("Browse"); //need to select Browse from the navigation bar again, because if the user is logged in he is directly taken to All Red videos website when clicking Browse for the 1st time
        videos.openFreeAccessVideo(1);
        videos.validateVideoPlays();
    }

    @Test
    public void openPaidVideoAndValidateItDoesntPlay() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.openVideosSubSection("Browse"); //need to select Browse from the navigation bar again, because if the user is logged in he is directly taken to All Red videos website when clicking Browse for the 1st time
        videos.openFullAccessVideo(1);
        videos.validateVideoDoesntPlay();
    }

    @Test
    public void openFreeVideoAndValidateCategoryName() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        String videoCategory = videos.getFreeVideoCategoryFromBrowsePage(1);
        videos.openFreeAccessVideo(1);
        videos.validateVideoCategory(videoCategory);
    }

    @Test
    public void openPaidVideoAndValidateCategoryName() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        String videoCategory = videos.getPaidVideoCategoryFromBrowsePage(1);
        videos.openFullAccessVideo(1);
        videos.validateVideoCategory(videoCategory);
    }
}
