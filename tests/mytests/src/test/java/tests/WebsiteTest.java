package tests;

import org.openqa.selenium.*;
import java.util.List;
import java.util.Random;
import java.net.MalformedURLException;
import java.util.UUID;

import org.testng.annotations.*;
import org.testng.*;

import pages.*;
import utils.ConfigReader;

public class WebsiteTest extends TestBase {

    @BeforeMethod
    public void Setup() throws MalformedURLException
    {
        before();
    }

    @Test
    public void courseCreationTest() {
        LoginPage loginPage = null;
        
        loginPage = login(loginPage);
        
        MainPage mainPage = loginPage.clickLogin();
        CoursesPage coursesPage = mainPage.clickMyCourses();
        CourseCreatorPage courseCreatorPage = coursesPage.clickCreateCourse();

        Random rand  = new Random();
        int randomNum = rand.nextInt(1000);
        UUID uniqueID = UUID.randomUUID();
        courseCreatorPage.typeCourseFullName("Selenium Testing Course " + randomNum + " " );
        courseCreatorPage.typeCourseShortName(uniqueID.toString());

        List<WebElement> selectElements = courseCreatorPage.selectCourseVisibility(0);
        Assert.assertTrue(selectElements.get(0).isSelected());
        Assert.assertFalse(selectElements.get(1).isSelected());
        selectElements = courseCreatorPage.selectCourseVisibility(1);
        Assert.assertTrue(selectElements.get(1).isSelected());
        Assert.assertFalse(selectElements.get(0).isSelected());

        WebElement checkboxElement = courseCreatorPage.clickEndDateEnabledCheckbox();
        Assert.assertFalse(checkboxElement.isSelected());

        CoursePage coursePage = courseCreatorPage.submitCreateCourse();
        Assert.assertTrue(coursePage.getBodyText().contains("Selenium Testing Course " + randomNum));

       System.out.println("Course creation test completed.");

        //Assert.assertTrue(courseCreatorPage.getBodyText().contains("Selenium Testing Course"));
    }

    @Test
    public void cookieHandlingTest() {
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

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(mainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertTrue(mainPage.getBodyText().contains("Log in"));

        System.out.println("Cookie handling test completed.");
    }

    @Test
    public void arrayofUrlsTest() {
        LoginPage loginPage = null;
        loginPage = login(loginPage);
        loginPage.clickLogin();
        List<String> urls = ConfigReader.getList("array_of_urls");
        List<String> urlContents = ConfigReader.getList("array_of_urls_contents");
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            driver.navigate().to(url);
            PageBase page = new PageBase(driver);
            Assert.assertTrue(page.getBodyText().contains(urlContents.get(i)));
        }

        System.out.println("Array of URLs test completed.");
    }

    @Test
    public void historyTest() {
        LoginPage loginPage = null;
        loginPage = login(loginPage);
        MainPage mainPage = loginPage.clickLogin();

        Assert.assertTrue(mainPage.getBodyText().contains("Hi, Elte!"));
        CoursesPage coursesPage = mainPage.clickMyCourses();
        Assert.assertTrue(coursesPage.getBodyText().contains("My courses"));
        driver.navigate().back();
        Assert.assertTrue(mainPage.getBodyText().contains("Hi, Elte!"));
        driver.navigate().forward();
        Assert.assertTrue(coursesPage.getBodyText().contains("My courses"));

        System.out.println("History test completed.");
    }


    @AfterMethod
    public void close() {
        after();
    }

}
