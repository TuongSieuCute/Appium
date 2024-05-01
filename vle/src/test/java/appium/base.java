package appium;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.android.AndroidDriver;

public class base {
    protected static AndroidDriver androidDriver;
    
    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("udid", "6b58d3010508");
        cap.setCapability("deviceName", "Xiaomi 21061119AG");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "13");
        cap.setCapability("browserName", "Chrome");
        cap.setCapability("chromedriverExecutable", "C:\\Users\\Administrator\\Desktop\\Appium\\vle\\chromedriver-win64\\chromedriver.exe");

        URI uri = URI.create("http://127.0.0.1:4725");
        URL url = uri.toURL();

        androidDriver = new AndroidDriver(url, cap);
    }

    @AfterTest
    public void tearDown() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
    }
}
