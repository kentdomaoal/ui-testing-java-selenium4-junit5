package com.balsamhill.pages;

import com.balsamhill.util.ConfigFileReader;
import org.openqa.selenium.WebDriver;

public class HomePage extends Page {
    private final WebDriver driver;
    private final ConfigFileReader config = new ConfigFileReader();

    public HomePage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    public void loadPage(){
        driver.get(config.getUrl());
    }

}
