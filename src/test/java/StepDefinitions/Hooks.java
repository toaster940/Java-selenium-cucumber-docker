package StepDefinitions;

import Managers.FileReaderManager;
import Utilities.CommonUtils;
import Utilities.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

import org.openqa.selenium.WebDriver;

public class Hooks {

    TestContext testContext;
    WebDriver webDriver;
    CommonUtils commonUtils;

    public Hooks(TestContext context) {
        testContext = context;
    }

    @Before
    public void setUp() {
        webDriver = testContext.getDriverManager().getDriver();
        webDriver.get(FileReaderManager.getInstance().getConfigFileReader().getUrl());
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {

    }

    @AfterStep
    public void afterStep(Scenario scenario) {

    }

    @After
    public void tearDown(Scenario scenario) {
        commonUtils = new CommonUtils(testContext);
        commonUtils.takeScreenShot(scenario);
        testContext.getDriverManager().closeDriver();
    }
}
