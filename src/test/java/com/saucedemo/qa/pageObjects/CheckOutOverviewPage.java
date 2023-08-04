package com.saucedemo.qa.pageObjects;

import com.saucedemo.qa.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.testng.Assert;

import java.util.List;

public class CheckOutOverviewPage  extends BasePage {


        @FindAll({ @FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_name\"]") })
        @CacheLookup
        public List<WebElement> ItemsNameText;

        @FindAll({ @FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_price\"]") })
        @CacheLookup
        public List<WebElement> itemsPriceText;

        @FindAll({ @FindBy(how = How.XPATH, using = "//div[@class=\"inventory_item_desc\"]") })
        @CacheLookup
        public List<WebElement> itemsDescriptionText;

        @FindBy(xpath = "//div[@class=\"summary_subtotal_label\"]")
        @CacheLookup
        public WebElement itemTotalText;

        @FindBy(xpath = "//div[@class=\"summary_tax_label\"]")
        @CacheLookup
        public WebElement taxTotalText;

        @FindBy(xpath = "//div[@class=\"summary_info_label summary_total_label\"]")
        @CacheLookup
        public WebElement finalTotalText;

        @FindBy(xpath = "//span[@class=\"title\" and text()=\"Checkout: Overview\"]")
        @CacheLookup
        public WebElement pageHeaderText;

        @FindBy(xpath = "//button[text()=\"Finish\"]")
        @CacheLookup
        public WebElement finishButton;

        public CheckOutOverviewPage(WebDriver driver) {
            super(driver);
            PageFactory.initElements(driver, this);
        }

        public void verifyThatUserIsOnCheckotuOverviewPage() {
            Assert.assertTrue(pageHeaderText.getText().equalsIgnoreCase("Checkout: Overview"), "User is not on Checkout Overview Page");
        }

        public Double getitemTotalPrice() {
            utility.scrollElementIntoView(itemTotalText);
            utility.highlightElement(itemTotalText);
            return Double.parseDouble(itemTotalText.getText().replace("Item total: $", "").trim());
        }

        public Double getTaxTotalPrice() {
            utility.scrollElementIntoView(taxTotalText);
            utility.highlightElement(taxTotalText);
            return Double.parseDouble(taxTotalText.getText().replace("Tax: $", "").trim());
        }


        public Double getFinalTotalPrice() {
            utility.scrollElementIntoView(finalTotalText);
            utility.highlightElement(finalTotalText);
            return Double.parseDouble(finalTotalText.getText().replace("Total: $", "").trim());
        }
}
