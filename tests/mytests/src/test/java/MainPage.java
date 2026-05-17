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


public class MainPage extends PageBase {
    private By userMenuToggle = By.id("user-menu-toggle");
    private By logoutButton = By.linkText("Log out");
    private By myCoursesButton = By.xpath("/html/body/div[2]/nav/div/div[1]/nav/ul/li[3]/a");
    
    
    public MainPage(WebDriver driver)
    {
        super(driver);
        waitAndReturnElement(bodyLocator);
    }

    public void toggleUserMenu() {
        WebElement userMenu = waitAndReturnElement(userMenuToggle);
        userMenu.click();
    }

    public void clickLogout() {
        WebElement logoutBtn = waitAndReturnElement(logoutButton);
        logoutBtn.click();
    }

    public CoursesPage clickMyCourses() {
        WebElement myCoursesBtn = waitAndReturnElement(myCoursesButton);
        myCoursesBtn.click();

        return new CoursesPage(this.driver);
    }

}
