package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Test;

public class VideosSearchTests extends BaseTestSetup {

    @Test
    public void searchVideosByValidInputWithResults() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.enterSearchVideosValue("Wirtz");
        videos.validateAllVideoTitlesContainInputString("Wirtz");
    }

    @Test
    public void searchVideosByValidInputWithNoResults() throws InterruptedException {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.enterSearchVideosValue("fariscolakovic");
        videos.validateExactNumberOfAllVideos(0);
    }

    @Test
    public void searchVideosByInputWithLeadingAndTrailingSpaces() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.enterSearchVideosValue(" Isak ");
        videos.validateAllVideoTitlesContainInputString("Isak");
    }

    @Test
    public void searchVideosByInputWithCaseSensitivity() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.enterSearchVideosValue("WiRtZ");
        videos.validateAllVideoTitlesContainInputString("Wirtz");
    }

    @Test
    public void searchVideosByInputWithSpecialCharacters() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.enterSearchVideosValue("Wirtz!!");
        videos.validateAllVideoTitlesContainInputString("Wirtz");
    }
}
