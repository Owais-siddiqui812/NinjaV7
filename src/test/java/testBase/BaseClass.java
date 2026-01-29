package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties p;

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = {"sanity","regression","dataDriven"})
    @Parameters({ "os", "browser"}) // ✅ added execution
    public void openApp(String os, String br) {

        logger.info("===== Test Execution Started =====");
        logger.info("OS: {} | Browser: {} ", os, br);

        try {
            FileReader file = new FileReader(".//src//test//resources//config.properties");
            p = new Properties();
            p.load(file);

            WebDriver localDriver = null;

            // ================= LOCAL EXECUTION =================
            if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

                logger.info("Running tests on LOCAL machine");

                switch (br.toLowerCase()) {
                    case "chrome":
                        localDriver = new ChromeDriver();
                        break;

                    case "edge":
                        localDriver = new EdgeDriver();
                        break;

                    case "firefox":
                        localDriver = new FirefoxDriver();
                        break;

                    default:
                        throw new RuntimeException("Invalid browser for local execution");
                }
            }

            // ================= REMOTE EXECUTION =================
            else if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

                logger.info("Running tests on SELENIUM GRID");

                URL gridUrl = new URL(p.getProperty("gridUrl"));

                switch (br.toLowerCase()) {

                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.setPlatformName(os);
                        localDriver = new RemoteWebDriver(gridUrl, chromeOptions);
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.setPlatformName(os);
                        localDriver = new RemoteWebDriver(gridUrl, edgeOptions);
                        break;

                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setPlatformName(os);
                        localDriver = new RemoteWebDriver(gridUrl, firefoxOptions);
                        break;

                    default:
                        throw new RuntimeException("Invalid browser for remote execution");
                }
            }

            else {
                throw new RuntimeException(
                    "Invalid execution_env value in config.properties: " 
                    + p.getProperty("execution_env")
                );
            }

            driver.set(localDriver);

            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().window().maximize();

            getDriver().get(p.getProperty("openUrl"));

            logger.info("Application launched successfully");

        } catch (Exception e) {
            logger.error("Error during test setup", e);
            throw new RuntimeException(e);
        }
    }
 
    
    //*****************Teardown**********//
    @AfterClass(groups = {"sanity","regression","dataDriven"})
    public void closeApp() {
        logger.info("Closing browser");
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    //*********Screenshots***********//
    public String captureScreen(String tname) {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try {
            File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String targetFilePath = System.getProperty("user.dir")
                    + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

            File targetFile = new File(targetFilePath);
            sourceFile.renameTo(targetFile);

            return targetFilePath;

        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
            return null;
        }
    }
}
