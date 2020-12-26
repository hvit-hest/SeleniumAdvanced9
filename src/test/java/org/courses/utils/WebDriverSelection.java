package org.courses.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;


public class WebDriverSelection {

    private String getDriverNameFromTestProperties() {
        return new Utils().getTestProperties().getProperty("driver");
    }

    public WebDriver getDriverFromProperties() {
        WebDriver myPersonalDriver = null;
        switch (getDriverNameFromTestProperties().toLowerCase()) {
            case "firefox":
                myPersonalDriver = new FirefoxDriver();
                break;
            case "ie":
                myPersonalDriver = new InternetExplorerDriver();
                break;
            case "chrome":
                myPersonalDriver = new ChromeDriver();
                break;
            default:
                Assert.fail("Error! Check your browser's type");
        }
        return myPersonalDriver;
    }
}

