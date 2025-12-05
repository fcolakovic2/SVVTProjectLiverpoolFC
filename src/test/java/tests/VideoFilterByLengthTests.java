package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Test;

public class VideoFilterByLengthTests extends BaseTestSetup {
    @Test
    public void filterShortVideos() throws InterruptedException {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.openVideoLengthFilter();
        videos.selectVideoLengthFilter("Short");
        videos.validateAllVideoTitlesHaveSetLength("Short");
    }

    @Test
    public void filterMediumLengthVideos() throws InterruptedException {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.openVideoLengthFilter();
        videos.selectVideoLengthFilter("Medium");
        videos.validateAllVideoTitlesHaveSetLength("Medium");
    }

    @Test
    public void filterLongVideos() throws InterruptedException {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.closeMarketingPopUp();
        videos.openVideoLengthFilter();
        videos.selectVideoLengthFilter("Long");
        videos.validateAllVideoTitlesHaveSetLength("Long");
    }
}
