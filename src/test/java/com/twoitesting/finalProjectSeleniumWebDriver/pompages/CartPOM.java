package com.twoitesting.finalProjectSeleniumWebDriver.pompages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.twoitesting.finalProjectSeleniumWebDriver.utilitiesPOM.HelpersStaticPOM.*;

public class CartPOM {
    // Field to work with webdriver in this class
    private WebDriver driver;

    // Constructor to receive driver form test and set field
    public CartPOM(WebDriver driver) {
        this.driver = driver;
        // initialise pagefactory with the driver and this class
        PageFactory.initElements(driver, this);
    }

    // locators
    @FindBy(className = "woocommerce-remove-coupon")
    WebElement removeCouponLink;
    @FindBy(className = "remove")
    WebElement removeProductLink;
    @FindBy(id = "coupon_code")
    WebElement couponCodeField;
    @FindBy(css = "button[name='apply_coupon']")
    WebElement applyCouponButton;
    @FindBy(className = "woocommerce-message")
    WebElement couponResponse;
    @FindBy(css = ".cart-subtotal > td > span > bdi ")
    WebElement subTotalAmount;
    @FindBy(css = ".cart-discount > td > span")
    WebElement discountAmount;
    @FindBy(css = "#shipping_method > li > label > span > bdi")
    WebElement shippingFeeAmount;

    @FindBy(css = "tr.order-total > td > strong > span > bdi")
    WebElement totalAmount;
    @FindBy(linkText = "Proceed to checkout")
    WebElement checkoutButton;


    // Methods
    public void removeCoupon() {
        waitForElementToBePresentPOM(driver, removeCouponLink, 1);
        removeCouponLink.click();
    }

    public void removeProduct() {
        removeProductLink.click();
    }

    public void addCoupon(String coupon) {
        couponCodeField.sendKeys(coupon);
        applyCouponButton.click();
    }

    public String retrieveAddCouponResponse() {
        waitForElementToBePresentPOM(driver, couponResponse, 1);
        return couponResponse.getText();
    }

    public String subTotalString() {
        return subTotalAmount.getText();
    }

    public String discountString() {
        waitForElementToBePresentPOM(driver, discountAmount, 1);
        return discountAmount.getText();
    }

    public String shippingFeeString() {
        return shippingFeeAmount.getText();
    }

    public String totalAmountString() {
        return totalAmount.getText();
    }

    public void clickCheckoutButton() {
        waitForElementToBeClickablePOM(driver, checkoutButton, 1);
        checkoutButton.click(); // ElementClickInterceptedException could happen,fixed it with dismissing "This is a demo store" bottom banner
    }

}
