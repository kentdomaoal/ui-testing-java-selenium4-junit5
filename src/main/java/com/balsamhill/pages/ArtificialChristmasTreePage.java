package com.balsamhill.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ArtificialChristmasTreePage extends Page {
    @FindBy(xpath = "//div[contains(text(),'Most Popular Trees')]/parent::section/div/ul/li")
    List<WebElement> headerSliderProducts;
    public ArtificialChristmasTreePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getHeaderSliderProducts(){
        List<String> productList = new ArrayList<>();
        for (WebElement product: headerSliderProducts) {
            productList.add(product.getText());
        }
        return productList;
    }


}
