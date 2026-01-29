package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC04_CompletePurchase extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC04_CompletePurchase.class);

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testCompletePurchase() {

        logger.info("===== TC04_CompletePurchase Test Started =====");

        try {
            CategoryPage cp = new CategoryPage(getDriver());

            logger.debug("Navigating to Laptops & Notebooks category");
            cp.clickLaptopsAndNotebooks();

            logger.debug("Clicking Show All products");
            cp.clickShowAll();

            Thread.sleep(500);

            logger.debug("Selecting HP product");
            cp.selectHPProduct();

            ProductPage pp = new ProductPage(getDriver());

            logger.debug("Setting delivery date");
            pp.setDeliveryDate();

            logger.debug("Adding product to cart");
            pp.clickAddToCart();

            logger.debug("Proceeding to Checkout");
            pp.clickCheckout();

            CheckoutPage cop = new CheckoutPage(getDriver());

            logger.debug("Clicking Login from checkout page");
            cop.clickLogin();

            LoginPage lp = new LoginPage(getDriver());

            logger.debug("Entering login email");
            lp.setEmail("sid@cloudberry.services");

            logger.debug("Entering login password");
            lp.setPwd("Test123");

            logger.debug("Submitting login");
            lp.clickLogin();

            logger.debug("Completing checkout steps");
            cop.completeCheckout();

            ConfirmationPage confirmationPage =
                    new ConfirmationPage(getDriver());

            logger.debug("Verifying order confirmation");
            Assert.assertTrue(
                    confirmationPage.isOrderPlaced(),
                    "Order placement failed!"
            );

            logger.info("Order placed successfully");

        } catch (AssertionError ae) {

            logger.error("Assertion failed during order placement", ae);
            Assert.fail(ae.getMessage());

        } catch (InterruptedException ie) {

            logger.error("Thread sleep interrupted", ie);
            Thread.currentThread().interrupt();
            Assert.fail("Test interrupted");

        } catch (Exception e) {

            logger.error("Unexpected exception in complete purchase test", e);
            Assert.fail("Test failed due to exception");

        } finally {
            logger.info("===== TC04_CompletePurchase Test Completed =====");
        }
    }
}
