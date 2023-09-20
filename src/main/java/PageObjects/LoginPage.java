package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//span[contains(text(), 'Customer Login')]")
    private WebElement loginHeaderTitle;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "pass")
    private WebElement passwordField;

    @FindBy(css = ".action.login.primary")
    private WebElement signInButton;

    @FindBy(id = "next")
    private WebElement selanjutnyaButton;

    @FindBy(xpath = "//div[@class='bt-social-group']//button[1]")
    private WebElement loginFacebookButton;

    @FindBy(id = "btnGoogle")
    private WebElement loginGoogleButton;

    @FindBy(xpath = "(//a[contains(@href, 'account/create')])[3]")
    private WebElement createNewAccount;

    @FindBy(xpath = "//a[@href='/register']")
    private WebElement registerLink;

    public boolean emailLoginPageIsDisplayed() {
        loginHeaderTitle.isDisplayed();
        emailField.isDisplayed();
        passwordField.isDisplayed();
        signInButton.isDisplayed();
        return true;
    }

    public String getLoginHeaderTitle() {
        loginHeaderTitle.isDisplayed();
        return loginHeaderTitle.getText();
    }

    public String getCreateNewAccountText() {
        createNewAccount.isDisplayed();
        return createNewAccount.getText();
    }

    public void fillLoginCredentials(String email, String password) {
        emailField.isEnabled();
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.isEnabled();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void fillEmailData(String email){

    }

    public void clickSelanjutnyaButton() {
        selanjutnyaButton.isEnabled();
        selanjutnyaButton.click();
    }

    public void clickSignInButton() {
        signInButton.isEnabled();
        signInButton.click();
    }
}
