package com.twoitesting.finalProjectSeleniumWebDriver.pompages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.twoitesting.finalProjectSeleniumWebDriver.utilitiesPOM.HelpersStaticPOM.*;

public class ShopPOM {
    // Field to work with webdriver in this class
    protected WebDriver driver;
    protected WebDriverWait wait;
    // Constructor to receive driver form test and set field
    public ShopPOM(WebDriver driver){
        this.driver = driver;
        // initialise pagefactory with the driver and this class
        PageFactory.initElements(driver, this);
    }
    // locators
//    WebElement cartButtonWait = waitForElementToBeClickable(driver, By.cssSelector("#site-header-cart > li > a"), 1);
//        cartButtonWait.click();
    @FindBy (css = "[aria-label*='Beanie']") WebElement addToCartLink;
    @FindBy (css = "#site-header-cart > li > a") WebElement cartButton;
    // Methods
     public void addProductToCart() {
         addToCartLink.click();
     }
    public void goToCart() {
        waitForElementToBeClickablePOM(driver, cartButton, 1);
        cartButton.click();

        cartButton.click();
    }
}
