package com.saucedemo.qa.tests;

import com.saucedemo.qa.pageObjects.*;
import com.saucedemo.qa.utils.Utility;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.testng.Assert.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginPageTests {

    WebDriver driver;
    LoginPage login;

    Utility utility;
    ProductPage product;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        product = new ProductPage(driver);
        login = new LoginPage(driver);
        utility = new Utility(driver);
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        utility.quit();
    }

    @Test
    public void TC01_verifyThatTheLoginPageLoadsSuccessfully() {
        assertEquals(utility.getPageTitle(), "Swag Labs", "Login page did not load sucessfully.");
    }

    @Test
    public void TC02_verifyThatUserIsAbleToLoginWithValidCredentials() {
        login.loginWith(login.standarUser, login.password);
        assertTrue(product.inventoryContainer.isDisplayed(), "Login Failed, Products page not launched");
    }

    @Test
    public void TC03_verifyThatUserIsNotAbleToLoginWithInValidCredentials() {
        login.loginWith("Invalid_username", "invalid_paassword");
        assertTrue(login.errorText.isDisplayed(), "User able to Login with Invalid Credentials");
    }

    @Test
    public void TC04_testUsernameFieldErrorMessageDisplayedForBlankUsername() {
        utility.type(login.usernameInput, "");
        utility.type(login.passwordInput, login.password);
        utility.click(login.loginButton);
        utility.assertElementDisplayed(login.usernameFieldEmptyError);
    }

    @Test
    public void TC05_testPasswordFieldErrorMessageDisplayedForBlankPassword() {
        utility.type(login.usernameInput, login.standarUser);
        utility.type(login.passwordInput, "");
        utility.click(login.loginButton);
        utility.assertElementDisplayed(login.passwordFieldEmptyError);
    }

    @Test
    public void TC06_testLoginWithLockedOutUserAccountAndVerifyErrorMessage() {
        utility.type(login.usernameInput, login.lockedOutUser);
        utility.type(login.passwordInput, login.password);
        utility.click(login.loginButton);
        String expectedErrMsg = "Epic sadface: Sorry, this user has been locked out.";
        utility.assertTextEquals(login.lockedOutuserError, expectedErrMsg);
    }

    @Test
    public void TC07_testLoginFormResponsivenessOnDifferentScreenSizes() {
        for (int width = 100; width <= 1920; width += 100) {
            for (int height = 100; height <= 1080; height += 100) {
                driver.manage().window().setSize(new Dimension(width, height));

                // Verify that the login form still displays correctly and all of the elements
                // are still clickable.
                assertTrue(login.usernameInput.isDisplayed());
                assertTrue(login.passwordInput.isDisplayed());
                assertTrue(login.loginButton.isDisplayed());
            }
        }
    }

    @Test
    public void TC08_testSwitchBetweenUsernameAndPasswordFieldsUsingTabKey() {
        utility.sendKeys(login.usernameInput, Keys.TAB);
        assertTrue(login.passwordInput.equals(utility.switchToActiveElement()));
        utility.sendKeys(login.passwordInput, Keys.TAB);
        assertTrue(login.loginButton.equals(utility.switchToActiveElement()));
    }

    public void TC09_accessabilityTest() {

    }

    public void TC10_crossBrowserTesting() {

    }

}


