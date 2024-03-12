package com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.twoitesting.finalProjectSeleniumWebDriver.noPOM.baseclass.utilities.HelpersStatic.*;

public class TestBase {
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
        driver = new ChromeDriver();

        // "demo site" message potentially intercept clicks, make sure it is dismissed
        driver.get(baseURL + "my-account/");
        driver.findElement(By.linkText("Dismiss")).click();

        // 1. Login to your account at https://www.edgewordstraining.co.uk/demo-site/my-account/ using the account you manually registered
//        driver.get(baseURL + "my-account/");
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(passwd);
        driver.findElement(By.cssSelector("button[value='Log in']")).click();

        // 2. Enter the shop using top nav link ‘Shop’
        driver.findElement(By.linkText("Shop")).click();

        // 3. Add a clothing item to your Cart
        driver.findElements(By.linkText("Add to cart")).get(0).click();

        // 4. View the Cart
        WebElement cartButtonWait = waitForElementToBeClickable(driver, By.cssSelector("#site-header-cart > li > a"), 1);
        cartButtonWait.click();
    }

    @AfterEach
    void TearDown() throws InterruptedException {

        // check if there's any discounts applied or products in the basket, if so, remove them
        driver.get(baseURL + "cart/");
        if (!driver.findElements(By.className("woocommerce-remove-coupon")).isEmpty()) {
            WebElement removeCouponWait = waitForElementToBeClickable(driver, By.className("woocommerce-remove-coupon"), 1);
            removeCouponWait.click(); // ElementClickInterceptedException could happen,fixed it with dismissing "This is a demo store" bottom banner
        }
        if (!driver.findElements(By.className("remove")).isEmpty()) {
            driver.findElement(By.className("remove")).click(); // would be nice to create an array of all element found and click all of them
        }
        // this works too
//        try {
//            driver.get(baseURL + "cart/");
//            driver.findElement(By.className("woocommerce-remove-coupon")).click(); // ElementClickInterceptedException - could happen,fixed it with dismissing "This is a demo store" bottom banner
//            driver.findElement(By.className("remove")).click(); // would be nice to create an array of all element found and click all of them
//            Thread.sleep(5000);
//        } catch (NotFoundException e) { // did not test, not sure if it's correct
//            // do nothing
//        }

        // 8. / 11. Finally - Log Out
        driver.get(baseURL + "my-account/");
        driver.findElement(By.linkText("Logout")).click();

        driver.quit();
    }
}
