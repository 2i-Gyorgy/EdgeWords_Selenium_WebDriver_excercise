package com.twoitesting.finalProjectSeleniumWebDriver.pomtests;

import com.twoitesting.finalProjectSeleniumWebDriver.baseclassPOM.TestBasePOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.CartPOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.CheckOutPOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.OrdersPOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.myAccountPOM;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;

public class eCommerceTestPOM extends TestBasePOM {

    @Test
    void TestCaseOneTest() {
        System.out.println("TestCaseOneTest start");

        // 5. Apply a coupon ‘edgewords’
        CartPOM cart = new CartPOM(driver);
        cart.addCoupon(couponCode);
        assertThat("Not applied Coupon Code", cart.retrieveAddCouponResponse(), containsStringIgnoringCase("applied"));
        System.out.println("Coupon code applied");

        // 6. Check that the coupon takes off 15%
        // & 7. Check that the total calculated after coupon & shipping is correct
        String priceString = cart.subTotalString();
        System.out.println("Subtotal amount is: " + priceString);
        String discountString = cart.discountString();
        System.out.println("Discount amount is: " + discountString);
        String shippingString = cart.shippingFeeString();
        System.out.println("Shipping fee amount is: " + shippingString);
        String totalString = cart.totalAmountString();
        System.out.println("Total amount is: " + totalString);

//      make some String to BigDecimal conversions
        BigDecimal priceBigDecimal = priceStringToBigDecimal(priceString);
        BigDecimal discountBigDecimal = priceStringToBigDecimal(discountString);
        BigDecimal shippingBigDecimal = priceStringToBigDecimal(shippingString);
        BigDecimal totalBigDecimal = priceStringToBigDecimal(totalString);
//        assertThat("discount price value is incorrect", priceBigDecimal.multiply(BigDecimal.valueOf(0.15)), is(equalTo(discountBigDecimal))); // this came back with x.x00 != x.x
        assertThat("Expected discount does not match actual discount", priceBigDecimal.multiply(BigDecimal.valueOf(.15)), Matchers.comparesEqualTo(discountBigDecimal)); // page shows correct discount
        assertThat("Expected total does not match actual total", priceBigDecimal.multiply(BigDecimal.valueOf(.85)).add(shippingBigDecimal), Matchers.comparesEqualTo(totalBigDecimal)); // page calculates correct total with correct discount applied
        System.out.println("Good new everyone! The applied discount amount is correct!");

        System.out.println("TestCaseOneTest end");
    }

    @Test
    void TestCaseTwoTest() throws InterruptedException {
        System.out.println("TestCaseTwoTest start");

        // 5. Proceed to checkout
        CartPOM cart = new CartPOM(driver);
        cart.clickCheckoutButton();
        System.out.println("Navigate to cart");

        // 6. Complete Billing details (you will need to use a valid postcode)
        CheckOutPOM checkout = new CheckOutPOM(driver);
        checkout.fillOutShippingAddress();
        System.out.println("fill out shipping details");

        // 7. Select ‘Check payments’ as payment method
        checkout.checkChequeRadioButton();
        System.out.println("Payment method 'cheque' selected");

        // 8. Place the order
        checkout.clickPlaceOrder();
        System.out.println("Order placed");

        // 9. Capture the Order Number and write it to the results
        String orderNumber = checkout.retrieveOrderNumber();
        System.out.print("the captured order number is: ");
        System.out.println(orderNumber);

        // 10. Navigate to My Account->Orders and check the same order shows in the account
        checkout.navigateToMyAccount();
        System.out.println("Go to My Account");

        myAccountPOM myAccount = new myAccountPOM(driver);
        myAccount.navigateToOrders();
        System.out.println("Go to Orders page");

        OrdersPOM orders = new OrdersPOM(driver);
        assertThat("orders don't contain my order number", orders.findOrderNumber(), Matchers.containsString(orderNumber));
        System.out.println("The two captured order numbers match! happy days");
        //Thread.sleep(5000); // this is just to give a chance for a quick glance

        System.out.println("TestCaseTwoTest end");
    }
}
