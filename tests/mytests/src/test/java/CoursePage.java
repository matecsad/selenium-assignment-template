package test.java;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;
import org.openqa.selenium.support.ui.*;

import org.testng.annotations.*;
import org.testng.*;


public class CoursePage extends PageBase {

    private By createCourseButton = By.cssSelector("#newcourseform > button");
    
    public CoursePage(WebDriver driver)
    {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public CourseCreatorPage clickCreateCourse() {
        WebElement createCourseBtn = waitAndReturnElement(createCourseButton);
        createCourseBtn.click();

        return new CourseCreatorPage(this.driver);
    }

}
