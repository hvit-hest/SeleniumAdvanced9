package org.courses.tests;

import com.google.common.collect.Ordering;
import org.courses.pages.adminpage.AdminPage;
import org.courses.pages.common.CommonTable;
import org.courses.pages.countriespage.CountriesPage;
import org.courses.pages.countrydatapage.CountryDataPage;
import org.courses.utils.Utils;
import org.courses.utils.WebDriverSelection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CountryPageTests {

    private WebDriver myPersonalDriver;
    private final String adminName = new Utils().getTestProperties().getProperty("adminName");
    private final String adminPassword = new Utils().getTestProperties().getProperty("adminPassword");

    @BeforeClass
    public void beforeClass() {
        myPersonalDriver = new WebDriverSelection().getDriverFromProperties();
        myPersonalDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        AdminPage adminPage = new AdminPage(myPersonalDriver);
        adminPage.open();
        adminPage.login(adminName, adminPassword);
    }

    @Test
    public void CountriesAlphabetOrderTest() {
        CountriesPage countriesPage = new CountriesPage(myPersonalDriver);
        countriesPage.open();
        List<String> countries = countriesPage.getCountriesTable().getColumnsText(4);
        Assert.assertTrue(countries.size() != 0 && Ordering.natural().isOrdered(countries));
    }

    @Test
    public void TimeZonesAlphabetOrderTest() {
        CountriesPage countriesPage = new CountriesPage(myPersonalDriver);
        countriesPage.open();
        CommonTable countriesTable = countriesPage.getCountriesTable();
        List<WebElement> rows = countriesPage.getCountriesTable().getRows();
        rows.forEach(r -> {
            List<WebElement> rowCells = countriesTable.getRowCells(r);
            String numberOfZonesText = rowCells.get(5).getText().trim();
            int numberOfZones = Integer.parseInt(numberOfZonesText);
            if (numberOfZones > 0) {
                rowCells.get(4).findElement(By.cssSelector("a[href]")).click();
                CountryDataPage timeZonesPage = new CountryDataPage(myPersonalDriver);
                List<String> timeZonesList = timeZonesPage.getTimeZonesTable().getColumnsText(2);
                Assert.assertTrue(timeZonesList.size() == numberOfZones
                        && Ordering.natural().isOrdered(timeZonesList));
            }
        });
    }

    @AfterClass
    public void afterClass() {
        //Quit driver
        myPersonalDriver.quit();
    }
}
