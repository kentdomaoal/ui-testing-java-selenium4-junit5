package com.balsamhill.pages;

import com.balsamhill.util.Target;
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

    // Targets using Dynamic XPath
    private final Target NAVIGATION_LINK = Target.locatedByXpath("//div/a[contains(text(),'{0}')]");
    private final Target MAIN_OPTION = Target.locatedByXpath("//div/span[contains(text(),'{0}')]"
            + "/following-sibling::span[@class='arrow' and not(@disabled)]");
    private final Target SUB_OPTION =  Target.locatedByXpath("//div/span/span[contains(@class,'facet-name-text') "
            + "and contains(text(),'{0}') and not(@disabled)]");
    private final Target SELECTED_OPTION =
            Target.locatedByXpath("//div[@class='filter-selected-blk']//span[text()='{0}']");

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
        WebElement navigationLink = driver.findElement(NAVIGATION_LINK.of(link));
        wait.until(ExpectedConditions.elementToBeClickable(navigationLink)).click();
    }

    public String getHeader(){
        return wait.until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public void selectMainOption(String option){
        WebElement mainOptionElement = driver.findElement(MAIN_OPTION.of(option));
        actions = new Actions(driver);
        actions.scrollToElement(mainOptionElement).perform();
        wait.until(ExpectedConditions.elementToBeClickable(mainOptionElement)).click();
    }

    public void selectSubOption(String option){
        WebElement subOptionElement = driver.findElement(SUB_OPTION.of(option));

        if(isOptionNotSelected(option)) {
            wait.until(ExpectedConditions.elementToBeClickable(subOptionElement)).click();
        }
    }

    public void sortBy(String option) {
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfProducts));
        Select sortOptionElement = new Select(sortOption);
        sortOptionElement.selectByVisibleText(option);
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
            driver.findElement(SELECTED_OPTION.of(option));
            return false;
        } catch (NoSuchElementException e){
            return true;
        }
    }
}
