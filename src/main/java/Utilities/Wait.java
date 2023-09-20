package Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Wait {

    private static void until(WebDriver webDriver, Duration timeOutInSeconds,
            Function<WebDriver, Boolean> waitCondition) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeOutInSeconds);
        try {
            webDriverWait.until(waitCondition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void untilAjaxCallIsDone(WebDriver webDriver, Duration timeOutInSeconds) {
        until(webDriver, timeOutInSeconds, (function) -> {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) webDriver)
                    .executeScript("return jQuery.active==0");
            if (!isJqueryCallDone)
                System.out.println("jQuery call is in progress");
            return isJqueryCallDone;
        });
    }

    public static void untilPageReadyState(WebDriver webDriver, Duration timeOutInSeconds) {
        until(webDriver, timeOutInSeconds, (function) -> {
            String isPageLoaded = String
                    .valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState"));
            if (isPageLoaded.equals("complete")) {
                return true;
            } else {
                System.out.println("Document is loading");
                return false;
            }
        });
    }

    public static void untilElementIsVisible(WebDriver webDriver, WebElement webElement, Duration timeOutInSeconds) {
        new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void untilListElementIsVisible(WebDriver webDriver, List<WebElement> webElements,
            Duration timeOutInSeconds) {
        new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

    public static void untilPageTitleContains(WebDriver webDriver, Duration timeOutInSeconds, String title) {
        new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.titleContains(title));
    }

    public static void untilElementIsClickable(WebDriver webDriver, WebElement element, Duration timeOutInSeconds) {
        new WebDriverWait(webDriver, timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitTillSpinnerDisable(WebDriver driver, WebElement element) {
        FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
        fWait.withTimeout(Duration.ofSeconds(5));
        fWait.pollingEvery(Duration.ofSeconds(1));
        fWait.ignoring(NoSuchElementException.class);

        Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                System.out.println(element.getCssValue("display"));
                if (element.getCssValue("display").equalsIgnoreCase("none")) {
                    return true;
                }
                return false;
            }
        };

        return fWait.until(func);
    }
}
