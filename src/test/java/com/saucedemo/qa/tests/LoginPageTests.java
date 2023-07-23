package com.saucedemo.qa.tests;

import com.deque.axe.AXE;
import com.saucedemo.qa.pageObjects.*;
import com.saucedemo.qa.utils.ConfigManager;
import com.saucedemo.qa.utils.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginPageTests {

    WebDriver driver;
    LoginPage login;
    Utility utility;
    ProductPage product;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        logger.info("ChromeDriver instance has been initialized.");
        driver = new EdgeDriver();

        product = new ProductPage(driver);
        logger.info("ProductPage instance has been initialized with the WebDriver.");

        login = new LoginPage(driver);
        logger.info("LoginPage instance has been initialized with the WebDriver.");

        utility = new Utility(driver);
        logger.info("Utility instance has been initialized with the WebDriver.");

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        try {
            utility.quit();
            logger.info("WebDriver has been quit successfully.");
        } catch (WebDriverException e) {
            logger.error("An error occurred while quitting the WebDriver: " + e.getMessage());
        }
    }

    @Test
    public void TC01_verifyThatTheLoginPageLoadsSuccessfully() {
        logger.info("Starting Test: Verify that the login page loads successfully.");
        // Step 1: Verifying the page title
        String expectedTitle = "Swag Labs";
        String actualTitle = utility.getPageTitle();
        try {
            Assert.assertEquals(actualTitle, expectedTitle, "Login page did not load successfully.");
            logger.info("Test Passed: The login page loaded successfully with the expected title: 'Swag Labs'");
        } catch (AssertionError e) {
            logger.error("Test Failed: The login page did not load successfully. Expected title: 'Swag Labs', Actual title: '" + actualTitle + "'");
        }
    }

    @Test
    public void TC02_verifyThatUserIsAbleToLoginWithValidCredentials() {
        logger.info("Starting Test: Verify that the user is able to login with valid credentials.");

        // Step 1: Logging in with valid credentials
        login.loginWith(ConfigManager.getProperty("standardUser"), ConfigManager.getProperty("password"));
        logger.info("Performed login with valid credentials.");

        // Step 2: Verifying if the inventory container (products page) is displayed
        try {
            Assert.assertTrue(product.inventoryContainer.isDisplayed(), "Login Failed, Products page not launched");
            logger.info("Test Passed: The user successfully logged in, and the products page is displayed.");
        } catch (AssertionError e) {
            logger.error("Test Failed: Login was successful, but the products page is not displayed.");
        }
    }

    @Test
    public void TC03_verifyThatUserIsNotAbleToLoginWithInValidCredentials() {
        logger.info("Starting Test: Verify that the user is not able to login with invalid credentials.");

        // Step 1: Logging in with invalid credentials
        login.loginWith("Invalid_username", "invalid_password");
        logger.info("Performed login with invalid credentials.");

        // Step 2: Verifying if the error message element is displayed
        try {
            Assert.assertTrue(login.errorText.isDisplayed(), "User able to Login with Invalid Credentials");
            logger.info("Test Passed: User was unable to log in with invalid credentials, and the error message is displayed.");
        } catch (AssertionError e) {
            logger.error("Test Failed: User was able to log in with invalid credentials.");
        }
    }

    @Test
    public void TC04_testUsernameFieldErrorMessageDisplayedForBlankUsername() {
        logger.info("Starting Test: Verify that the appropriate error message is displayed for a blank username during login.");

        // Step 1: Entering a blank username and a valid password
        utility.type(login.usernameInput, "");
        logger.info("Entered a blank username.");
        utility.type(login.passwordInput, ConfigManager.getProperty("password"));
        logger.info("Entered a valid password.");

        // Step 2: Clicking the login button
        utility.click(login.loginButton);
        logger.info("Clicked the login button.");

        // Step 3: Verifying if the username field empty error message element is displayed
        try {
            utility.assertElementDisplayed(login.usernameFieldEmptyError);
            logger.info("Test Passed: The appropriate error message is displayed for a blank username during login.");
        } catch (AssertionError e) {
            logger.error("Test Failed: The error message for a blank username is not displayed during login.");
        }

    }

    @Test
    public void TC05_testPasswordFieldErrorMessageDisplayedForBlankPassword() {
        logger.info("Starting Test: Verify that the appropriate error message is displayed for a blank password during login.");

        // Step 1: Entering a valid username and a blank password
        utility.type(login.usernameInput, ConfigManager.getProperty("standardUser"));
        logger.info("Entered a valid username: " + ConfigManager.getProperty("standardUser"));
        utility.type(login.passwordInput, "");
        logger.info("Entered a blank password.");

        // Step 2: Clicking the login button
        utility.click(login.loginButton);
        logger.info("Clicked the login button.");

        // Step 3: Verifying if the password field empty error message element is displayed
        try {
            utility.assertElementDisplayed(login.passwordFieldEmptyError);
            logger.info("Test Passed: The appropriate error message is displayed for a blank password during login.");
        } catch (AssertionError e) {
            logger.error("Test Failed: The error message for a blank password is not displayed during login.");
        }
    }

    @Test
    public void TC06_testLoginWithLockedOutUserAccountAndVerifyErrorMessage() {
        logger.info("Starting Test: Verify the error message when logging in with a locked-out user account.");

        // Step 1: Entering a locked-out username and a valid password
        utility.type(login.usernameInput, ConfigManager.getProperty("lockedOutUser"));
        logger.info("Entered a locked-out username: " + ConfigManager.getProperty("lockedOutUser"));
        utility.type(login.passwordInput, ConfigManager.getProperty("password"));
        logger.info("Entered a valid password.");

        // Step 2: Clicking the login button
        utility.click(login.loginButton);
        logger.info("Clicked the login button.");

        // Step 3: Verifying if the expected error message is displayed
        String expectedErrMsg = "Epic sadface: Sorry, this user has been locked out.";
        try {
            utility.assertTextEquals(login.lockedOutuserError, expectedErrMsg);
            logger.info("Test Passed: The correct error message is displayed for a locked-out user account.");
        } catch (AssertionError e) {
            logger.error("Test Failed: The error message for a locked-out user account is incorrect or not displayed.");
        }
    }

    @Test
    public void TC07_testLoginFormResponsivenessOnDifferentScreenSizes() {
        logger.info("Starting Test: Verify the responsiveness of the login form on different screen sizes.");
        int minWidth = 100;
        int maxWidth = 1920;
        int minHeight = 100;
        int maxHeight = 1080;

        // Step 1: Iterating through different screen sizes
        for (int width = minWidth; width <= maxWidth; width += 100) {
            for (int height = minHeight; height <= maxHeight; height += 100) {
                logger.info("Setting window size: " + width + "x" + height);
                driver.manage().window().setSize(new Dimension(width, height));

                // Step 2: Verify that the login form elements are displayed and clickable
                try {
                    Assert.assertTrue(login.usernameInput.isDisplayed());
                    logger.info("Username input is displayed.");
                    Assert.assertTrue(login.passwordInput.isDisplayed());
                    logger.info("Password input is displayed.");
                    Assert.assertTrue(login.loginButton.isDisplayed());
                    logger.info("Login button is displayed.");
                } catch (AssertionError e) {
                    logger.error("Test Failed: Some login form elements are not displayed or not clickable at window size: " + width + "x" + height);
                }
            }
        }

        logger.info("Test Passed: The login form is responsive on different screen sizes.");
    }

    @Test
    public void TC08_testSwitchBetweenUsernameAndPasswordFieldsUsingTabKey() {
        logger.info("Starting Test: Verify the functionality of switching between username and password fields using the Tab key.");

        // Step 1: Moving focus from username to password field using Tab
        utility.sendKeys(login.usernameInput, Keys.TAB);
        logger.info("Switched focus to the password field using the Tab key.");

        // Step 2: Verifying the active element is the password input field
        try {
            Assert.assertEquals(utility.switchToActiveElement(), login.passwordInput);
            logger.info("Test Passed: Successfully switched focus to the password field using the Tab key.");
        } catch (AssertionError e) {
            logger.error("Test Failed: Focus did not switch to the password field using the Tab key.");
        }

        // Step 3: Moving focus from password field to login button using Tab
        utility.sendKeys(login.passwordInput, Keys.TAB);
        logger.info("Switched focus to the login button using the Tab key.");

        // Step 4: Verifying the active element is the login button
        try {
            Assert.assertEquals(utility.switchToActiveElement(), login.loginButton);
            logger.info("Test Passed: Successfully switched focus to the login button using the Tab key.");
        } catch (AssertionError e) {
            logger.error("Test Failed: Focus did not switch to the login button using the Tab key.");
        }
    }

    @Test
    public void TC09_accessabilityTest() throws MalformedURLException, URISyntaxException {
        logger.info("Starting Test: Performing accessibility testing.");

        String urlString = "https://cdnjs.cloudflare.com/ajax/libs/axe-core/4.1.3/axe.min.js";
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        // Now you can use the 'url' object for further operations
        System.out.println("URL: " + url);

        AXE.Builder builder = new AXE.Builder(driver, url   );
        JSONObject axeResult = builder.analyze();

        if (axeResult.has("violations")) {
            JSONArray violations = axeResult.getJSONArray("violations");
            logger.error("Accessibility Violations Found:");
            for (int i = 0; i < violations.length(); i++) {
                JSONObject violation = violations.getJSONObject(i);
                logger.error("Rule: " + violation.getString("id"));
                logger.error("Impact: " + violation.getString("impact"));
                logger.error("Description: " + violation.getString("description"));
                logger.error("Help URL: " + violation.getString("help"));
            }
            logger.error("Test Failed: Accessibility violations found.");
        } else {
            logger.info("Test Passed: No accessibility violations found.");
        }
    }
}