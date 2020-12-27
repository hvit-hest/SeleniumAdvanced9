package org.courses.tests;

import org.courses.pages.adminpage.AdminPage;
import org.courses.testdata.MenuItemModel;
import org.courses.utils.Utils;
import org.courses.utils.WebDriverSelection;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;


public class AdminPageMenuTest {

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

    @Test(dataProvider = "getData")
    public void adminPageMenuTest(MenuItemModel itemData) {

        String itemName = itemData.getItemName();
        String pageHeaderExpected = itemData.getPageHeader();
        List<MenuItemModel> subMenu = itemData.getSubMenu();

        adminPage.getMainAdminMenu().selectMenuOption(itemName);
        assertTrue(adminPage.headerIsFound(pageHeaderExpected),
                String.format("For '%s' main menu item - header '%s' was not found", itemName, pageHeaderExpected));

        if (subMenu.size() != 0) {
            //Better have it in reverse so I am not trying a stream. Keep it simple...
            for (int i = subMenu.size() - 1; i >= 0; i--) {
                String subMenuItemName = subMenu.get(i).getItemName();
                String pageHeaderAfterClickingSubMenuExpected = subMenu.get(i).getPageHeader();
                adminPage.getMainAdminMenu().selectSubMenuOption(subMenuItemName);
                assertTrue(adminPage.headerIsFound(pageHeaderAfterClickingSubMenuExpected),
                        String.format("For '%s' submenu item - header '%s' was not found",
                                itemName, pageHeaderAfterClickingSubMenuExpected));
            }
        }
    }

    @AfterClass
    public void afterClass() {
        //Quit driver
        myPersonalDriver.quit();
    }

    @DataProvider
    public Object[] getData() {
        List<MenuItemModel> testData = new Utils().readMenuFromJson("AdminMenuData.json");
        return testData.toArray();
    }
}
