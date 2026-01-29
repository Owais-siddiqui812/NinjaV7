package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class Tc_01Launchapplication extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(Tc_01Launchapplication.class);

    @Test(
        groups = {"sanity","regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void Testlaunch() {

        logger.info("===== TC_01 : Launch Application Test Started =====");

        try {
            logger.debug("Fetching application title");
            String actualTitle = getDriver().getTitle();
            logger.debug("Actual Title: {}", actualTitle);

            String expectedTitle = "Your store of fun";
            logger.debug("Expected Title: {}", expectedTitle);

            Assert.assertEquals(actualTitle, expectedTitle);

            logger.info("Title verification PASSED");

        } catch (AssertionError ae) {

            logger.error("Title verification FAILED", ae);

            // Fail the test explicitly so RetryAnalyzer can re-run it
            Assert.fail("Assertion failed: Title mismatch");

        } catch (Exception e) {

            logger.error("Unexpected error occurred during test execution", e);
            Assert.fail("Test execution failed due to exception");

        } finally {
            logger.info("===== TC_01 : Launch Application Test Completed =====");
        }
    }
}
