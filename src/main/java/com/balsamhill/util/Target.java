package com.balsamhill.util;

import org.openqa.selenium.By;

public class Target {
    private final String xPathOrCssSelector;
    private final SelectorType selectorType;

    private Target(String xPathOrCssSelector, SelectorType selectorType) {
        this.xPathOrCssSelector = xPathOrCssSelector;
        this.selectorType = selectorType;
    }

    public static Target locatedByXpath(String xPathSelector){
        return new Target(xPathSelector, SelectorType.XPATH);
    }

    public static Target locatedByCSS(String cssSelector){
        return new Target(cssSelector, SelectorType.CSS);
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
