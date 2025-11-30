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
        String expectedTitle = home.getHomeArticleTitle(1);
        home.openArticleByIndex(1);
        articles.validateCorrectArticleTitle(expectedTitle);
    }

    @Test
    public void validateArticleType() {
        home.validateThereAreHomePageArticles();
        String expectedTitle = home.getHomeArticleTitle(1);
        home.openArticleByIndex(1);
        articles.validateCorrectArticleTitle(expectedTitle);
    }

    @Test
    public void validateArticlePublishedTime() {
        home.validateThereAreHomePageArticles();
        String expectedTime = home.getHomeArticleTime(1).toLowerCase();
        home.openArticleByIndex(1);
        articles.validateCorrectArticlePublishedTime(expectedTime);
    }

    @Test
    public void validateArticleImage() {
        home.validateThereAreHomePageArticles();
        String expectedImageAlt = home.getHomeImageAlt(1);
        home.openArticleByIndex(1);
        articles.validateCorrectImage(expectedImageAlt);
    }

}
