package com.balsamhill;

import com.balsamhill.pages.ArtificialChristmasTreePage;
import com.balsamhill.pages.HomePage;
import com.balsamhill.util.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BalsamHillTest {
    private WebDriver driver;

//    @BeforeEach
    public void setUp() {
        DriverManager driverManager = new DriverManager();
        driver = driverManager.createWebDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

//    @AfterEach
    public void tearDown() {
        driver.close();
    }

//    @Test
    public void advanceSearchChristmasTree() {
        HomePage homePage = new HomePage(driver);
        homePage.loadPage();
        homePage.refreshPage();
        homePage.closePopup();
        homePage.navigateTo("Artificial Christmas Trees");

        ArtificialChristmasTreePage page = new ArtificialChristmasTreePage(driver);
        assertThat(page.getHeader(),equalTo("Artificial Christmas Trees"));

        page.selectMainOption("Decoration Options");
        page.selectSubOption("Undecorated");
        page.sortBy("Price Low to High");

        List<String> productList = page.getProductList();
        assertThat(page.getProductByIndex(productList, 1),equalTo("Classic Blue Spruce"));
        assertThat(page.getProductByIndex(productList, 3),equalTo("Frosted Alpine Balsam Fir Tree"));
        assertThat(page.getProductByIndex(productList, 4),equalTo("Berkshire Mountain Fir"));
    }



}
