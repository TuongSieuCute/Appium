package appium;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class loginTest extends base {
    @Test
    public void testLogin() throws InterruptedException {
        String csvFile = "C:\\Users\\Administrator\\Desktop\\Appium\\vle\\src\\test\\java\\appium\\taikhoan.txt";
        String line = "";
        String cvsSplitBy = ",";
        int testCaseCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                testCaseCount++;
                String[] account = line.split(cvsSplitBy);
                String email = account[0];
                String password = account[1];

                androidDriver.get("https://vle.hcmue.edu.vn/users/users/login");

                // Kiểm tra sự tồn tại của input_email trước khi tìm kiếm
                if (androidDriver.findElements(By.id("input_email")).size() > 0) {
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
                    if (actualURL.equals(expectedURL)) {
                        WebElement menuBtn = androidDriver
                                .findElement(By.cssSelector("button.btn.btn-info.btn-sm.navbar-toggle"));
                        menuBtn.click();
                        Thread.sleep(2000);

                        WebElement avtBtn = androidDriver
                                .findElement(By.cssSelector("img[src='/assets/userimages/noprofileimage.jpg']"));
                        avtBtn.click();
                        Thread.sleep(2000);

                        WebElement logoutBtn = androidDriver
                                .findElement(By.xpath("//*[@id='navbar-collapse-1']/ul/li[4]/div/ul/li[10]/a"));
                        logoutBtn.click();
                    }
                    System.out.println("Test " + testCaseCount + " - PASSED");

                } else {
                    System.out.println("Test " + testCaseCount + " - FAILED");
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
}
