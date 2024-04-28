package appium;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class loginTest {
    private AndroidDriver androidDriver;

    @Before
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

    @Test
    public void testLogin() throws InterruptedException {
        String csvFile = "C:\\Users\\Administrator\\Desktop\\Appium\\vle\\src\\test\\java\\appium\\taikhoan.txt";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] account = line.split(cvsSplitBy);
                String email = account[0];
                String password = account[1];

                androidDriver.get("https://vle.hcmue.edu.vn/users/users/login");

                // Kiểm tra sự tồn tại của input_email trước khi tìm kiếm
                if(androidDriver.findElements(By.id("input_email")).size() > 0) {
                    WebElement emailInput = androidDriver.findElement(By.id("input_email"));
                    WebElement passwordInput = androidDriver.findElement(By.id("input_password"));
                    WebElement loginBtn = androidDriver.findElement(By.className("btn-signupwith"));

                    emailInput.sendKeys(email);
                    passwordInput.sendKeys(password);
                    loginBtn.click();

                    // Tạm dừng một thời gian để đợi trang đăng nhập xử lý
                    Thread.sleep(2000);

                    // Kiểm tra URL của trang sau khi đăng nhập
                    String expectedURL = "https://vle.hcmue.edu.vn/dashboard/student";
                    String actualURL = androidDriver.getCurrentUrl();
                    if (actualURL.equals(expectedURL)){
                        System.out.println("Dang nhap thanh cong voi tai khoan: " + email);
                        
                        WebElement menuBtn = androidDriver.findElement(By.cssSelector("button.btn.btn-info.btn-sm.navbar-toggle"));
                        menuBtn.click();
                        Thread.sleep(2000);

                        WebElement avtBtn = androidDriver.findElement(By.cssSelector("img[src='/assets/userimages/noprofileimage.jpg']"));
                        avtBtn.click();
                        Thread.sleep(2000);

                        WebElement logoutBtn = androidDriver.findElement(By.xpath("//*[@id='navbar-collapse-1']/ul/li[4]/div/ul/li[10]/a"));
                        logoutBtn.click();

                    }
                    else
                        System.out.println("Dang nhap that bai voi tai khoan: " + email);

                } 
                else {
                    System.out.println("Khong tim thay input_email");
                }

                // Khởi động lại trang web trước mỗi lần lặp
                restartWebsite();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Khởi động lại trang web
    public void restartWebsite() {
        androidDriver.get("about:blank");
    }

    @After
    public void tearDown() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
    }
}
