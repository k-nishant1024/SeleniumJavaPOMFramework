package com.saucedemo.qa.tests;

import com.saucedemo.qa.pageObjects.CartPage;
import com.saucedemo.qa.pageObjects.InventoryItemPage;
import com.saucedemo.qa.pageObjects.LoginPage;
import com.saucedemo.qa.pageObjects.ProductPage;
import com.saucedemo.qa.utils.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductPageTests {

    WebDriver driver;
    Utility utility;
    ProductPage products;
    LoginPage login;
    CartPage cart;
    InventoryItemPage productDetail;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        utility = new Utility(driver);
        products = new ProductPage(driver);
        login = new LoginPage(driver);
        cart = new CartPage(driver);
        productDetail = new InventoryItemPage(driver);
        login.loginWith("standard_user", "secret_sauce");
    }


    @AfterMethod
    public void tearDown() throws InterruptedException {
        utility.quit();
    }


    @Test
    public void TC01_VerifyThatUserIsAbleToAddAnItemToCartFromProductsPage() {
        String item = "Sauce Labs Backpack";
        products.verifyUserIsOnProductPage();
        products.addAnItemToCart(item);
        utility.click(products.cartButton);
        cart.verifyUserIsOnCartPage();
        List<String> allItemsText = new ArrayList<String>();

        for (WebElement element : cart.ItemsNameText)
            allItemsText.add(element.getText());
        boolean isItemPresent = allItemsText.contains(item);

        assertTrue(isItemPresent, item + "is not present in cart");
//		Assert.assertTrue(cartPage.ItemsNameText.stream().anyMatch(element -> element.getText().equals(item)));
    }


    @Test
    public void TC02_VerifyThatUserIsRemoveAnItemFromCartFromProductsPage() {
        // List of items to add to cart
        List<String> items = new ArrayList<String>();
        items.add("Sauce Labs Backpack");
        items.add("Sauce Labs Bolt T-Shirt");
        items.add("Sauce Labs Fleece Jacket");
        // Verify user is on product page
        products.verifyUserIsOnProductPage();
        // Add all item in list to cart
        for (String item : items)
            products.addAnItemToCart(item);
        // Go to cart page
        utility.click(products.cartButton);
        cart.verifyUserIsOnCartPage();
        for (int i = 0; i < cart.ItemsNameText.size(); i++)
            assertEquals(cart.ItemsNameText.get(i).getText(), items.get(i));
        utility.click(cart.continueShoppingButton);
        PageFactory.initElements(driver, products);
        products.verifyUserIsOnProductPage();
        products.clickOnRemoveButton(items.get(1));
        items.remove(1);
        utility.click(products.cartButton);
        PageFactory.initElements(driver, cart);
        cart.verifyUserIsOnCartPage();
        for (int i = 0; i < cart.ItemsNameText.size(); i++)
            assertEquals(cart.ItemsNameText.get(i).getText(), items.get(i));
    }


    @Test
    public void TC03_sortProductsByPriceLowToHighAndVerify() {
        products.sortbyPriceLowToHigh();
        double previousPrice = 0;
        for (WebElement productPrice : products.itemsPriceText) {
            double currentPrice = Double.parseDouble(productPrice.getText().replace("$", ""));
            assertTrue(currentPrice >= previousPrice, "Product are not sorted by Price (low to high)");
            previousPrice = currentPrice;
        }
    }


    @Test
    public void TC04_sortProductsByPriceHighToLowAndVerify() {
        products.sortbyPriceHighToLow();
        double previousPrice = Double.parseDouble(products.itemsPriceText.get(0).getText().replace("$", ""));
        for (WebElement productPrice : products.itemsPriceText) {
            double currentPrice = Double.parseDouble(productPrice.getText().replace("$", ""));
            assertTrue(currentPrice <= previousPrice, "Product are not sorted by Price (High to Low)");
            previousPrice = currentPrice;
        }
    }

    @Test
    public void TC05_sortProductsByNameAtoZAndVerify() {
        products.sortByNameAtoZ();
        String previousName = "";
        for (WebElement productName : products.itemsNameText) {
            String currentName = productName.getText();
            assertTrue(currentName.compareToIgnoreCase(previousName) >= 0,
                    "Product are not sorted by Name(A-Z)");
            previousName = currentName;
        }
    }


    @Test
    public void TC06_sortProductsByNameZtoAAndVerify() {
        products.sortByNameZtoA();
        String previousName = products.itemsNameText.get(0).getText();
        for (WebElement productName : products.itemsNameText) {
            String currentName = productName.getText();
          assertTrue(currentName.compareToIgnoreCase(previousName) <= 0,
                    "Product are not sorted by Name(Z-A)");
            previousName = currentName;
        }
    }

    @Test
    public void TC07_testProductNamesAreVisibleAndAccurate() {
        List<String> productsName = new ArrayList<String>();
        productsName.add("Sauce Labs Backpack");
        productsName.add("Sauce Labs Bike Light");
        productsName.add("Sauce Labs Bolt T-Shirt");
        productsName.add("Sauce Labs Fleece Jacket");
        productsName.add("Sauce Labs Onesie");
        productsName.add("Test.allTheThings() T-Shirt (Red)");

//		for(WebElement element : products.ItemsNameText) {

//			assertTrue(!element.getText().isEmpty(), "Text for WebElement : " + element.toString() + "is not visible");

//		}


        for (int i = 0; i < products.itemsNameText.size(); i++) {
            assertTrue(!products.itemsNameText.get(i).getText().isEmpty(),
                    "Text for WebElement : " + products.itemsNameText.get(i) + "is not visible");
            assertEquals(products.itemsNameText.get(i).getText(), productsName.get(i));
        }
    }


    @Test
    public void TC08_testProductPriceAreVisibleAndAccurate() {
        String[] productsPrice = {"$29.99", "$9.99", "$15.99", "$49.99", "$7.99", "$15.99"};
        for (int i = 0; i < products.itemsPriceText.size(); i++) {
            assertTrue(!products.itemsPriceText.get(i).getText().isEmpty(),
                    "Price for WebElement : " + products.itemsPriceText.get(i) + "is not visible");
            assertEquals(products.itemsPriceText.get(i).getText(), productsPrice[i]);
        }
    }

    @Test
    public void TC09_testAddAnItemToCartAndVerifyReflectionInCartIcon() {
        String item = "Sauce Labs Onesie";
        assertEquals(products.cartButton.getText().trim(), "");
        products.addAnItemToCart(item);
        assertEquals(products.cartButton.getText().trim(), "1");
    }

    @Test
    public void TC10_testAddToCartButtonIsFunctionalForEachProduct() {
        int count = 0;
        for (WebElement element : products.itemsNameText) {
            products.addAnItemToCart(element.getText().trim());
            assertEquals(Integer.parseInt(products.cartButton.getText().trim()), ++count);
        }
    }

    @Test
    public void TC11_ClickOnAProductAndVerifyThatTheProductDetailsPageOpensSuccessfully() {
        String item = "Sauce Labs Onesie";
        products.clickOnAProduct(item);
        productDetail.verifyUserIsOnInventoryItemPage();
        assertTrue(productDetail.itemNameText.getText().trim().equals(item));
    }
}