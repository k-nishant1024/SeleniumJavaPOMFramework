package com.saucedemo.qa.base;

import com.saucedemo.qa.utils.Utility;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    // Logger instance shared by all instances of BasePage
    public static Logger logger;

    protected WebDriver driver;
    protected Utility utility;

    // Constructor for BasePage
    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Initialize the logger with the class name
        logger = LoggerFactory.getLogger(this.getClass());
        // Set implicit wait and maximize the browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        // Initialize the utility object to perform common actions
        utility = new Utility(driver);
    }

    public void visit(String url){
        // Log the action of visiting the URL
        logger.info("Visiting URL: " + url);
        // Open the URL in the browser
        driver.get(url);
    }
}
