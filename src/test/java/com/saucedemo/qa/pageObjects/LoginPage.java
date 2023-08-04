package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id=\"user-name\"]")
    @CacheLookup
    public WebElement usernameInput;

    @FindBy(xpath = "//h3[text()=\"Epic sadface: Username is required\"]")
    @CacheLookup
    public WebElement usernameFieldEmptyError;

    @FindBy(xpath = "//h3[contains(text(),\"locked out\")]")
    @CacheLookup
    public WebElement lockedOutuserError;

    @FindBy(xpath = "//input[@id=\"password\"]")
    @CacheLookup
    public WebElement passwordInput;

    @FindBy(xpath = "//h3[text()=\"Epic sadface: Password is required\"]")
    @CacheLookup
    public WebElement passwordFieldEmptyError;

    @FindBy(xpath = "//input[@id=\"login-button\"]")
    @CacheLookup
    public WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test=\"error\"]")
    @CacheLookup
    public WebElement errorText;

    @FindBy(xpath = "//div[@class=\"bm-burger-button\"]/button[text()=\"Open Menu\"]")
    @CacheLookup
    public WebElement burgerMenuButton;

    @FindBy(xpath = "//a[text()=\"Logout\"]")
    @CacheLookup
    public WebElement logout_link;

    @FindBy(xpath = "//div[@class=\"login_logo\" and text()=\"Swag Labs\"]")
    @CacheLookup
    public WebElement loginLogotext;

    public String standarUser = "standard_user";
    public String lockedOutUser = "locked_out_user";
    public String problemUser = "problem_user";
    public String performanceGlitchUser = "performance_glitch_user";
    public String password = "secret_sauce";

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("Initializing LoginPage with the provided WebDriver instance");
        logger.info("Initializing page elements using PageFactory.initElements(driver, this)");
        PageFactory.initElements(driver, this);
        logger.info("Navigating to the home page");
        visit("https://www.saucedemo.com/");
    }

    public void verifyUserIsOnLoginPage() {
        // Verify if the login logo text matches the expected text "Swag Labs"
        if (loginLogotext.getText().equalsIgnoreCase("Swag Labs")) {
            logger.info("User is on Login Page. Login logo text is: " + loginLogotext.getText());
        } else {
            logger.error("User is not on Login Page. Expected: 'Swag Labs', Actual: '" + loginLogotext.getText() + "'");
            Assert.fail("User is not on Login Page. Expected: 'Swag Labs', Actual: '" + loginLogotext.getText() + "'");
        }

    }

    public void loginWith(String username, String password) {
        logger.info("Performing login with username: " + username + " and password: " + maskPassword(password));
        // Type the username and password, and click the login button
        utility.type(usernameInput, username);
        utility.type(passwordInput, password);
        utility.click(loginButton);
    }

    private String maskPassword(String password) {
        StringBuilder maskedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            maskedPassword.append("*");
        }
        return maskedPassword.toString();
    }

}

