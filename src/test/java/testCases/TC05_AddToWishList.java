package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC05_AddToWishList extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC05_AddToWishList.class);

    @Test(
        groups = {"regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void testAddToWishList() {

        logger.info("===== TC05_AddToWishList Test Started =====");

        try {
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking My Account");
            hp.clickMyAccount();

            logger.debug("Navigating to Login page");
            hp.goToLogin();

            LoginPage lp = new LoginPage(getDriver());

            logger.debug("Entering login email");
            lp.setEmail("sid@cloudberry.services");

            logger.debug("Entering login password");
            lp.setPwd("Test123");

            logger.debug("Submitting login");
            lp.clickLogin();

            CategoryPage cp = new CategoryPage(getDriver());

            logger.debug("Navigating to Laptops & Notebooks category");
            cp.clickLaptopsAndNotebooks();

            logger.debug("Clicking Show All products");
            cp.clickShowAll();

            Thread.sleep(500);

            logger.debug("Selecting HP product");
            cp.selectHPProduct();

            ProductPage pp = new ProductPage(getDriver());

            logger.debug("Adding product to Wishlist");
            pp.addToWishlist();

            logger.debug("Verifying Wishlist success message");
            Assert.assertTrue(
                    pp.isSuccessMessageDisplayed(),
                    "Wishlist message not shown."
            );

            logger.info("Product successfully added to Wishlist");

        } catch (AssertionError ae) {

            logger.error("Assertion failed in Add to Wishlist test", ae);
            Assert.fail(ae.getMessage());

        } catch (InterruptedException ie) {

            logger.error("Thread sleep interrupted", ie);
            Thread.currentThread().interrupt();
            Assert.fail("Test interrupted");

        } catch (Exception e) {

            logger.error("Unexpected exception in Add to Wishlist test", e);
            Assert.fail("Test failed due to exception");

        } finally {
            logger.info("===== TC05_AddToWishList Test Completed =====");
        }
    }
}
