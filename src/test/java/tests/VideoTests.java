package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Test;

public class VideoTests extends BaseTestSetup {

    @Test
    public void openVideosPageAndValidateVideoCategories() {
        home.clickOnSection("Video");
        videos.validateAllCategories();
    }

    @Test
    public void openVideosPageAndValidateBrowseCategory() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.validateCurrentUrl();
        videos.validateCorrectCategoryIsOpened("Browse");
    }

    @Test
    public void openBrowseVideosPageAndValidateNumberOfResults() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.validateCurrentUrl();
        videos.validateNumberOfAllVideos(28000);
    }

    @Test
    public void openBrowseVideosPageAndValidateThereAre12Videos() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.validateCurrentUrl();
        videos.validateNumberOfVideosInTheList(12);
    }

    @Test
    public void openBrowseVideosPageAndValidateLoadMoreVideos() {
        home.clickOnSection("Video");
        videos.openVideosSubSection("Browse");
        videos.validateCurrentUrl();
        videos.loadMoreVideos();
        videos.validateNumberOfVideosInTheList(24);
    }
}
