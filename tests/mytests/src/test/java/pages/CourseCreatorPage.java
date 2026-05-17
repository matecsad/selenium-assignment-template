package pages;

import org.openqa.selenium.*;
import java.util.List;
import org.openqa.selenium.support.ui.*;

public class CourseCreatorPage extends PageBase {
    private By courseNameInput = By.cssSelector("#id_fullname");
    private By courseShortNameInput = By.cssSelector("#id_shortname");
    private By visibilitySelect = By.cssSelector("#id_visible");
    //private By hoverable = By.cssSelector("#fitem_id_fullname > div.col-md-3.col-form-label.d-flex.pb-0.pe-md-0 > div > a > i");
    private By createCourseButton = By.cssSelector("#id_saveanddisplay");

    public CourseCreatorPage(WebDriver driver)
    {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public void typeCourseFullName(String courseName) {
        WebElement courseNameElement = waitAndReturnElement(courseNameInput);
        courseNameElement.sendKeys(courseName);
    }

    public void typeCourseShortName(String courseShortName) {
        WebElement courseShortNameElement = waitAndReturnElement(courseShortNameInput);
        courseShortNameElement.sendKeys(courseShortName);
    }

    public List<WebElement> selectCourseVisibility(int index) {
        WebElement visibilitySelectElement = waitAndReturnElement(visibilitySelect);
        Select select = new Select(visibilitySelectElement);

        select.selectByIndex(index);

        return select.getOptions();
    }

    public CoursePage submitCreateCourse() {
        WebElement createCourseBtn = waitAndReturnElement(createCourseButton);
        createCourseBtn.submit();

        return new CoursePage(this.driver);
    }

}
