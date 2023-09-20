package StepDefinitions;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Utilities.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class LoginSteps {

    TestContext testContext;
    LoginPage loginPage;
    HomePage homePage;

    public LoginSteps(TestContext context) {
        testContext = context;
        loginPage = testContext.getPageObjectManager().getLoginPage();
        homePage = testContext.getPageObjectManager().getHomePage();
    }

    @Given("User navigates to Login page")
    public void user_navigates_to_login_page() {
        homePage.clickLoginButton();
    }

    @Then("Login page is displayed")
    public void loginPageIsDisplayed() {
        Assert.assertTrue(loginPage.emailLoginPageIsDisplayed());

        String actualLoginHeaderTitle = loginPage.getLoginHeaderTitle();
        Assert.assertEquals("Customer Login", actualLoginHeaderTitle);

        String actualCreateNewAccountText = loginPage.getCreateNewAccountText();
        Assert.assertEquals("Create an Account", actualCreateNewAccountText);
    }
    @When("Input {string} as email, {string} as password, {string} as account type")
    public void inputAsEmailAsPasswordAsAccountType(String email, String password, String account) {
        System.out.println("---" + email);
        System.out.println("---" + password);
        System.out.println("---" + account);
    }

    @When("Input credentials to login without headers")
    public void inputCredentialsToLoginWithoutHeaders(DataTable dataTable) {
        List<String> dataRow = dataTable.row(0);
        String email = dataRow.get(0);

        System.out.println("row index 0 --- " + email);
        loginPage.fillEmailData(email);
        System.out.println("row index 1 --- " + dataRow.get(1));
        System.out.println("row index 2 --- " + dataRow.get(2));
    }

    @When("Input credentials to login with headers table")
    public void inputCredentialsToLoginWithHeadersTable(DataTable dataTable) {
        List<Map<String, String>> dataRow = dataTable.asMaps(String.class, String.class);

        // Use for...loop if you have multiple data table
        for (Map<String, String> dataMap : dataRow) {
            String email = dataMap.get("Email");
            loginPage.fillEmailData(email);
            System.out.println("row header email " + email);
            System.out.println("row index 1 --- " + dataMap.get("Password"));
            System.out.println("row index 2 --- " + dataMap.get("Account Type"));
        }
    }

    @When("User inputs email {string} and password {string}")
    public void user_inputs_email_and_password(String email, String password) {
        loginPage.fillLoginCredentials(email, password);
    }

    @When("User clicks sign in button")
    public void user_clicks_sign_in_button() {
        loginPage.clickSignInButton();
    }

    @When("Click selanjutnya button")
    public void clickSelanjutnyaButton() {
        loginPage.clickSelanjutnyaButton();
    }

}
