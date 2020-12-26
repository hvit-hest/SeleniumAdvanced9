package org.courses.tests;

import org.courses.pages.mainpage.MainPage;
import org.courses.utils.Utils;
import org.courses.utils.WebDriverSelection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class StickersTest {

    private WebDriver driver;
    private String userEmail;
    private String userPassword;
    private MainPage mainPage;

    @BeforeClass
    public void beforeClass() {
        userPassword = new Utils().getTestProperties().getProperty("userPassword");
        userEmail = new Utils().getTestProperties().getProperty("userEmail");
        driver = new WebDriverSelection().getDriverFromProperties();
        mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.login(userEmail, userPassword);
    }

    @Test
    public void stickerExistsSimpleTest() {
        String searchStickerCSS = "[class^=sticker]";
        mainPage.getDuckProducts().forEach( we -> {
            List<WebElement> stickersPerDuck = we.findElements(By.cssSelector(searchStickerCSS));
            Assert.assertTrue(stickersPerDuck.size()==1 &&
                    !stickersPerDuck.get(0).getText().trim().isEmpty());
        });
    }

    @Test
    public void stickerExistsWithSoftAssertTest() {
        String searchStickerCSS = "[class^=sticker]";
        SoftAssert softAssertion = new SoftAssert();
        mainPage.getDuckProducts().forEach( we -> {
            List<WebElement> stickersPerDuck = we.findElements(By.cssSelector(searchStickerCSS));
            softAssertion.assertTrue(stickersPerDuck.size()==1 &&
                    !stickersPerDuck.get(0).getText().trim().isEmpty() );
        });
                softAssertion.assertAll("Test did not pass");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
