package com.twoitesting.finalProjectSeleniumWebDriver.utilitiesPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HelpersStaticPOM {
    // Helper methods
    public static void waitForElementToBeClickablePOM(WebDriver driver, WebElement locator, int timeOutSeconds) {
        WebDriverWait myClickWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        myClickWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForElementToBePresentPOM(WebDriver driver, WebElement locator, int timeOutSeconds) {
        WebDriverWait myPresenceWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
        myPresenceWait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
