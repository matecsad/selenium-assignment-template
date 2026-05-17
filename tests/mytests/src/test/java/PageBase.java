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


public class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected By bodyLocator = By.tagName("body");
    
    public PageBase(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected String getBodyText() {
        WebElement bodyElement = waitAndReturnElement(bodyLocator);
        return bodyElement.getText();
    }
    
    public String getTitle() {
        return this.driver.getTitle();
    }

}
