package com.saucedemo.qa.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saucedemo.qa.utils.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BasePage {
    public static Logger logger;
    protected WebDriver driver;
    protected Utility utility;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        logger = LoggerFactory.getLogger(this.getClass());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        utility = new Utility(driver);
    }

    public void visit(String url){
        logger.info("Visit URL : " + url );
        driver.get(url);
    }
}
