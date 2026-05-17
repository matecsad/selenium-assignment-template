package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.net.MalformedURLException;

import org.testng.annotations.*;
import org.testng.*;

import pages.*;
import utils.ConfigReader;

public class MySeleniumTest extends TestBase {

    @BeforeMethod
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
    public void courseCreation() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);
        
        MainPage mainPage = loginPage.clickLogin();
        CoursesPage coursesPage = mainPage.clickMyCourses();
        CourseCreatorPage courseCreatorPage = coursesPage.clickCreateCourse();

        Random rand  = new Random();
        int randomNum = rand.nextInt(1000);
        courseCreatorPage.typeCourseFullName("Selenium Testing Course " + randomNum);
        courseCreatorPage.typeCourseShortName("STC " + randomNum);

        List<WebElement> selectElements = courseCreatorPage.selectCourseVisibility(0);
        Assert.assertTrue(selectElements.get(0).isSelected());
        Assert.assertFalse(selectElements.get(1).isSelected());
        selectElements = courseCreatorPage.selectCourseVisibility(1);
        Assert.assertTrue(selectElements.get(1).isSelected());
        Assert.assertFalse(selectElements.get(0).isSelected());

        CoursePage coursePage = courseCreatorPage.submitCreateCourse();
        Assert.assertTrue(coursePage.getBodyText().contains("Selenium Testing Course " + randomNum));

        //Assert.assertTrue(courseCreatorPage.getBodyText().contains("Selenium Testing Course"));
    }

    @Test
    public void cookieHandling() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);

        MainPage mainPage = loginPage.clickLogin();
        Assert.assertTrue(mainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertFalse(mainPage.getBodyText().contains("Log in"));

        driver.manage().addCookie(new Cookie("TestCookie", "moodleTestCookie"));
        Cookie testCookie = driver.manage().getCookieNamed("TestCookie");
        Assert.assertEquals(testCookie.getValue(), "moodleTestCookie");

        Cookie sessionCookie = this.driver.manage().getCookieNamed("MoodleSessionelteseleniumtesting");
        Assert.assertNotNull(sessionCookie);

        driver.manage().deleteCookieNamed("MoodleSessionelteseleniumtesting");
        driver.navigate().refresh();

        Assert.assertFalse(mainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertTrue(mainPage.getBodyText().contains("Log in"));

    }


    @AfterMethod
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

}
