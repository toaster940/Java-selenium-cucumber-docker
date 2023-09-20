package Managers;

import Enums.DriverType;
import Enums.EnvironmentType;
import Utilities.CustomEventListeners;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AllDriverManager {

    private WebDriver webDriver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;

    public AllDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigFileReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigFileReader().getEnvironment();
    }

    private WebDriver createLocalDriver() {
        WebDriver decoratedDriver = null;
        WebDriverListener listener;
        switch (driverType) {
            case CHROME:
                //WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                // chromeOptions.setBrowserVersion("116");
                chromeOptions.addArguments("--window-size=1644,868");
                webDriver = new ChromeDriver();
                listener = new CustomEventListeners();
                decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                webDriver = new FirefoxDriver(firefoxOptions);
                listener = new CustomEventListeners();
                //decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                listener = new CustomEventListeners();
                //decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                break;
            case SAFARI:
                webDriver = new SafariDriver();
                listener = new CustomEventListeners();
                //decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                break;
        }
        long time = FileReaderManager.getInstance().getConfigFileReader().getTime();

        decoratedDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        decoratedDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        decoratedDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(time));
        return decoratedDriver;
    }

    private WebDriver createRemoteDriver() {

        WebDriver decoratedDriver = null;
        WebDriverListener listener;
        switch (driverType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("incognito", "--window-size=1644,868", "--ignore-ssl-errors=yes",
                        "--ignore-certificate-errors");
                try {
                    webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                    listener = new CustomEventListeners();
                    decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                }
                break;
            case FIREFOX:

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--window-size=1644,868", "--ignore-ssl-errors=yes",
                        "--ignore-certificate-errors");
                try {
                    webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
                    listener = new CustomEventListeners();
                    decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                }
                break;

            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--window-size=1644,868", "--ignore-ssl-errors=yes",
                        "--ignore-certificate-errors");
                try {
                    webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), edgeOptions);
                    listener = new CustomEventListeners();
                    decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                }
                break;
            case SAFARI:
                System.out.println("Not yet implemented for Safari browser");

        }
        long time = FileReaderManager.getInstance().getConfigFileReader().getTime();

        decoratedDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        decoratedDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        decoratedDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(time));
        return decoratedDriver;
    }

    private WebDriver createDriver() {
        switch (environmentType) {
            case LOCAL:
                webDriver = createLocalDriver();
                break;
            case REMOTE:
                webDriver = createRemoteDriver();
                break;
        }
        return webDriver;
    }

    public WebDriver getDriver() {
        if (webDriver == null)
            webDriver = createDriver();
        return webDriver;
    }

    public void closeDriver() {
        webDriver.close();
        webDriver.quit();
    }
}
