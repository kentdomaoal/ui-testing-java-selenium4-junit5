package com.balsamhill.pages;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);
    private final WebDriver driver;
    protected final WebDriverWait wait;
    protected Actions actions;

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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        LOGGER.debug("Base page initialized.");
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

        actions = new Actions(driver);
        actions.scrollToElement(mainOptionElement).perform();
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

    public void sortBy(String option) {
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfProducts));
        Select sortOptionElement = new Select(sortOption);
        sortOptionElement.selectByVisibleText(option);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public List<String> getProductList(){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfProducts));
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

    public String getProductByIndex(int index){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfProducts));
        WebElement product = listOfProducts.get(index-1);
        actions.moveToElement(product).perform();
        return product.getText();
    }

    public Boolean isOptionNotSelected(String option){
        try{
            driver.findElement(By.xpath(formatXpath(selectedOptionXpath,option)));
            return false;
        } catch (NoSuchElementException e){
            return true;
        }
    }

    public String formatXpath(String xpath, String value){
        return xpath.replace("{}",value);
    }
}
