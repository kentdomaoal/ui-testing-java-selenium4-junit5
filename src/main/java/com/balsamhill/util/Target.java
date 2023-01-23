package com.balsamhill.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Target {
    private String xPathOrCssSelector;
    private SelectorType selectorType;
    private WebDriver driver;

    private Target(String xPathOrCssSelector, SelectorType selectorType) {
        this.xPathOrCssSelector = xPathOrCssSelector;
        this.selectorType = selectorType;
    }
    private Target(WebDriver driver){
        this.driver = driver;
    }
    public static Target locatedByXpath(String xPathSelector){
        return new Target(xPathSelector, SelectorType.XPATH);
    }
    public static Target locatedByCSS(String cssSelector){
        return new Target(cssSelector, SelectorType.CSS);
    }
    public static Target using(WebDriver driver){
        return new Target(driver);
    }
    public WebElement find(By targetOf){
        return driver.findElement(targetOf);
    }

    public By of (String... parameters){
        switch (selectorType) {
            case XPATH:
                return By.xpath(resolvedWith(parameters));
            case CSS:
                return By.cssSelector(resolvedWith(parameters));
            default:
                throw new RuntimeException("Unsupported selectorType: " + selectorType);
        }
    }
    public String resolvedWith(String[] parameters) {
        String selector = xPathOrCssSelector;

        int index = 0;
        for(String parameter : parameters) {
            String variablePlaceholder = "\\{" + index++ + "\\}";
            selector = selector.replaceAll(variablePlaceholder, parameter);
        }
        return selector;
    }
    private enum SelectorType{
        CSS, XPATH
    }
}
