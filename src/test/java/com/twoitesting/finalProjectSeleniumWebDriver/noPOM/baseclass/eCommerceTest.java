package com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass;

import com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass.TestBase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

import static com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass.utilities.HelpersStatic.*;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;

public class eCommerceTest extends TestBase {

    @Test
    void TestCaseOneTest() {

        // 5. Apply a coupon ‘edgewords’
        driver.findElement(By.id("coupon_code")).sendKeys(couponCode);
        driver.findElement(By.cssSelector("button[name='apply_coupon']")).click();
        WebElement couponMessageWait = waitForElementToBePresent(driver, By.className("woocommerce-message"), 1);
        String couponMessage = couponMessageWait.getText();
        assertThat("Not applied Coupon Code", couponMessage, containsStringIgnoringCase("applied"));

        // 6. Check that the coupon takes off 15%
        // & 7. Check that the total calculated after coupon & shipping is correct

//        WebElement priceStringWait = waitForElementToBePresent(driver, By.cssSelector(".cart-subtotal > td > span > bdi "), 1);
//        String priceString = priceStringWait.getText(); // find total
//        Thread.sleep(250); // need it, fix it
        String priceString = driver.findElement(By.cssSelector(".cart-subtotal > td > span > bdi ")).getText();
        WebElement discountStringWait = waitForElementToBePresent(driver, By.cssSelector(".cart-discount > td > span"), 1);
        String discountString = discountStringWait.getText(); // find discount
//        String discountString = driver.findElement(By.cssSelector(".cart-discount > td > span")).getText(); // find discount
        String shippingString = driver.findElement(By.cssSelector("#shipping_method > li > label > span > bdi")).getText(); // find shipping
        String totalString = driver.findElement(By.cssSelector("tr.order-total > td > strong > span > bdi")).getText(); // find total
        BigDecimal priceBigDecimal = priceStringToBigDecimal(priceString);
        BigDecimal discountBigDecimal = priceStringToBigDecimal(discountString);
        BigDecimal shippingBigDecimal = priceStringToBigDecimal(shippingString);
        BigDecimal totalBigDecimal = priceStringToBigDecimal(totalString);
//        assertThat("discount price value is incorrect", priceBigDecimal.multiply(BigDecimal.valueOf(0.15)), is(equalTo(discountBigDecimal))); // this came back with x.x00 != x.x
        assertThat("Expected discount does not match actual discount", priceBigDecimal.multiply(BigDecimal.valueOf(.15)), Matchers.comparesEqualTo(discountBigDecimal)); // page shows correct discount
        assertThat("Expected total does not match actual total", priceBigDecimal.multiply(BigDecimal.valueOf(.85)).add(shippingBigDecimal), Matchers.comparesEqualTo(totalBigDecimal)); // page calculates correct total with correct discount applied
    }

    @Test
    void TestCaseTwoTest() throws InterruptedException {

        // 5. Proceed to checkout
//        Thread.sleep(250); // need it, fix it
//        driver.findElement(By.linkText("Proceed to checkout")).click();
        WebElement CheckoutButtonWait = waitForElementToBeClickable(driver, By.linkText("Proceed to checkout"), 1);
        CheckoutButtonWait.click(); // ElementClickInterceptedException could happen,fixed it with dismissing "This is a demo store" bottom banner

        // 6. Complete Billing details (you will need to use a valid postcode)
        driver.findElement(By.id("billing_first_name")).clear();
        driver.findElement(By.id("billing_first_name")).sendKeys("Devon");
        driver.findElement(By.id("billing_last_name")).clear();
        driver.findElement(By.id("billing_last_name")).sendKeys("Miles");
        driver.findElement(By.id("billing_address_1")).clear();
        driver.findElement(By.id("billing_address_1")).sendKeys("5 King Street");
        driver.findElement(By.id("billing_city")).clear();
        driver.findElement(By.id("billing_city")).sendKeys("Dundee");
        driver.findElement(By.id("billing_postcode")).clear();
        driver.findElement(By.id("billing_postcode")).sendKeys("DD1 2PE");
        driver.findElement(By.id("billing_phone")).clear();
        driver.findElement(By.id("billing_phone")).sendKeys("07753826584");

        // 7. Select ‘Check payments’ as payment method
        Thread.sleep(1000); // need it, fix it, but no idea. listening to the conversations, it seems to be a hard one
        driver.findElement(By.cssSelector("label[for='payment_method_cheque']")).click();

        // 8. Place the order
        driver.findElement(By.id("place_order")).click();

        // 9. Capture the Order Number and write it to the results
//        Thread.sleep(2000); // need it, fix it
//        String orderNumber = (driver.findElement(By.cssSelector("li.woocommerce-order-overview__order.order")).getText()).substring(14, 18);
        WebElement orderNumberWait = waitForElementToBePresent(driver, By.cssSelector("li.woocommerce-order-overview__order.order"), 2);
        String orderNumber = orderNumberWait.getText().substring(14, 18); // find discount
        System.out.print("the captured order number is: ");
        System.out.println(orderNumber);

        // 10. Navigate to My Account->Orders and check the same order shows in the account
        driver.findElement(By.linkText("My account")).click();
        driver.findElement(By.linkText("Orders")).click();
        assertThat("orders don't contain my order number", driver.findElement(By.cssSelector("tbody > tr:nth-child(1) > td:nth-child(1) > a")).getText(), Matchers.containsString(orderNumber));

        //Thread.sleep(5000); // this is just to give a chance for a quick glance

    }
}
