package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_AddAffiliate extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC06_AddAffiliate.class);

    @Test(
        groups = {"regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void testAddAffiliate() {

        logger.info("===== TC06_AddAffiliate Test Started =====");

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

            AffiliatePage ap = new AffiliatePage(getDriver());

            logger.debug("Navigating to Affiliate form");
            ap.navigateToAffiliateForm();

            logger.debug("Filling Affiliate details");
            ap.fillAffiliateDetails(
                    "CloudBerry",
                    "cloudberry.services",
                    "123456",
                    "Shadab Siddiqui"
            );

            logger.debug("Verifying Affiliate creation success message");
            Assert.assertTrue(
                    ap.isAffiliateAdded(),
                    "Affiliate details not added successfully."
            );

            logger.info("Affiliate details added successfully");

        } catch (AssertionError ae) {

            logger.error("Assertion failed in Add Affiliate test", ae);
            Assert.fail(ae.getMessage());

        } catch (Exception e) {

            logger.error("Unexpected exception in Add Affiliate test", e);
            Assert.fail("Test failed due to exception");

        } finally {
            logger.info("===== TC06_AddAffiliate Test Completed =====");
        }
    }
}
