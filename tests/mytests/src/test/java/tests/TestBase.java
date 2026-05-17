package tests;

import org.openqa.selenium.*;

import pages.LoginPage;
import utils.ConfigReader;

public class TestBase {

    protected WebDriver driver;

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
