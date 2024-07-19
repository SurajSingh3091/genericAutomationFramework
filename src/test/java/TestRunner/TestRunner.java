package TestRunner;

import FrameworkUtility.BaseClass;
import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.getProperty;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = {"src/test/resources/featureFiles/"},
        glue = {"StepDefinition"},
        tags = ("@DealCapture"),
        monochrome = true,
        dryRun = false,
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:", "pretty", "html:target/cucumber-reports/WebReports/web"}
)

public class TestRunner {

    @BeforeClass
    public static void setup() throws IOException {
        //BaseClass baseClass= new BaseClass();
        BaseClass.executionProperties();
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime()).replaceAll(":", "-");
        String userDir = getProperty("user.dir");
        extentProperties.setReportPath(userDir + "/target/ProjectReports/" + (BaseClass.isDesktopApplication ? "DesktopApplicationReport_" : BaseClass.isAPI ? "APIReport_" : "WebApplicationReport_") + timeStamp + ".html");
    }

    @AfterClass
    public static void teardown() throws IOException {
        Reporter.loadXMLConfig(new File(BaseClass.projectLocation + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Properties" + File.separator + "execution.properties"));
        Reporter.setSystemInfo("User Name", "Suraj Singh");
        Reporter.setSystemInfo("Application Name", "GenericApplication");
        Reporter.setSystemInfo("Operating System Type", getProperty("os.name").toString());
        Reporter.setSystemInfo("Environment", "SIT");
        Reporter.setSystemInfo("Platform", "Desktop");
        Reporter.setSystemInfo("Browser", "Chrome");
        Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
    }
}
