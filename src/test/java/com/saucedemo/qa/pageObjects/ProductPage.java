package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.testng.Assert;

import java.util.List;

public class ProductPage extends BasePage {
    @FindBy(xpath = "//div[@id=\"inventory_container\"]")
    @CacheLookup
    public WebElement inventoryContainer;

    @FindBy(xpath = "//div[@class=\"bm-burger-button\"]/button[text()=\"Open Menu\"]")
    @CacheLookup
    public WebElement burgerButton;

    @FindBy(xpath = "//a[text()=\"Logout\"]")
    @CacheLookup
    public WebElement logout_link;

    @FindBy(xpath = "//div[@id=\"shopping_cart_container\" and @class=\"shopping_cart_container\"]")
    @CacheLookup
    public WebElement cartButton;

    @FindBy(xpath = "//span[@class=\"select_container\"]/select[@class=\"product_sort_container\"]")
    @CacheLookup
    public WebElement sortContainerSelect;

    @FindBy(xpath = "//div[@class=\"pricebar\"]/button[text()=\"Add to cart\"]")
    @CacheLookup
    public WebElement addToCartButon;

    @FindBy(xpath = "//div[@class=\"pricebar\"]/button[text()=\"Remove\"]")
    @CacheLookup
    public WebElement removeButton;

    @FindBy(xpath = "//span[@class=\"title\" and text()=\"Products\"]")
    @CacheLookup
    public WebElement pageHeaderText;

    //	@FindAll({ @FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_name\"]") })
    @FindAll({@FindBy(xpath = "//div[@class=\"inventory_item_name\"]")})
    @CacheLookup
    public List<WebElement> itemsNameText;

    @FindAll({@FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_price\"]")})
    @CacheLookup
    public List<WebElement> itemsPriceText;

    @FindAll({@FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_desc\"]")})
    @CacheLookup
    public List<WebElement> itemsDescriptionText;

    LoginPage login;
    String itemName;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void verifyUserIsOnProductPage() {
        // Verify if the header text matches the expected text "Products"
        if (pageHeaderText.getText().equalsIgnoreCase("Products")) {
            logger.info("User is on Product Page. Header text is: " + pageHeaderText.getText());
        } else {
            logger.error("User is not on Product Page. Expected: 'Products', Actual: '" + pageHeaderText.getText() + "'");
            Assert.fail("User is not on Product Page. Expected: 'Products', Actual: '" + pageHeaderText.getText() + "'");
        }
    }

    public void addAnItemToCart(String itemName) {
//        for (WebElement item_Name : itemsNameText) {
//            if (item_Name.getText().equalsIgnoreCase(itemName)) {
//                utility.click(driver.findElement(By.xpath(String.format(
//                        "//div[text()=\"%s\"]/../../following-sibling::div/button[text()=\"Add to cart\"]",
//                        itemName))));
//            }
//        }
        for (WebElement item_Name : itemsNameText) {
            if (item_Name.getText().equalsIgnoreCase(itemName)) {
                // Click the "Add to cart" button for the matching item
                String addToCartButtonXpath = String.format("//div[text()=\"%s\"]/../../following-sibling::div/button[text()=\"Add to cart\"]", itemName);
                WebElement addToCartButton = driver.findElement(By.xpath(addToCartButtonXpath));
                utility.click(addToCartButton);

                logger.info("Added item '" + itemName + "' to the cart.");
                return; // Exit the method once the item is added to the cart
            }
        }

        // If the item with the specified name is not found in the list
        logger.warn("Item '" + itemName + "' not found. Could not add to cart.");


    }

    public void clickOnRemoveButton(String itemName) {
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                // Click the "Remove" button for the matching item
                utility.click(driver.findElement(By.xpath(String.format(
                        "//div[text()=\"%s\"]/../../following-sibling::div/button[text()=\"Remove\"]", itemName))));
                logger.info("Clicked on the 'Remove' button for item: " + itemName);
                return; // Exit the method once the "Remove" button is clicked
            }
        }
        // If the item is not found, log an error message
        logger.error("Item '" + itemName + "' not found in the cart. Cannot click on 'Remove' button.");
    }

    public void sortbyPriceHighToLow() {
        utility.selectDropdownByValue(sortContainerSelect, "hilo");
        logger.info("Sorted items by price: High to Low");

    }

    public void sortbyPriceLowToHigh() {
        utility.selectDropdownByValue(sortContainerSelect, "lohi");
        logger.info("Sorted items by price: Low to High");

    }

    public void sortByNameAtoZ() {
        utility.selectDropdownByValue(sortContainerSelect, "az");
        logger.info("Sorted items by name: A to Z");

    }

    public void sortByNameZtoA() {
        utility.selectDropdownByValue(sortContainerSelect, "za");
        logger.info("Sorted items by name: Z to A");

    }

    public void clickOnAProduct(String itemName) {
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                // Click on the item that matches the specified itemName

                utility.click(item);
                logger.info("Clicked on the product: " + itemName);
                return; // Exit the method once the item is clicked
            }
            // If the item is not found, log an error message
            logger.error("Product '" + itemName + "' not found in the items list. Cannot click on it.");

        }
    }

    public double getPriceOfAProduct(String itemName) {
        double itemPrice = 0;
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                // Highlight the element to make it stand out in the Test
                utility.highlightElement(item);
                // Get the price of the item by extracting it from the element's text
                itemPrice = Double.parseDouble(utility.getText(driver.findElement(By.xpath(String.format(
                        "//*[text()=\"%s\"]/../../following-sibling::div/div[@class=\"inventory_item_price\"]",
                        itemName)))).replace("$", "").trim());
                logger.info("Retrieved price of product '" + itemName + "': $" + itemPrice);
                return itemPrice; // Exit the method once the price is retrieved
            }
        }
        // If the item is not found, log an error message
        logger.error("Product '" + itemName + "' not found in the items list. Cannot retrieve the price.");
        return itemPrice; // Return 0 as the price since the product is not found
    }

    public void logOut() {
        // Click on the burger button to open the navigation menu
        utility.click(burgerButton);
        // Click on the logout link to log out from the application
        utility.click(logout_link);

        logger.info("Logged out successfully.");
    }
}