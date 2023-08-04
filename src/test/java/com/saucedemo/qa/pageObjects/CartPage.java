package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.testng.Assert;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@class=\"inventory_item_name\"]")
    @CacheLookup
    public WebElement itemNameText;

    @FindAll({@FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_name\"]")})
    @CacheLookup
    public List<WebElement> ItemsNameText;

    @FindBy(xpath = "//div[@class=\"inventory_item_price\"]")
    @CacheLookup
    public WebElement itemPriceText;

    @FindAll({@FindBy(xpath = "//div[@class=\"inventory_item_price\"]")})
    @CacheLookup
    public List<WebElement> itemPricesText;

    @FindBy(xpath = "//div[@class=\"cart_quantity\"]")
    @CacheLookup
    public WebElement itemQuantityText;

    @FindBy(xpath = "//button[contains(@class,\"btn btn_secondary\")][text()=\"Remove\"]")
    @CacheLookup
    public WebElement removeButton;

    @FindBy(xpath = "//button[contains(@class,\"checkout_button\")][text()=\"Checkout\"]")
    @CacheLookup
    public WebElement checkOutButton;

    @FindBy(xpath = "//button[@id=\"continue-shopping\" and text()=\"Continue Shopping\"]")
    @CacheLookup
    public WebElement continueShoppingButton;

    @FindBy(xpath = "//span[@class=\"title\" and text()=\"Your Cart\"]")
    @CacheLookup
    public WebElement pageHeaderText;

    @FindBy(xpath = "//div[@id=\"shopping_cart_container\" and @class=\"shopping_cart_container\"]")
    @CacheLookup
    public WebElement cartButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("CartPage constructor initialized. Page elements initialized using PageFactory.");
    }

    public void verifyUserIsOnCartPage() {
        // Expected header text for the Cart page
        String expectedHeaderText = "Your Cart";

        // Get the actual header text from the page
        String actualHeaderText = pageHeaderText.getText();

        // Assertion to check if the user is on the Cart page
        Assert.assertTrue(actualHeaderText.equalsIgnoreCase(expectedHeaderText),
                "User is not on Cart Page. Expected header: " + expectedHeaderText + ", Actual header: " + actualHeaderText);

        // Logging information about the verification result
        if (actualHeaderText.equalsIgnoreCase(expectedHeaderText)) {
            // The verification passed; user is on the Cart page
            logger.info("User is on Cart Page. Header text matches: " + expectedHeaderText);
        } else {
            // The verification failed; user is not on the Cart page
            logger.info("User is NOT on Cart Page. Expected header: " + expectedHeaderText + ", Actual header: " + actualHeaderText);
        }
    }

    public void removeAnItemFromCart(String itemName) {
        // Loop through all the items in the cart to find the one with the specified itemName
        for (WebElement item : ItemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                // Found the item, so get the remove button element and click it
                WebElement removeButton = driver.findElement(By.xpath(String.format(
                        "//div[text()=\"%s\"]/../following-sibling::div[2]/button", itemName)));

                utility.click(removeButton);

                // Logging the action
                logger.info("Removed item '" + itemName + "' from the cart.");

                // The return statement below ensures that the method exits after the item is removed,
                // assuming that there won't be multiple items with the same name in the cart.
                // If multiple items with the same name are allowed, and you want to remove all of them,
                // you can remove this return statement.
                return;
            }
        }

        // If the item is not found in the cart, log a message indicating that it was not found.
        logger.info("Item '" + itemName + "' not found in the cart.");
    }


}
