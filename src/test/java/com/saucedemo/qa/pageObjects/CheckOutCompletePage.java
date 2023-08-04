package com.saucedemo.qa.pageObjects;


import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class CheckOutCompletePage extends BasePage {

    @FindBy(xpath = "//span[@class=\"title\" and text()=\"Checkout: Complete!\"]")
    @CacheLookup
    public WebElement pageHeaderText;

    @FindBy(xpath = "//div[@id=\"checkout_complete_container\"]/h2[text()=\"Thank you for your order!\"]")
    @CacheLookup
    public WebElement thankYouText;

    @FindBy(xpath = "//button[text()=\"Back Home\"]")
    @CacheLookup
    public WebElement backHomeButton;

    public CheckOutCompletePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("CheckOutCompletePage constructor initialized. Page elements initialized using PageFactory.");
    }

    public void verifyUserIsOnCheckoutCompletePage() {
        // Expected header text for the Checkout Complete page
        String expectedHeaderText = "Checkout: Complete!";

        // Get the actual header text from the page
        String actualHeaderText = pageHeaderText.getText();

        // Assertion to check if the user is on the Checkout Complete page
        Assert.assertTrue(actualHeaderText.equalsIgnoreCase(expectedHeaderText),
                "User is not on Checkout Complete Page. Expected header: " + expectedHeaderText + ", Actual header: " + actualHeaderText);

        // Logging information about the verification result
        if (actualHeaderText.equalsIgnoreCase(expectedHeaderText)) {
            // The verification passed; user is on the Checkout Complete page
            logger.info("User is on Checkout Complete Page. Header text matches: " + expectedHeaderText);
        } else {
            // The verification failed; user is not on the Checkout Complete page
            logger.info("User is NOT on Checkout Complete Page. Expected header: " + expectedHeaderText + ", Actual header: " + actualHeaderText);
        }
    }
}
