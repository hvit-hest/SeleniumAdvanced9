package org.courses.tests;

import com.google.common.collect.Ordering;
import org.courses.pages.adminpage.AdminPage;
import org.courses.pages.countriespage.CountriesPage;
import org.courses.utils.Utils;
import org.courses.utils.WebDriverSelection;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class CountryPageTests {

    private WebDriver myPersonalDriver;
    private AdminPage adminPage;
    private final String adminName = new Utils().getTestProperties().getProperty("adminName");
    private final String adminPassword = new Utils().getTestProperties().getProperty("adminPassword");

    @BeforeClass
    public void beforeClass() {
        myPersonalDriver = new WebDriverSelection().getDriverFromProperties();
        myPersonalDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        adminPage = new AdminPage(myPersonalDriver);
        adminPage.open();
        adminPage.login(adminName, adminPassword);
    }

    @Test
    public void CountryPageTest() {
        CountriesPage countriesPage = new CountriesPage(myPersonalDriver);
        countriesPage.open();
        List<String> columnData = countriesPage.getCountriesTable().getColumnsText(4);
        Assert.assertTrue(columnData.size()!=0 && Ordering.natural().isOrdered(columnData));
    }

    @AfterClass
    public void afterClass() {
        //Quit driver
        myPersonalDriver.quit();
    }
}
