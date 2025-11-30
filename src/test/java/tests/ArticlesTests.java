package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArticlesTests extends BaseTestSetup {

    @BeforeAll
    public static void setCookiesAndLogin(){
        setLoggedIn(false);
        setCookiesHandled(false);
    }

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

}
