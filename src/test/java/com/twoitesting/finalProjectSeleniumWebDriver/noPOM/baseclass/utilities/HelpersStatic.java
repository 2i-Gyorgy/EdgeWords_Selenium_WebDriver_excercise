package com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HelpersStatic {
    // Helper methods
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeOutSeconds) {
        WebDriverWait myClickWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        return myClickWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBePresent(WebDriver driver, By locator, int timeOutSeconds) {
        WebDriverWait myPresenceWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        return myPresenceWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
