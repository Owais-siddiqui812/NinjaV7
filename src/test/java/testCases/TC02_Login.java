package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;

public class TC02_Login extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC02_Login.class);

    @Test(
        groups = {"sanity", "regression", "dataDriven"},
        dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void testLogin(String email, String pwd) {

        logger.info("===== TC02_Login Test Started =====");
        logger.info("Executing login test for user: {}", email);

        try {
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking on My Account");
            hp.clickMyAccount();

            logger.debug("Navigating to Login page");
            hp.goToLogin();

            LoginPage lp = new LoginPage(getDriver());

            logger.debug("Entering email");
            lp.setEmail(email);

            logger.debug("Entering password");
            lp.setPwd(pwd);

            logger.debug("Clicking Login button");
            lp.clickLogin();

            AccountPage ap = new AccountPage(getDriver());

            logger.debug("Verifying My Account confirmation");
            boolean status = ap.getMyAccountConfirmation().isDisplayed();

            if (status) {
                logger.info("Login successful for user: {}", email);

                logger.debug("Logging out user");
                hp.clickMyAccount();
                ap.Logout();

                Assert.assertTrue(true);

            } else {
                logger.error("Login failed for user: {}", email);
                Assert.fail("My Account confirmation not displayed");
            }

        } catch (AssertionError ae) {

            logger.error("Assertion failed during login test for user: " + email, ae);
            Assert.fail(ae.getMessage());

        } catch (Exception e) {

            logger.error("Unexpected exception during login test for user: " + email, e);
            Assert.fail("Test failed due to exception");

        } finally {
            logger.info("===== TC02_Login Test Completed for user: {} =====", email);
        }
    }
}
