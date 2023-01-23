package com.balsamhill.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ArtificialChristmasTreePage extends Page {

    @FindBy(xpath = "//div[text()='Most Popular Trees']/parent::section//div[@class='wo-mtslider__title']")
    List<WebElement> headerSliderProducts;
    WebDriver driver;
    public ArtificialChristmasTreePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public List<String> getHeaderSliderProducts() throws InterruptedException {
        List<String> productList = new ArrayList<>();

        for (WebElement product: headerSliderProducts) {
            productList.add(product.getAttribute("textContent"));
        }
        return productList;
    }

}
