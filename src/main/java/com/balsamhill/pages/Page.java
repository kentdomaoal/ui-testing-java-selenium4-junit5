package com.balsamhill.pages;

import com.balsamhill.util.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigFileReader config = new ConfigFileReader();

    // Page Elements
    @FindBy(id = "em-close")
    private WebElement popupCloseButton;
    @FindBy(xpath = "//h1[contains(@class,'head-1')]")
    private WebElement header;
    @FindBy(id = "sortOptions2")
    private WebElement sortOption;
    @FindBy(xpath = "//div[@class='details']/span")
    List<WebElement> listOfProducts;

    // XPaths
    private String navigationLinkXpath = "//div/a[contains(text(),'{}')]";
    private String mainOptionXpath = "//div/span[contains(text(),'{}')]"
            + "/following-sibling::span[@class='arrow' and not(@disabled)]";
    private String subOptionXpath = "//div/span/span[contains(@class,'facet-name-text') "
            + "and contains(text(),'{}') and not(@disabled)]";

    private String selectedOptionXpath = "//div[@class='filter-selected-blk']//span[text()='{}']";

    public Page(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        LOGGER.info("Base page initialized.");
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    public void closePopup(){
        popupCloseButton.click();
    }

    public void navigateTo(String link){
        WebElement navigationLink = driver.findElement(By.xpath(formatXpath(navigationLinkXpath,link)));
        wait.until(ExpectedConditions.elementToBeClickable(navigationLink));
        navigationLink.click();
    }

    public String getHeader(){
        wait.until(ExpectedConditions.visibilityOf(header));
        return header.getText();
    }

    public void selectMainOption(String mainOption){
        WebElement mainOptionElement = driver.findElement(By.xpath(formatXpath(mainOptionXpath,mainOption)));

        Actions actions = new Actions(driver);
        actions.moveToElement(mainOptionElement);
        wait.until(ExpectedConditions.elementToBeClickable(mainOptionElement));
        mainOptionElement.click();
    }

    public void selectSubOption(String subOption){
        WebElement subOptionElement = driver.findElement(By.xpath(formatXpath(subOptionXpath,subOption)));
        wait.until(ExpectedConditions.elementToBeClickable(subOptionElement));
        if(isOptionNotSelected(subOption)) {
            subOptionElement.click();
        }
    }

    public void sortBy(String option){
        Select sortOptionElement = new Select(sortOption);
        sortOptionElement.selectByVisibleText(option);
    }

    public List<String> getProductList(){
        List<String> productList = new ArrayList<>();
        for (WebElement product: listOfProducts) {
            productList.add(product.getText());
        }
        return productList;
    }

    public String getProductByIndex(List<String> productList, int index){
        String product = "";
        for(int i = 0; i < productList.size(); i++){
            if(i+1 == index){
                product = productList.get(i);
                LOGGER.info("Product Name: "+product);
            }
        }
        return product;
    }

    public Boolean isOptionNotSelected(String option){
        try{
            WebElement selectedOption = driver.findElement(By.xpath(formatXpath(selectedOptionXpath,option)));
            return false;
        } catch (NoSuchElementException e){
            return true;
        }
    }

    public String formatXpath(String xpath, String value){
        return xpath.replace("{}",value);
    }
}
