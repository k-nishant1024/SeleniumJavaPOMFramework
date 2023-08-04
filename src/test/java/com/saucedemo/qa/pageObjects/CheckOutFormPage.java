package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class CheckOutFormPage extends BasePage {

    @FindBy(xpath = "//input[@id=\"first-name\"]")
    @CacheLookup
    public WebElement firstNameInput;

    @FindBy(xpath = "//input[@id=\"last-name\"]")
    @CacheLookup
    public WebElement LastNameInput;
    @FindBy(xpath = "//input[@id=\"postal-code\"]")
    @CacheLookup
    public WebElement zipInput;
    @FindBy(xpath = "//div[@class=\"checkout_buttons\"]/input[@id=\"continue\"]")
    @CacheLookup
    public WebElement continueButton;
    @FindBy(xpath = "//div[@class=\"checkout_buttons\"]/button[@id=\"cancel\"]")
    @CacheLookup
    public WebElement cancelButton;
    @FindBy(xpath = "//span[@class=\"title\" and text()=\"Checkout: Your Information\"]")
    @CacheLookup
    public WebElement pageHeaderText;

    public CheckOutFormPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void verifyUserIsOnCheckoutFormPage() {
        Assert.assertTrue(pageHeaderText.getText().equalsIgnoreCase("Checkout: Your Information"), "Useer is not on Checkout Form Page");
    }

    public void fillForm(String firstName, String lastName, int zipCode) {
        // Log the method entry with provided parameters
        logger.info("Filling the form with firstName: " + firstName + ", lastName: " + lastName + ", zipCode: " + zipCode);

        // Type the first name in the firstNameInput element
        utility.type(firstNameInput, firstName);

        // Type the last name in the lastNameInput element
        utility.type(LastNameInput, lastName);

        // Type the zip code as a string in the zipInput element
        utility.type(zipInput, Integer.toString(zipCode));

        // Log that the form filling is complete
        logger.info("Form filling completed for firstName: " + firstName + ", lastName: " + lastName + ", zipCode: " + zipCode);
    }

    public void clickOnContinueButton() {
        // Log that the "Continue" button is being clicked
        logger.info("Clicking on the 'Continue' button");

        // Click on the continueButton element using the utility method
        utility.click(continueButton);

        // Log that the click on the "Continue" button is completed
        logger.info("Clicked on the 'Continue' button");
    }

    public void clickOnCancelButton() {
        // Log that the "Cancel" button is being clicked
        logger.info("Clicking on the 'Cancel' button");

        // Click on the cancelButton element using the utility method
        utility.click(cancelButton);

        // Log that the click on the "Cancel" button is completed
        logger.info("Clicked on the 'Cancel' button");
    }
}

