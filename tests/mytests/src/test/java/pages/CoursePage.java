package pages;

import org.openqa.selenium.*;

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
