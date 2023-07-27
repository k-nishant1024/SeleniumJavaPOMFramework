package com.saucedemo.qa.tests;

import com.saucedemo.qa.pageObjects.*;
import com.saucedemo.qa.utils.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CheckOutTests {

    WebDriver driver;

    Utility utility;

    ProductPage products;

    LoginPage login;

    CartPage cart;

    InventoryItemPage productDetail;

    CheckOutFormPage checkoutForm;

    CheckOutOverviewPage checkoutOverview;

    CheckOutCompletePage checkoutComplete;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        utility = new Utility(driver);
        products = new ProductPage(driver);
        login = new LoginPage(driver);
        cart = new CartPage(driver);
        productDetail = new InventoryItemPage(driver);

        checkoutForm = new CheckOutFormPage(driver);

        checkoutOverview = new CheckOutOverviewPage(driver);

        checkoutComplete = new CheckOutCompletePage(driver);

        login.loginWith("standard_user", "secret_sauce");


    }

    @AfterMethod

    public void tearDown() {

        utility.quit();

    }


    @Test
    public void TC01_AddAnItemToCartAndCompleteCheckOut() {
        products.verifyUserIsOnProductPage();
        products.clickOnAProduct("Sauce Labs Backpack");
        productDetail.verifyUserIsOnInventoryItemPage();

        utility.click(productDetail.addItemToCartButton);

        utility.click(productDetail.cartButton);

        cart.verifyUserIsOnCartPage();

        utility.click(cart.checkOutButton);

        checkoutForm.verifyUserIsOnCheckoutFormPage();

        utility.type(checkoutForm.firstNameInput, "First Name");

        utility.type(checkoutForm.LastNameInput, "Last Name");

        utility.type(checkoutForm.zipInput, "123456");

        utility.click(checkoutForm.continueButton);

        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();

        utility.click(checkoutOverview.finishButton);

        checkoutComplete.verifyUserIsOnCheckoutCompletePage();

        assertTrue(checkoutComplete.thankYouText.isDisplayed());

    }

    @Test

    public void TC02_verifyCorrectPriceAreDisplayedOnCheckOut() {

        products.verifyUserIsOnProductPage();

        products.clickOnAProduct("Sauce Labs Backpack");

        productDetail.verifyUserIsOnInventoryItemPage();

        Double priceOnProductDetailsPage = Double

                .parseDouble(utility.getText(productDetail.itemPriceText).replace("$", "").trim());

        utility.click(productDetail.addItemToCartButton);

        utility.click(productDetail.cartButton);

        cart.verifyUserIsOnCartPage();

        utility.click(cart.checkOutButton);

        checkoutForm.verifyUserIsOnCheckoutFormPage();

        utility.type(checkoutForm.firstNameInput, "First Name");

        utility.type(checkoutForm.LastNameInput, "Last Name");

        utility.type(checkoutForm.zipInput, "123456");

        utility.click(checkoutForm.continueButton);

        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();

        assertEquals(checkoutOverview.getitemTotalPrice(), priceOnProductDetailsPage);

    }

    @Test

    public void TC03_verifyCorrectPriceIsCalculatedForAllProductsOnCheckout() {


        products.verifyUserIsOnProductPage();
        List<String> productsName = new ArrayList<>();
        productsName.add("Sauce Labs Backpack");
        productsName.add("Sauce Labs Bike Light");
        productsName.add("Sauce Labs Bolt T-Shirt");
        productsName.add("Sauce Labs Fleece Jacket");
        productsName.add("Sauce Labs Onesie");

        Double priceOfProducts = 0.00;
        for (String product : productsName) {
            priceOfProducts += products.getPriceOfAProduct(product);
            products.addAnItemToCart(product);
        }
        utility.click(products.cartButton);
        cart.verifyUserIsOnCartPage();
        utility.click(cart.checkOutButton);
        checkoutForm.verifyUserIsOnCheckoutFormPage();
        utility.type(checkoutForm.firstNameInput, "First Name");
        utility.type(checkoutForm.LastNameInput, "Last Name");
        utility.type(checkoutForm.zipInput, "123456");
        utility.click(checkoutForm.continueButton);
        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();
        assertEquals(checkoutOverview.getitemTotalPrice(), priceOfProducts);

    }

    @Test

    public void TC04_verifyCorrectTotalTaxIsCalculatedForAllProductsOnCheckout() {


        products.verifyUserIsOnProductPage();


        List<String> productsName = new ArrayList<>();

        productsName.add("Sauce Labs Backpack");

        productsName.add("Sauce Labs Bike Light");

        productsName.add("Sauce Labs Bolt T-Shirt");

        productsName.add("Sauce Labs Fleece Jacket");

        productsName.add("Sauce Labs Onesie");


        double priceOfProducts = 0.00;

        for (String product : productsName) {

            priceOfProducts += products.getPriceOfAProduct(product);

            products.addAnItemToCart(product);

        }


        utility.click(products.cartButton);

        cart.verifyUserIsOnCartPage();

        utility.click(cart.checkOutButton);

        checkoutForm.verifyUserIsOnCheckoutFormPage();

        utility.type(checkoutForm.firstNameInput, "First Name");

        utility.type(checkoutForm.LastNameInput, "Last Name");

        utility.type(checkoutForm.zipInput, "123456");

        utility.click(checkoutForm.continueButton);

        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();


        double taxTotal = BigDecimal.valueOf(priceOfProducts * 0.08).setScale(2, RoundingMode.HALF_UP).doubleValue();

        assertEquals(checkoutOverview.getTaxTotalPrice(), taxTotal);


    }


    @Test

    public void TC05_verifyCorrectTotalTaxIsCalculatedForAllProductsOnCheckout() {


        products.verifyUserIsOnProductPage();


        List<String> productsName = new ArrayList<>();

        productsName.add("Sauce Labs Backpack");

        productsName.add("Sauce Labs Bike Light");

        productsName.add("Sauce Labs Bolt T-Shirt");

        productsName.add("Sauce Labs Fleece Jacket");

        productsName.add("Sauce Labs Onesie");


        double priceOfProducts = 0.00;

        for (String product : productsName) {

            priceOfProducts += products.getPriceOfAProduct(product);

            products.addAnItemToCart(product);

        }


        utility.click(products.cartButton);

        cart.verifyUserIsOnCartPage();

        utility.click(cart.checkOutButton);

        checkoutForm.verifyUserIsOnCheckoutFormPage();

        utility.type(checkoutForm.firstNameInput, "First Name");

        utility.type(checkoutForm.LastNameInput, "Last Name");

        utility.type(checkoutForm.zipInput, "123456");

        utility.click(checkoutForm.continueButton);

        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();


        double taxTotal = BigDecimal.valueOf(priceOfProducts * 0.08).setScale(2, RoundingMode.HALF_UP).doubleValue();


        double finalTotalPrice = BigDecimal.valueOf(taxTotal + priceOfProducts).setScale(2, RoundingMode.HALF_UP)

                .doubleValue();


        assertEquals(checkoutOverview.getFinalTotalPrice(), finalTotalPrice);


    }

    @Test

    public void TC06_verifyAllThePriceIsDisplayedDuringCheckoutIsCorrect() {

        products.verifyUserIsOnProductPage();


        List<String> productsName = new ArrayList<>();

        productsName.add("Sauce Labs Backpack");

        productsName.add("Sauce Labs Bike Light");

        productsName.add("Sauce Labs Bolt T-Shirt");

        productsName.add("Sauce Labs Fleece Jacket");

        productsName.add("Sauce Labs Onesie");


        Double priceOfProducts = 0.00;

        for (String product : productsName) {

            priceOfProducts += products.getPriceOfAProduct(product);

            products.addAnItemToCart(product);

        }


        utility.click(products.cartButton);

        cart.verifyUserIsOnCartPage();

        utility.click(cart.checkOutButton);

        checkoutForm.verifyUserIsOnCheckoutFormPage();

        utility.type(checkoutForm.firstNameInput, "First Name");

        utility.type(checkoutForm.LastNameInput, "Last Name");

        utility.type(checkoutForm.zipInput, "123456");

        utility.click(checkoutForm.continueButton);

        checkoutOverview.verifyThatUserIsOnCheckotuOverviewPage();


        double taxTotal = BigDecimal.valueOf(priceOfProducts * 0.08).setScale(2, RoundingMode.HALF_UP).doubleValue();


        double finalTotalPrice = BigDecimal.valueOf(taxTotal + priceOfProducts).setScale(2, RoundingMode.HALF_UP)

                .doubleValue();


        assertEquals(checkoutOverview.getitemTotalPrice(), priceOfProducts);

        assertEquals(checkoutOverview.getTaxTotalPrice(), taxTotal);

        assertEquals(checkoutOverview.getFinalTotalPrice(), finalTotalPrice);


    }
}

