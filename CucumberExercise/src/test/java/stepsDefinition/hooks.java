package stepsDefinition;

import Helpers.constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import commons.ElementEvents;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class hooks {
    public static WebDriver driver;
    ExtentHtmlReporter reporter;
    constants con = new constants();
    ElementEvents eL = new ElementEvents();
    public static ExtentReports extentR;
    public static ExtentTest scenarioDef;
    public ExtentTest features;

    @Before
    public void beforeSuite(Scenario scenario) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(constants.baseUrl);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //create the report
        String fileName = "\\Report_" + eL.reportDateTime() + ".html";
        reporter = new ExtentHtmlReporter(con.reportsPath + fileName);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("Test Report");
        reporter.config().setEncoding("uft-8");
        reporter.config().setReportName("Execution Report");

        extentR = new ExtentReports();
        extentR.attachReporter(reporter);

        features = extentR.createTest(Feature.class, "Update user Information");
        scenarioDef = features.createNode(scenario.getName());
    }


    @After
    public void afterSuite(){
        driver.quit();
        extentR.flush();
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        //scenarioDef.createNode(test.testName())
        //String s = getClass().getName();
        //System.out.println(s);
       if(scenario.isFailed()){
            try {
                reportScreenshot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void reportScreenshot() throws IOException {
        var scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(scr.toPath(), new File(con.reportsPath + "screenshot.png").toPath());
        scenarioDef.fail("details").addScreenCaptureFromPath(con.reportsPath + "screenshot" + eL.reportDateTime() +
                ".png");
    }
}
