package tests;

import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;

import org.testng.annotations.*;
import org.testng.*;

import pages.LoginPage;
import pages.MainPage;
import utils.ConfigReader;

public class DependencyTest extends TestBase {

    private MainPage sharedMainPage;
    
    @BeforeClass
    public void Setup() throws MalformedURLException
    {
        final Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        this.driver = new RemoteWebDriver(new URL(ConfigReader.get("selenium_url")), options);
        this.driver.manage().window().maximize();
    }

    @Test
    public void loginTest() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);

        sharedMainPage =loginPage.clickLogin();
        Assert.assertTrue(sharedMainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertFalse(sharedMainPage.getBodyText().contains("Log in"));
        Assert.assertEquals("Dashboard | ELTESelTest", sharedMainPage.getTitle());
    }

    @Test (dependsOnMethods = { "loginTest" })
    public void logoutTest() {
        sharedMainPage.toggleUserMenu();
        sharedMainPage.clickLogout();
        Assert.assertTrue(sharedMainPage.getBodyText().contains("Log in"));
    }


    @AfterClass
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

}
