package test.java;




import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.beans.Transient;
import java.net.MalformedURLException;
import org.openqa.selenium.support.ui.*;

import org.testng.annotations.*;
import org.testng.*;

public class DependencyTest {

    private WebDriver driver;
    private MainPage sharedMainPage;

    private LoginPage login(LoginPage loginPage) {
        loginPage = new LoginPage(this.driver, ConfigReader.get("moodle_url"));
        
        try {
            loginPage.acceptCookies();
        }
        catch (Exception e){
            System.out.println("Accept cookies button not found, proceeding without accepting cookies.");
        }

        loginPage.typeIntoUsername(ConfigReader.get("login_username"));
        loginPage.typeIntoPassword(ConfigReader.get("login_password"));

        return loginPage;
    }
    
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
