package PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

    private String serchText = "";

    public LandingPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(tagName = "title")
    private WebElement landingPageTitlElement;

    @FindBy(css = "#search")
    private WebElement searchBoxElement;

    @FindBy(css = "#search_autocomplete ul[role='listbox'] li span")
    private List<WebElement> searchResults;

    @FindBy(css = ".navigation .nav-3")
    private WebElement topNavMenDropDownElement;

    @FindBy(css = ".navigation .nav-3 [class*='submenu'] a span:nth-child(2)")
    private List<WebElement> topNavMenSubMenuElements;

    @FindBy(css = ".level1.nav-3-2 .level2 span")
    private List<WebElement> topNavMenSubMenuChildElements;

    @FindBy(css = ".product-items .item")
    private List<WebElement> producListElements;

    @FindBy(css = ".tocart")
    private WebElement toCardButtonElement;

    @FindBy(css = ".swatch-option.text")
    private List<WebElement> sizeOptionElements;

    @FindBy(css = ".swatch-option.color")
    private List<WebElement> colorOptionElements;

    @FindBy(css = ".minicart-wrapper span[class='counter-number']")
    private WebElement itemCountInCart;

    @FindBy(css = "#top-cart-btn-checkout")
    private WebElement checkOutButtonElement;

    @FindBy(css = ".breadcrumbs .item.product strong")
    private WebElement breadcrumbProductName;

    @FindBy(xpath = "//*[text()='shopping cart']")
    private WebElement addedToCartMsgElement;

    @FindBy(css = ".action.showcart")
    private WebElement cartIconElement;

    @FindBy(css = ".opc-progress-bar-item._active")
    private WebElement shippingProgressBarElement;

    @FindBy(css = ".action-show-popup")
    private WebElement addNewAddressElement;

    @FindBy(css = "button.action.continue.primary")
    private WebElement shippingNextButton;

    @FindBy(css = ".shipping-address-item a")
    private WebElement shippingPhoneNumber;

    @FindBy(xpath = "//img[@src~'busy']")
    // or @src~'busy' or @alt = 'Processing...'
    private WebElement shippingPageLoadingIcon;

    @FindBy(css = "#modal-content-11")
    private WebElement shippingAddressModal;

    public String getTitle(WebDriver webDriver) {
        return webDriver.getTitle();
    }

    public void typeInSearchBox(String key) {
        searchBoxElement.isDisplayed();
        searchBoxElement.sendKeys(key);
    }

    public boolean checkSearchResultContainsKey(String key) {
        for (WebElement each : searchResults) {
            if (!each.getText().contains(key)) {
                System.out.println("Search result : " + each + " doesn't include key :" + key);
                return false;
            }
        }
        return true;
    }

    public WebElement getTopNavMenDropDownElement() {
        return topNavMenDropDownElement;
    }

    public List<WebElement> getTopNavMenSubmenuItems() {
        return topNavMenSubMenuElements;
    }

    public List<WebElement> getTopNavMenSubmenuChildItems() {
        return topNavMenSubMenuChildElements;
    }

    public List<WebElement> getSearchResultElements() {

        return searchResults;
    }

    public List<WebElement> getProductListElements() {

        return producListElements;
    }

    public WebElement getToCartbuttonElement() {
        toCardButtonElement.isDisplayed();
        return toCardButtonElement;
    }

    public List<WebElement> getSizeOptionElements() {

        return sizeOptionElements;
    }

    public List<WebElement> getColorOptionElements() {

        return colorOptionElements;
    }

    public WebElement getItemCountInCartElement() {

        return itemCountInCart;
    }

    public WebElement getCheckOutButtonElement() {

        return checkOutButtonElement;
    }

    public WebElement getBreadcrumbProductNameElement() {

        return breadcrumbProductName;
    }

    public WebElement getAddedToCartMsgElement() {

        return addedToCartMsgElement;
    }

    public WebElement getCartIconElement() {

        return cartIconElement;
    }

    public WebElement getshippingProgressBarElement() {

        return shippingProgressBarElement;
    }

    public WebElement getaddNewAddressElement() {

        return addNewAddressElement;
    }

    public WebElement getshippingNextButtonElement() {

        return shippingNextButton;
    }

    public WebElement getshippingPhoneNumberElement() {

        return shippingPhoneNumber;
    }

    public WebElement getshippingPageLoadingIconElement() {

        return shippingPageLoadingIcon;
    }

    public WebElement getshippingAddressModalElement() {

        return shippingAddressModal;
    }

    public String getAddressFieldXpathText(String text) {

        return "//*[text()='" + text + "']";
    }

}
