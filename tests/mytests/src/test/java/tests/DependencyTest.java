package tests;

import java.net.MalformedURLException;

import org.testng.annotations.*;
import org.testng.*;

import pages.LoginPage;
import pages.MainPage;

public class DependencyTest extends TestBase {

    private MainPage sharedMainPage;
    
    @BeforeClass
    public void Setup() throws MalformedURLException
    {
        before();
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
        after();
    }

}
