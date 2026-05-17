package tests;

import java.util.HashMap;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.Map;
import java.net.MalformedURLException;

import pages.LoginPage;
import utils.ConfigReader;

public class TestBase {

    protected WebDriver driver;

    protected void before() throws MalformedURLException {
        final Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        this.driver = new RemoteWebDriver(new URL(ConfigReader.get("selenium_url")), options);
        this.driver.manage().window().maximize();
    }

    protected void after() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    protected LoginPage login(LoginPage loginPage) {
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

}
