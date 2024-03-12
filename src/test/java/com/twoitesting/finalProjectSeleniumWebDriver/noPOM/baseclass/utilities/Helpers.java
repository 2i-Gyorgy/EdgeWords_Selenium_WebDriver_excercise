package com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helpers {
    // Field to hold driver
    WebDriver driver;
    public Helpers(WebDriver driver) {
        this.driver = driver;
    }
    
    // Helper methods
    public WebElement waitForElementToBeClickable(By locator, int timeOutSeconds){
        WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        return myWait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
