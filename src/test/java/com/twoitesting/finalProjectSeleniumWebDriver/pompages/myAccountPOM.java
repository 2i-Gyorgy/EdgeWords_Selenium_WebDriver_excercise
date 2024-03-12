package com.twoitesting.finalProjectSeleniumWebDriver.pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class myAccountPOM {
    // Field to work with webdriver in this class
    private WebDriver driver;

    // Constructor to receive driver form test and set field
    public myAccountPOM(WebDriver driver) {
        this.driver = driver;
        // initialise pagefactory with the driver and this class
        PageFactory.initElements(driver, this);
    }

    // locators
    @FindBy(linkText = "Dismiss")
    WebElement dismissLink;  // button to dismiss blue information banner at the bottom
    @FindBy(id = "username")
    WebElement usernameField;
    @FindBy(id = "password")
    WebElement passwordField;
    @FindBy(css = "button[value='Log in']")
    WebElement loginLink;
    @FindBy(linkText = "Shop")
    WebElement shopLink;
    @FindBy(linkText = "Logout")
    WebElement logOutLink;

    @FindBy(linkText = "Orders")
    WebElement ordersLink;

    // Methods
    public void dismissBanner() {
        dismissLink.click();
    }

    public void doLogIn(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginLink.click();
    }

    public void goToShop() {
        shopLink.click();
    }

    public void doLogOut() {
        logOutLink.click();
    }

    public void navigateToOrders() {
        ordersLink.click();
    }
}
