package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class InventoryItemPage extends BasePage {

    @FindBy(xpath = "//button[@id=\"back-to-products\" and text() = \"Back to products\"]")
    @CacheLookup
    public WebElement pageHeaderText;

    @FindBy(xpath = "//div[contains(@class, \"inventory_details_name\")]")
    @CacheLookup
    public WebElement itemNameText;

    @FindBy(xpath = "//div[@class=\"inventory_details_name large_size\"]")
    @CacheLookup
    public WebElement itemDescriptionText;

    @FindBy(xpath = "//div[contains(@class, \"inventory_details_price\")]")
    @CacheLookup
    public WebElement itemPriceText;

    @FindBy(xpath = "//button[text()=\"Add to cart\"]")
    @CacheLookup
    public WebElement addItemToCartButton;

    @FindBy(xpath = "//button[text()=\"Remove\"]")
    @CacheLookup
    public WebElement removeItemFromCartButton;

    @FindBy(xpath = "//div[@id=\"shopping_cart_container\" and @class=\"shopping_cart_container\"]")
    @CacheLookup
    public WebElement cartButton;

    public InventoryItemPage(WebDriver driver) {
        // Call the constructor of the parent class (BasePage) with the provided driver
        super(driver);

        // Initialize page elements using PageFactory
        PageFactory.initElements(driver, this);

        // Log that the ProductDetailsPage constructor is initialized
        logger.info("InventoryItemPage constructor initialized. Page elements initialized using PageFactory.");

    }

    public void verifyUserIsOnInventoryItemPage() {
        String expectedHeaderText = "Back to products";
        String actualHeaderText = pageHeaderText.getText();

        // Log the actual header text to provide additional information for debugging purposes.
        logger.info("Actual header text: " + actualHeaderText);

        // Compare the actual header text with the expected value in a case-insensitive manner.
        // If the header text matches, the user is on the correct page; otherwise, the test will fail.
        Assert.assertTrue(actualHeaderText.equalsIgnoreCase(expectedHeaderText), "User is not on the Inventory Item page.");
    }

    public void verifyItemName(String itemName) {
        // Log the expected item name and the actual item name obtained from the page.
        logger.info("Expected item name: " + itemName);
        logger.info("Actual item name: " + itemNameText.getText());

        // Compare the actual item name with the expected name using the utility method assertTextEquals.
        // If the names match, the item name is displayed correctly; otherwise, the test will fail.
        utility.assertTextEquals(itemNameText, itemName);
    }

}
