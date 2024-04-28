package appium;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class login {
    public void loginWithCredentials() throws InterruptedException, IOException { 
        Properties properties = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String email = properties.getProperty("email");
        String password = properties.getProperty("password");

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("udid", "6b58d3010508");
        cap.setCapability("deviceName", "Xiaomi 21061119AG");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "13");
        cap.setCapability("browserName", "Chrome");
        cap.setCapability("chromedriverExecutable", "C:\\Users\\Administrator\\Desktop\\New folder\\demo\\chromedriver-win64\\chromedriver.exe");

        URI uri = URI.create("http://127.0.0.1:4725");
        URL url = uri.toURL();
        
        AndroidDriver androidDriver = new AndroidDriver(url, cap);
        
        androidDriver.get("https://vle.hcmue.edu.vn/users/users/login");

        // Thực hiện quá trình đăng nhập
        WebElement emailBase = androidDriver.findElement(By.id("input_email"));
        WebElement passwordBase = androidDriver.findElement(By.id("input_password"));
        WebElement loginBtn = androidDriver.findElement(By.className("btn-signupwith"));

        // Nhập tên người dùng và mật khẩu
        if (email != null && password != null) {
            emailBase.sendKeys(email);
            passwordBase.sendKeys(password);
        } else {
            System.out.println("Email hoac password rong");
        }

        // Nhấp vào nút đăng nhập
        loginBtn.click();

        // Kiểm tra URL của trang sau khi đăng nhập
        String expectedURL = "https://vle.hcmue.edu.vn/dashboard/student";
        String actualURL = androidDriver.getCurrentUrl();
        if (!actualURL.equals(expectedURL)) {
            System.out.println("Dang nhap khong thanh cong. URL khong khop");
        }

        Thread.sleep(3000);
        androidDriver.quit();
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException, IOException {
        login loginTest = new login();
        loginTest.loginWithCredentials();
    }
}
