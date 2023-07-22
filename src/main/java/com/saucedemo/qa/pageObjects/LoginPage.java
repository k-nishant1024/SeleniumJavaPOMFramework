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
        PageFactory.initElements(driver, this);
        visit("https://www.saucedemo.com/");
    }

    public void verifyUserIsOnCheckoutCompletePage() {
        Assert.assertTrue(loginLogotext.getText().equalsIgnoreCase("Swag Labs"), "Useer is not on Login Page");
    }

    public void loginWith(String username, String password) {
        utility.type(usernameInput, username);
        utility.type(passwordInput, password);
        utility.click(loginButton);
    }

}

