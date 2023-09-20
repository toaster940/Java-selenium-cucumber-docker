package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import DataProviders.ConfigFileReader;
import Enums.EnvironmentType;

@CucumberOptions(features = "src/test/resources/features", glue = "StepDefinitions", plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "timeline:target/test-output-thread/"
} , tags =  "@LandingPageTest"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    ConfigFileReader configFileReader = new ConfigFileReader();

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeSuite
    public void startDockerGrid() throws InterruptedException, IOException {
        System.out.println("================ BEFORE SUITE STARTED ================");

        try {
            configFileReader.getEnvironment();
            if (configFileReader.getEnvironment() == EnvironmentType.REMOTE) {
                System.out.println("================ Starting Docker Grid ================");
                Runtime.getRuntime().exec("cmd /c start start_dockergrid.bat");
                Thread.sleep(20000);
            }
        } catch (Exception e) {
            System.out.println(
                    "================ No browser and environment parameter passed running on defaul values from config file ================");
        }

    }

    @AfterSuite
    public void stopDockerGrid() throws IOException, InterruptedException {
        System.out.println("================ AFTER SUITE STARTED================");

        try {
            configFileReader.getEnvironment();
            if (configFileReader.getEnvironment() == EnvironmentType.REMOTE) {
                System.out.println("================ Stopping Docker Grid ================");
                Runtime.getRuntime().exec("cmd /c start stop_dockergrid.bat");
                Thread.sleep(7000);
                Runtime.getRuntime().exec("taskkill /f /im cmd.exe");// closes command prompt
            }
        } catch (Exception e) {

        }

    }
}
