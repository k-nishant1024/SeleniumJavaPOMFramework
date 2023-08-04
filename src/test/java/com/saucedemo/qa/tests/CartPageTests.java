package com.saucedemo.qa.tests;
import com.saucedemo.qa.pageObjects.CartPage;
import com.saucedemo.qa.pageObjects.LoginPage;
import com.saucedemo.qa.pageObjects.ProductPage;
import com.saucedemo.qa.utils.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
public class CartPageTests{



    WebDriver driver;
    Utility utility;
    ProductPage productsPage;
    LoginPage loginPage;
    CartPage cartPage;
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        utility = new Utility(driver);
        productsPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
        loginPage.loginWith("standard_user", "secret_sauce");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        utility.quit();
    }

    @Test
    public void TC01_VerifyUserIsAbleToRemoveAnItemFromCart() {
        // Add three items to the cart.
        productsPage.addAnItemToCart("Sauce Labs Backpack");
        productsPage.addAnItemToCart("Sauce Labs Bolt T-Shirt");
        productsPage.addAnItemToCart("Sauce Labs Onesie");
        // Click the cart button to go to the cart page.
        utility.click(productsPage.cartButton);
        // Assert that the cart contains three items.
        Assert.assertEquals(Integer.parseInt(cartPage.cartButton.getText()), 3);
        // Remove the "Sauce Labs Backpack" item from the cart.
        cartPage.removeAnItemFromCart("Sauce Labs Backpack");
        // Assert that the cart now contains two items.
        Assert.assertEquals(Integer.parseInt(cartPage.cartButton.getText()), 2);

    }
    @Test
    public void TC02_VerifyThatUserIsAbleToAnItemToCart() {
        productsPage.verifyUserIsOnProductPage();
        productsPage.addAnItemToCart("Sauce Labs Backpack");
    }
}
