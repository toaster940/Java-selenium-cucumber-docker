package StepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import Enums.RelativeLocators;
import Managers.FileReaderManager;
import PageObjects.HomePage;
import PageObjects.LandingPage;
import PageObjects.LoginPage;
import Utilities.CommonUtils;
import Utilities.TestContext;
import Utilities.Wait;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LandingSteps {
    TestContext testContext;
    LandingPage landingPage;
    HomePage homePage;
    LoginPage loginPage;
    WebDriver webDriver;
    CommonUtils commonUtils;

    public LandingSteps(TestContext testContext) {
        this.testContext = testContext;
        landingPage = testContext.getPageObjectManager().getLandingPage();
        homePage = testContext.getPageObjectManager().getHomePage();
        loginPage = testContext.getPageObjectManager().getLoginPage();
        webDriver = testContext.getDriverManager().getDriver();
        commonUtils = new CommonUtils(testContext);
    }

    @Then("User lands on landing page")
    public void user_lands_on_landing_page() {
        String actualTitle = landingPage.getTitle(webDriver);
        Assert.assertEquals(actualTitle, "Home Page");
    }

    @Given("User is on Landing page")
    public void user_is_on_landing_page() {
        homePage.clickLoginButton();
        String[] credential = FileReaderManager.getInstance().getConfigFileReader().getCredentials();
        loginPage.fillLoginCredentials(credential[0], credential[1]);
        loginPage.clickSignInButton();
    }

    @Given("Landing page is displayed")
    public void landing_page_is_displayed() {
        user_lands_on_landing_page();
    }

    @When("User seacrhes product {string} from the search box")
    public void user_seacrhes_product_from_the_search_box(String key) {
        landingPage.typeInSearchBox(key);
    }

    @When("User should see key {string} in all results")
    public void user_should_see_key_in_all_results(String key) {
        landingPage.checkSearchResultContainsKey(key);
    }

    @When("User moves cursor to top nav men menu")
    public void user_moves_cursor_to_top_nav_men_menu() {
        WebElement element = landingPage.getTopNavMenDropDownElement();
        Actions mouseActions = new Actions(webDriver);
        mouseActions.moveToElement(element).build().perform();
    }

    @Then("User sees {string} and {string} as suggestions from top nav")
    public void user_sees_and_as_suggestions_from_top_nav(String item1, String item2) {
        List<WebElement> items = landingPage.getTopNavMenSubmenuItems();
        Assert.assertEquals(items.get(0).getText(), item1);
        Assert.assertEquals(items.get(1).getText(), item2);
    }

    @When("User mover cursor to Bottoms")
    public void user_mover_cursor_to_bottoms() {
        WebElement item = landingPage.getTopNavMenSubmenuItems().get(1);
        Actions mouseActions = new Actions(webDriver);
        mouseActions.moveToElement(item).build().perform();
    }

    @Then("User sees {string} and {string} as suggestions from submenu")
    public void user_sees_and_as_suggestions_from_submenu(String item1, String item2) {
        List<WebElement> items = landingPage.getTopNavMenSubmenuChildItems();
        Assert.assertEquals(items.get(0).getText(), item1);
        Assert.assertEquals(items.get(1).getText(), item2);
    }

    @When("User selects an item from suggestions")
    public void user_selects_an_item_from_suggestions() {
        List<WebElement> items = landingPage.getSearchResultElements();
        for (WebElement eachItem : items) {
            if (eachItem.getText().contains("women")) {
                eachItem.click();
                break;
            }
        }
    }

    @Then("User lands on {string} title page")
    public void user_lands_on_title_page(String title) {
        Wait.untilPageReadyState(webDriver, Duration.ofSeconds(5));
        commonUtils.verifyPageTitleContains(title);
    }

    @When("User clicks on shorts from suggestions")
    public void user_clicks_on_shorts_from_suggestions() {
        List<WebElement> items = landingPage.getTopNavMenSubmenuChildItems();
        for (WebElement eachItem : items) {
            if (eachItem.getText().contains("Shorts")) {
                eachItem.click();
                break;
            }
        }
    }

    @When("User selects a product and proceed to add it to cart")
    public void user_selects_a_product_and_proceed_to_add_it_to_cart() {
        Actions mouseActions = new Actions(webDriver);
        WebElement itemToAdd = landingPage.getProductListElements().get(0);
        mouseActions.moveToElement(itemToAdd).build().perform();
        itemToAdd.findElement(By.cssSelector(".tocart")).click();

    }

    @When("User selects size and color of the item and add it to the cart")
    public void user_selects_size_and_color_of_the_item_and_add_it_to_the_cart() {
        Wait.untilElementIsVisible(webDriver, landingPage.getBreadcrumbProductNameElement(), Duration.ofSeconds(5));
        landingPage.getSizeOptionElements().get(0).click();
        landingPage.getColorOptionElements().get(0).click();
        landingPage.getToCartbuttonElement().click();
    }

    @Then("User sees item gets added to the cart")
    public void user_sees_item_gets_added_to_the_cart() {
        Wait.untilElementIsVisible(webDriver, landingPage.getAddedToCartMsgElement(), Duration.ofSeconds(3));
        int count = Integer.parseInt(landingPage.getItemCountInCartElement().getText());
        Assert.assertTrue(count > 0);
    }

    @Then("User performs proceed to checkout action")
    public void user_performs_proceed_to_checkout_action() throws InterruptedException {
        landingPage.getCartIconElement().click();
        landingPage.getCheckOutButtonElement().click();
        // Wait.waitTillSpinnerDisable(webDriver,
        // landingPage.getshippingPageLoadingIconElement());
        // Wait.untilElementIsClickable(webDriver,
        // landingPage.getshippingPhoneNumberElement(), 5L);
        Thread.sleep(7000);
    }

    @Then("User clicks on add new address")
    public void user_clicks_on_add_new_address() {
        landingPage.getaddNewAddressElement().click();
    }

    @Then("User fill in address details")
    public void user_fill_in_address_details() throws IOException {
        HashMap<String, String> addressData = commonUtils.getDataFromExcel("ShippingAddress");
        WebElement dropdownWebElement = null;
        for (Entry<String, String> eachPair : addressData.entrySet()) {

            if (eachPair.getKey().equals("State/Province") || eachPair.getKey().equals("Country")) {
                dropdownWebElement = commonUtils.getRelativeElement(webDriver, By.tagName("select"),
                        RelativeLocators.BELOW,
                        webDriver.findElement(By.xpath(landingPage.getAddressFieldXpathText(eachPair.getKey()))));
                Select select = new Select(dropdownWebElement);
                select.selectByVisibleText(eachPair.getValue());
            } else {

                WebElement inputField = commonUtils.getRelativeElement(webDriver, By.tagName("input"),
                        RelativeLocators.BELOW,
                        webDriver.findElement(By.xpath(landingPage.getAddressFieldXpathText(eachPair.getKey()))));
                Wait.untilElementIsClickable(webDriver, inputField, Duration.ofSeconds(2));
                // inputField.clear();
                inputField.sendKeys(eachPair.getValue());
            }
        }
        commonUtils.elementScreenShot(webDriver, dropdownWebElement);
    }

    public void temp() {
        testContext.getDriverManager().getDriver().findElement(By.xpath("null")).getScreenshotAs(OutputType.BYTES);
    }
}
