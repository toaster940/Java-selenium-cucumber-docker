package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class CustomEventListeners implements WebDriverListener {

    @Override
    public void beforeGet(WebDriver driver, String url) {

        System.out.println("Before navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {

        System.out.println("After navigating to: " + url);
    }

    @Override
    public void beforeQuit(WebDriver driver) {

        driver.manage().deleteAllCookies();
    }

    @Override
    public void afterQuit(WebDriver driver) {

    }

    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Before clicking on: " + element.toString());
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("After clicking on: " + element.toString());
    }

}
