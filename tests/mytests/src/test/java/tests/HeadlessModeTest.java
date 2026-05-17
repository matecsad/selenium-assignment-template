package tests;

import java.net.MalformedURLException;

import org.testng.annotations.*;
import org.testng.*;

import pages.LoginPage;
import pages.MainPage;

public class HeadlessModeTest extends TestBase {

    @BeforeClass
    public void Setup() throws MalformedURLException
    {
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("--lang=en-US");
        before();
    }

    @Test
    public void headlessModeTest() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);

        MainPage mainPage = loginPage.clickLogin();
        Assert.assertTrue(mainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertFalse(mainPage.getBodyText().contains("Log in"));
        Assert.assertEquals("Dashboard | ELTESelTest", mainPage.getTitle());

        System.out.println("Headless mode test completed.");
    }

    @AfterClass
    public void close() {
        after();
    }

}
