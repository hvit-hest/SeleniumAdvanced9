package org.courses.tests;

import com.google.common.collect.Ordering;
import org.courses.pages.adminpage.AdminPage;
import org.courses.pages.common.CommonTable;
import org.courses.pages.countriespage.CountriesPage;
import org.courses.pages.countrydatapage.CountryDataPage;
import org.courses.pages.editgeozones.EditGeoZonesPage;
import org.courses.pages.geozones.GeoZonesPage;
import org.courses.utils.Utils;
import org.courses.utils.WebDriverSelection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


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
        //Countries with timezones > 0
        Map<String, Integer> countries = rows.stream().
                filter(rr -> Integer.parseInt(countriesTable.getRowCells(rr).get(5).getText().trim()) > 0).
                collect(Collectors.toMap(r -> countriesTable.getRowCells(r).get(4).getText().trim(),
                        r -> Integer.parseInt(countriesTable.getRowCells(r).get(5).getText())));

        /*take country with tz >0, click to open country data page, check order,return back to countries page,
                take next country with tz >0 and click it again, etc*/
        SoftAssert softAssertion = new SoftAssert();
        countries.entrySet().forEach(r -> {
            countriesPage.open();
            countriesPage.getCountriesTable().
                    getCellByTextFromColumn(4, r.getKey()).findElement(By.cssSelector("a[href]")).click();

            CountryDataPage timeZonesPage = new CountryDataPage(myPersonalDriver);
            List<String> timeZonesList = timeZonesPage.getTimeZonesTable().getColumnsText(2);
            softAssertion.assertTrue(timeZonesList.size() == r.getValue()
                    && Ordering.natural().isOrdered(timeZonesList));
        });
        softAssertion.assertAll("Test did not pass");
    }


    //SubTask 2 from 5_1
    @Test
    public void SubTaskN2TimeZonesAlphabetOrderTest() {
        GeoZonesPage geoZonesPage = new GeoZonesPage(myPersonalDriver);
        geoZonesPage.open();
        CommonTable countriesTable = geoZonesPage.getCountriesTable();
        List<WebElement> rows = geoZonesPage.getCountriesTable().getRows();
        //Just List of countries' names
        List<String> countries = rows.stream().map(r -> countriesTable.getRowCells(r).get(2).getText()).
                collect(Collectors.toList());

        SoftAssert softAssertion = new SoftAssert();
        //click every country and check time zones order
        countries.forEach( country -> {
            geoZonesPage.open();
            geoZonesPage.getCountriesTable().
                    getCellByTextFromColumn(2, country).findElement(By.cssSelector("a[href]")).click();
            EditGeoZonesPage editGeoZonesPage = new EditGeoZonesPage(myPersonalDriver);
            List<String> timeZonesList = editGeoZonesPage.getGeoZonesTable().
                    getColumnsText(2, By.cssSelector("[selected]"));
            softAssertion.assertTrue(Ordering.natural().isOrdered(timeZonesList));
        });
        softAssertion.assertAll("Test did not pass");
    }

    @AfterClass
    public void afterClass() {
        //Quit driver
        myPersonalDriver.quit();
    }
}
