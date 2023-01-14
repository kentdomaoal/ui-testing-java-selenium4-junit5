package com.balsamhill.util;

import com.balsamhill.enums.DriverType;
import com.balsamhill.enums.EnvironmentType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class DriverManager {
    private static WebDriver webDriver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;

    public DriverManager() {
        ConfigFileReader config = new ConfigFileReader();
        driverType = config.getBrowser();
        environmentType = config.getEnvironment();
    }

    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX:
                return WebDriverManager.firefoxdriver().avoidShutdownHook().create();
            case CHROME:
                return WebDriverManager.chromedriver().avoidShutdownHook().create();
            case EDGE:
                return WebDriverManager.edgedriver().avoidShutdownHook().create();
            case SAFARI:
                return WebDriverManager.safaridriver().avoidShutdownHook().create();
            default:
                throw new RuntimeException("Unsupported driverType: " + driverType);
        }
    }

    private WebDriver createRemoteDriver() {
        throw new RuntimeException("Remote web driver is not yet implemented");
    }

    private WebDriver createHeadlessDriver() {
        switch (driverType) {
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                return new FirefoxDriver(firefoxOptions);
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.setHeadless(true);
                chromeOptions.addArguments("--headless","--window-size=1920,1200");
                return new ChromeDriver(chromeOptions);
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(true);
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Unsupported Headless browser: " + driverType);
        }
    }

    public WebDriver createWebDriver() {
        switch (environmentType) {
            case LOCAL:
                webDriver = createLocalDriver();
                break;
            case REMOTE:
                webDriver = createRemoteDriver();
                break;
            case LOCAL_HEADLESS:
                webDriver = createHeadlessDriver();
                break;
        }
        return webDriver;
    }
}
