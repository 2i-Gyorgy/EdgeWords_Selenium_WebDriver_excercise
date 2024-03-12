package com.twoitesting.finalProjectSeleniumWebDriver.pompages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.twoitesting.finalProjectSeleniumWebDriver.utilitiesPOM.HelpersStaticPOM.waitForElementToBePresentPOM;

public class CheckOutPOM {
    // Field to work with webdriver in this class
    private WebDriver driver;

    // Constructor to receive driver form test and set field
    public CheckOutPOM(WebDriver driver) {
        this.driver = driver;
        // initialise pagefactory with the driver and this class
        PageFactory.initElements(driver, this);
    }

    // locators
    @FindBy(id = "billing_first_name")
    WebElement FirstNameField;
    @FindBy(id = "billing_last_name")
    WebElement LatNameField;
    @FindBy(id = "billing_address_1")
    WebElement address1Field;
    @FindBy(id = "billing_city")
    WebElement cityField;
    @FindBy(id = "billing_postcode")
    WebElement postcodeField;
    @FindBy(id = "billing_phone")
    WebElement phoneNumberField;
    @FindBy(css = "label[for='payment_method_cheque']")
    WebElement paymentMethodChequeRadioButton;
    @FindBy(id = "place_order")
    WebElement placeOrderButton;
    @FindBy(css = "li.woocommerce-order-overview__order.order")
    WebElement orderNumberWithText;
    @FindBy(linkText = "My account") WebElement myAccountLink;

    // Methods
    public void fillOutShippingAddress() {
        FirstNameField.clear();
        FirstNameField.sendKeys("Devon");
        LatNameField.clear();
        LatNameField.sendKeys("Miles");
        address1Field.clear();
        address1Field.sendKeys("5 King Street");
        cityField.clear();
        cityField.sendKeys("Dundee");
        postcodeField.clear();
        postcodeField.sendKeys("DD1 2PE");
        postcodeField.clear();
        postcodeField.sendKeys("DD1 2PE");
        phoneNumberField.clear();
        phoneNumberField.sendKeys("07753826584");
    }

    public void checkChequeRadioButton() {
        try {
            Thread.sleep(1000); // need it, fix it, but no idea. listening to the conversations, it seems to be a hard one
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        paymentMethodChequeRadioButton.click();
    }

    public void clickPlaceOrder() {
        placeOrderButton.click();
    }

    public String retrieveOrderNumber() {
        waitForElementToBePresentPOM(driver, orderNumberWithText, 2);
        return orderNumberWithText.getText().substring(14, 18);
    }

    public void navigateToMyAccount()  {
        myAccountLink.click();
    }
}
