package com.twoitesting.finalProjectSeleniumWebDriver.baseclassPOM;

import com.twoitesting.finalProjectSeleniumWebDriver.pompages.CartPOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.ShopPOM;
import com.twoitesting.finalProjectSeleniumWebDriver.pompages.myAccountPOM;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBasePOM {
    protected WebDriver driver;
    protected String baseURL = "https://www.edgewordstraining.co.uk/demo-site/";
    protected String userName = "gyorgy.papp@2itesting.com";
    protected String passwd = "gyWSfp4Dv5AZFa";
    protected String couponCode = "edgewords";

    protected BigDecimal priceStringToBigDecimal(String priceValueString) { // takes in a string, looks for a pattern of \d+.\d+ - the number part of a price -, strips the currency and returns the value converted to BigDecimal
        Pattern pattern = Pattern.compile("\\d+.\\d+");
        Matcher matcher = pattern.matcher(priceValueString);
        StringBuilder noCurrencyStringBuilder = new StringBuilder();
        while (matcher.find()) {
            noCurrencyStringBuilder.append(matcher.group(0));
        }
        String noCurrencyString = noCurrencyStringBuilder.toString();
        return BigDecimal.valueOf(Double.valueOf(noCurrencyString));
    }

    @BeforeEach
    void SetUp() {
        System.out.println("@BeforeEach start");

        driver = new ChromeDriver();

        // "demo site" message potentially intercept clicks, make sure it is dismissed
        driver.get(baseURL + "my-account/");
        myAccountPOM myAccount = new myAccountPOM(driver);
        myAccount.dismissBanner();
        System.out.println("Warning banner dismissed");

        // 1. Login to your account at https://www.edgewordstraining.co.uk/demo-site/my-account/ using the account you manually registered
        myAccount.doLogIn(userName, passwd);
        System.out.println("Logged in with username " + userName);

        // 2. Enter the shop using top nav link ‘Shop’
        myAccount.goToShop();
        System.out.println("Navigated to Shop");

        // 3. Add a clothing item to your Cart
        ShopPOM shop = new ShopPOM(driver);
        shop.addProductToCart();
        System.out.println("Beanie added to cart");

        // 4. View the Cart
        shop.goToCart();
        System.out.println("Go to cart");

        System.out.println("@BeforeEach end");
    }

    @AfterEach
    void TearDown() throws InterruptedException {
        // check if there's any discounts applied or products in the basket, if so, remove them
        System.out.println("@AfterEach start");
        driver.get(baseURL + "cart/");
        CartPOM cart = new CartPOM(driver);
        if (!driver.findElements(By.className("woocommerce-remove-coupon")).isEmpty()) {
            cart.removeCoupon();
            System.out.println("Coupon removed");
        }
        if (!driver.findElements(By.className("remove")).isEmpty()) {
            cart.removeProduct();
            System.out.println("Product removed");
        }

        // 8. / 11. Finally - Log Out
        driver.get(baseURL + "my-account/");
        myAccountPOM myAccount = new myAccountPOM(driver);
        myAccount.doLogOut();
        System.out.println("Logged out");

        driver.quit();

        System.out.println("@AfterEach end");

    }
}
