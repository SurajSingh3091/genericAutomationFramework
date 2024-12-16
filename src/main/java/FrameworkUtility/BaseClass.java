package FrameworkUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    public static boolean isDesktopApplication;
    public static boolean isAPI;
    public static boolean isWebApplication;
    public static boolean isMobileApplication;

    static String desktopAppPath;

    public static String endPointUrl;
    public static String projectLocation;
    static Properties properties;
    public static WebDriver driver;

    public static void setExecutionProperties() {
        //Reading Properties file
        invokePropertyFile();
        isDesktopApplication = Boolean.parseBoolean(properties.getProperty("isDesktopApplication"));
        isAPI = Boolean.parseBoolean(properties.getProperty("isAPI"));
        isWebApplication = Boolean.parseBoolean(properties.getProperty("isWebApplication"));

        if (isDesktopApplication) {
            desktopAppPath = properties.getProperty("desktopApplicationURL");
        }
        if (isAPI) {
            endPointUrl = properties.getProperty("apiEndPoint");
        }
        if(isMobileApplication){

        }
        if (isWebApplication) {
            String browser = properties.getProperty("browserName");
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments();
                    break;
                default:
            }
        }
    }

    public static void invokePropertyFile() {
        projectLocation = System.getProperty("user.dir");
        properties = new Properties();
        try {
            FileInputStream fileInputReader = new FileInputStream(projectLocation + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Properties" + File.separator + "execution.properties");
            properties.load(fileInputReader);
        } catch (IOException exception) {
            System.out.println("Failed to Load properties file");
        }
    }

}