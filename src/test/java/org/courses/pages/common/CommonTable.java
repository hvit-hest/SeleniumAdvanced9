package org.courses.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CommonTable {
    private WebDriver driverHere;
    private By tableBy = By.cssSelector("table");
    private By recordBy = By.cssSelector("tr.row");
    private By cellBy = By.cssSelector("td");
    private By headerBy = By.cssSelector("tr.header");
    private By footerBy = By.cssSelector("tr.footer");

    public CommonTable(WebDriver driverHere) {
        this.driverHere = driverHere;
    }

    public CommonTable(WebDriver driverHere, By tableBy) {
        this.driverHere = driverHere;
        this.tableBy = tableBy;
    }

    public CommonTable(WebDriver driverHere, By tableBy, By recordBy, By cellBy) {
        this.driverHere = driverHere;
        this.tableBy = tableBy;
        this.recordBy = recordBy;
        this.cellBy = cellBy;
    }

    public List<WebElement> getRows() {
       return driverHere.findElement(tableBy).findElements(recordBy);
    }

    public WebElement getRow(int row) {
        return getRows().get(row);
    }

    public WebElement getCell(int row, int cell) {
        return getRowCells(row).get(cell);
    }

    public List<WebElement> getRowCells(int row) {
        return getRow(row).findElements(cellBy);
    }

    public List<WebElement> getRowCells(WebElement row) {
        return row.findElements(cellBy);
    }


    public List<WebElement> getColumn(int column) {
        return getRows().stream().map(r -> getRowCells(r).get(column)).collect(toList());
    }

    public List<String> getColumnsText(int column) {
        return getRows().stream().map(r -> getRowCells(r).get(column).getText().trim()).collect(toList());
    }
}
