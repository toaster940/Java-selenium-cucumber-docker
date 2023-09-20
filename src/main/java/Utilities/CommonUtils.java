package Utilities;

import io.cucumber.java.Scenario;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

import Enums.RelativeLocators;
import Managers.FileReaderManager;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonUtils {

    TestContext testContext;

    public CommonUtils(TestContext context) {
        testContext = context;
    }

    public void takeScreenShot(Scenario scenario) {

        try {
            byte[] screenshot = ((TakesScreenshot) testContext.getDriverManager().getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        } catch (WebDriverException noSupportScreenshot) {
            System.err.println(noSupportScreenshot.getMessage());
        }
    }

    public Actions mouseAction() {
        WebDriver driver = testContext.getDriverManager().getDriver();
        return new Actions(driver);
    }

    public String getPageTitile() {

        return testContext.getDriverManager().getDriver().getTitle();
    }

    public HashMap<String, String> getDataFromExcel(String datafromSheetName) {

        String excelFilePath = FileReaderManager.getInstance().getConfigFileReader().getExcelPath();
        HashMap<String, String> dataMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheetName = workbook.getSheet(datafromSheetName);

            // Iterate over rows
            for (Row row : sheetName) {
                // Get the key from the first cell
                Cell keyCell = row.getCell(0);
                String key = keyCell.getStringCellValue();

                // Get the value from the second cell
                Cell valueCell = row.getCell(1);
                String value = "";
                if (valueCell != null) {
                    CellType cellType = valueCell.getCellType();

                    // Handle different cell types
                    switch (cellType) {
                        case STRING:
                            value = valueCell.getStringCellValue();
                            break;
                        case NUMERIC:
                            value = String.valueOf(valueCell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            value = String.valueOf(valueCell.getBooleanCellValue());
                            break;
                    }
                }
                // Add key-value pair to the map
                dataMap.put(key, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    public WebElement getRelativeElement(WebDriver driver, By by, RelativeLocators relativeLocator,
            WebElement webElement) {

        driver = testContext.getDriverManager().getDriver();

        WebElement relativeElement;
        switch (relativeLocator) {
            case ABOVE:
                relativeElement = driver.findElement(with(by).above(webElement));
                break;
            case BELOW:
                relativeElement = driver.findElement(with(by).below(webElement));
                break;
            case TO_LEFT_OFF:
                relativeElement = driver.findElement(with(by).toLeftOf(webElement));
                break;
            case TO_RIGHT_OFF:
                relativeElement = driver.findElement(with(by).toRightOf(webElement));
                break;
            default:
                relativeElement = null;
        }
        return relativeElement;
    }

    public void takeScreenShotOfElement(Scenario scenario, WebElement element) {

        byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "screenshot");
    }

    public void verifyPageTitleContains(String title) {
        boolean verify = false;
        String actualTitle = getPageTitile();
        if (actualTitle.contains(title)) {
            verify = true;
        } else {
            System.out
                    .println("Expecting page title to contain : " + title + " But, page title found : " + actualTitle);
        }
        Assert.assertTrue(verify);

    }

    public void elementScreenShot(WebDriver driver, WebElement element) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get the location of element on the page
        org.openqa.selenium.Point point = element.getLocation();

        // Get width and height of the element
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);

        // Copy the element screenshot to disk
        String path = "C:\\images\\GoogleLogo_screenshot.png";
        File screenshotLocation = new File(path);
        FileUtils.copyFile(screenshot, screenshotLocation);
        
    }
}
