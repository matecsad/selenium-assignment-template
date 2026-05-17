package tests;

import java.net.MalformedURLException;

import org.testng.annotations.*;
import org.testng.*;

import pages.*;

public class ScreenshotTest extends TestBase {

    @BeforeMethod
    public void Setup() throws MalformedURLException
    {
        before();
    }

    @Test
    public void screenshotTest() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);
        
        MainPage mainPage = loginPage.clickLogin();

        Assert.assertTrue(mainPage.getBodyText().contains("This text will surely not be found on the page!"));

    }

    @AfterMethod
    public void close() {
        after();
    }

}
