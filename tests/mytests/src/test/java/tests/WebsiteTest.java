package tests;

import org.openqa.selenium.*;
import java.util.List;
import java.util.Random;
import java.net.MalformedURLException;
import java.util.UUID;

import org.testng.annotations.*;
import org.testng.*;

import pages.*;

public class WebsiteTest extends TestBase {

    @BeforeMethod
    public void Setup() throws MalformedURLException
    {
        before();
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
        UUID uniqueID = UUID.randomUUID();
        courseCreatorPage.typeCourseFullName("Selenium Testing Course " + randomNum + " " );
        courseCreatorPage.typeCourseShortName(uniqueID.toString());

        List<WebElement> selectElements = courseCreatorPage.selectCourseVisibility(0);
        Assert.assertTrue(selectElements.get(0).isSelected());
        Assert.assertFalse(selectElements.get(1).isSelected());
        selectElements = courseCreatorPage.selectCourseVisibility(1);
        Assert.assertTrue(selectElements.get(1).isSelected());
        Assert.assertFalse(selectElements.get(0).isSelected());

        CoursePage coursePage = courseCreatorPage.submitCreateCourse();
        Assert.assertTrue(coursePage.getBodyText().contains("Selenium Testing Course " + randomNum));

       System.out.println("Course creation test completed.");

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

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(mainPage.getBodyText().contains("Hi, Elte!"));
        Assert.assertTrue(mainPage.getBodyText().contains("Log in"));

        System.out.println("Cookie handling test completed.");
    }


    @AfterMethod
    public void close() {
        after();
    }

}
