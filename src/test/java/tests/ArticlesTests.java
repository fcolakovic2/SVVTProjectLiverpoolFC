package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArticlesTests extends BaseTestSetup {

    @Test
    public void validateThereIsAtLeast1Article() {
      home.validateThereAreHomePageArticles();
    }

    @Test
    public void validateArticleTitle() {
        home.validateThereAreHomePageArticles();
        String expectedTitle = home.getHomeArticleTitle(1, true);
        home.openArticleByIndex(1, true);
        articles.validateCorrectArticleTitle(expectedTitle);
    }

    @Test
    public void validateArticleType() {
        home.validateThereAreHomePageArticles();
        String expectedType = home.getHomeArticleType(1);
        home.openArticleByIndex(1, true);
        articles.validateCorrectArticleType(expectedType);
    }

    @Test
    public void validateArticlePublishedTime() {
        home.validateThereAreHomePageArticles();
        String expectedTime = home.getHomeArticleTime(1).toLowerCase();
        home.openArticleByIndex(1, true);
        articles.validateCorrectArticlePublishedTime(expectedTime);
    }

    @Test
    public void validateArticleImage() {
        home.validateThereAreHomePageArticles();
        String expectedImageAlt = home.getHomeImageAlt(1);
        home.openArticleByIndex(1, false);
        articles.validateCorrectImage(expectedImageAlt);
    }

}
