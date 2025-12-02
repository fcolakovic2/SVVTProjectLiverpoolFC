package tests;

import base.BaseTestSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnforceHTTPSTests extends BaseTestSetup {

    @Test
    public void testHttpHomepageRedirectsToHttps() throws Exception {
        driver.get("http://www.liverpoolfc.com");
        String current = driver.getCurrentUrl();
        Assertions.assertTrue(current.startsWith("https://"),
                "Expected redirect to https but was: " + current);
    }

    @Test
    public void testHttpNewsRedirectsToHttps() throws Exception {
        driver.get("http://www.liverpoolfc.com/news");
        String current = driver.getCurrentUrl();
        Assertions.assertTrue(current.startsWith("https://"),
                "Expected video browse to redirect to https but was: " + current);
    }

    @Test
    public void testHttpFixturesRedirectsToHttps() throws Exception {
        driver.get("http://video.liverpoolfc.com/fixtures");
        String current = driver.getCurrentUrl();
        Assertions.assertTrue(current.startsWith("https://"),
                "Expected video browse to redirect to https but was: " + current);
    }

    @Test
    public void testHttpShopRedirectsToHttps() throws Exception {
        driver.get("http://video.liverpoolfc.com/shop");
        String current = driver.getCurrentUrl();
        Assertions.assertTrue(current.startsWith("https://"),
                "Expected video browse to redirect to https but was: " + current);
    }
}
