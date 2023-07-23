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
        Assert.assertTrue(pageHeaderText.getText().equalsIgnoreCase("Products"), "Ueer is not on Products Page");
    }

    public void addAnItemToCart(String itemName) {
        for (WebElement item_Name : itemsNameText) {
            if (item_Name.getText().equalsIgnoreCase(itemName)) {
                utility.click(driver.findElement(By.xpath(String.format(
                        "//div[text()=\"%s\"]/../../following-sibling::div/button[text()=\"Add to cart\"]",
                        itemName))));
            }
        }
    }

    public void clickOnRemoveButtton(String itemName) {
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                utility.click(driver.findElement(By.xpath(String.format(
                        "//div[text()=\"%s\"]/../../following-sibling::div/button[text()=\"Remove\"]", itemName))));
            }
        }
    }
    public void sortbyPriceHighToLow() {
        utility.selectDropdownByValue(sortContainerSelect, "hilo");
    }

    public void sortbyPriceLowToHigh() {
        utility.selectDropdownByValue(sortContainerSelect, "lohi");
    }

    public void sortByNameAtoZ() {
        utility.selectDropdownByValue(sortContainerSelect, "az");
    }

    public void sortByNameZtoA() {
        utility.selectDropdownByValue(sortContainerSelect, "za");
    }
    public void clickOnAProduct(String itemName) {
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                utility.click(item);
                break;
            }
        }
    }

    public double getPriceOfAProduct(String itemName) {
        double itemPrice = 0;
        for (WebElement item : itemsNameText) {
            if (item.getText().equalsIgnoreCase(itemName)) {
                utility.highlightElement(item);
                itemPrice = Double.parseDouble(utility.getText(driver.findElement(By.xpath(String.format(
                        "//*[text()=\"%s\"]/../../following-sibling::div/div[@class=\"inventory_item_price\"]",
                        itemName)))).replace("$", "").trim());
            }
        }
        return itemPrice;
    }

    public void logOut() {
        utility.click(burgerButton);
        utility.click(logout_link);
    }
}