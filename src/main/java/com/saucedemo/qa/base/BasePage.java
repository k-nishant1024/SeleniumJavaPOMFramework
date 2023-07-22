package com.saucedemo.qa.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BasePage {
    public static Logger logger;
    protected WebDriver driver;
    protected Utility utility;
    static Properties prop;

    public BasePage(WebDriver driver) {
        try {
            prop =  new Properties();
            FileInputStream ip = new FileInputStream("src/main/java/com/saucedemo/qa/config/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.driver = driver;
        logger = LoggerFactory.getLogger(getClass());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        utility = new Utility(driver);
    }

    public void visit(String url){
        logger.info("Visit URL");
        driver.get(url);
    }
}
