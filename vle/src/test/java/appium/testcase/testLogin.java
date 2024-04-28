package appium.testcase;

import org.testng.annotations.Test;

import appium.login;

import java.io.IOException;
import java.net.MalformedURLException;

public class testLogin {
    @Test
    public void testLogin() throws MalformedURLException, InterruptedException, IOException {
        login loginTest = new login();
        loginTest.loginWithCredentials();
    }
}
