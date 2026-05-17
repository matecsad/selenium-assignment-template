package pages;

import org.openqa.selenium.*;

public class LoginPage extends PageBase {
    private By userInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.id("loginbtn");
    private By acceptCookiesButton = By.id("onetrust-accept-btn-handler");
    
    public LoginPage(WebDriver driver, String url)
    {
        super(driver);
        this.driver.get(url);
    }

    public void acceptCookies() {
        WebElement acceptCookiesBtn = waitAndReturnElement(acceptCookiesButton);
        acceptCookiesBtn.click();
    }

    public MainPage clickLogin() {
        WebElement btn = waitAndReturnElement(loginButton);
        btn.click();

        return new MainPage(this.driver);
    }

    public void typeIntoUsername(String username) {
        WebElement usernameElement = waitAndReturnElement(userInput);
        usernameElement.sendKeys(username);
    }

    public void typeIntoPassword(String password) {
        WebElement passwordElement = waitAndReturnElement(passwordInput);
        passwordElement.sendKeys(password);
    }

}
