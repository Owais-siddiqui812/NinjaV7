package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC03_AddToCart extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC03_AddToCart.class);

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testAddToCart() {

        logger.info("===== TC03_AddToCart Test Started =====");

        try {
            CategoryPage cp = new CategoryPage(getDriver());

            logger.debug("Clicking on Laptops & Notebooks category");
            cp.clickLaptopsAndNotebooks();

            logger.debug("Clicking on Show All products");
            cp.clickShowAll();

            // Prefer explicit wait in real projects; kept sleep as-is
            Thread.sleep(500);

            logger.debug("Selecting HP product");
            cp.selectHPProduct();

            ProductPage pp = new ProductPage(getDriver());

            logger.debug("Setting delivery date");
            pp.setDeliveryDate();

            logger.debug("Clicking Add to Cart button");
            pp.clickAddToCart();

            logger.debug("Verifying success message after adding product to cart");
            Assert.assertTrue(
                    pp.isSuccessMessageDisplayed(),
                    "Add to Cart Failed!"
            );

            logger.info("Product successfully added to cart");

        } catch (AssertionError ae) {

            logger.error("Assertion failed in Add to Cart test", ae);
            Assert.fail(ae.getMessage());

        } catch (InterruptedException ie) {

            logger.error("Thread sleep interrupted", ie);
            Thread.currentThread().interrupt();
            Assert.fail("Test interrupted");

        } catch (Exception e) {

            logger.error("Unexpected exception in Add to Cart test", e);
            Assert.fail("Test failed due to exception");

        } finally {
            logger.info("===== TC03_AddToCart Test Completed =====");
        }
    }
}
