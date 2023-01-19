package com.balsamhill.steps;

import com.balsamhill.pages.ArtificialChristmasTreePage;
import com.balsamhill.pages.HomePage;
import com.balsamhill.util.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PageSteps {
    private WebDriver driver;
    private HomePage homePage;
    private ArtificialChristmasTreePage page;
    private final DriverManager driverManager;

    public PageSteps(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @Before
    public void setUp() {
        driver = driverManager.createWebDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After
    public void tearDown(Scenario scenario) {
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        driver.close();
    }

    @Given("User visit the Balsam Hill website")
    public void user_visit_the_balsam_hill_website() {
        homePage = new HomePage(driver);
        homePage.loadPage();
        homePage.refreshPage();
        homePage.closePopup();
    }
    @When("user navigates to {string}")
    public void user_search_for(String link) {
        homePage.navigateTo(link);
    }
    @When("refine the results using the following options")
    public void refine_the_results_using_the_following_options(DataTable dataTable) throws InterruptedException {
        page = new ArtificialChristmasTreePage(driver);
        assertThat(page.getHeader(),equalTo("Artificial Christmas Trees"));

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            page.selectMainOption(columns.get("mainOption"));
            page.selectSubOption(columns.get("subOption"));
        }
    }
    @When("sort the results by {string}")
    public void sort_the_results_by(String sortOption) throws InterruptedException {
        page.sortBy(sortOption);
    }
    @Then("user should see the following products in order")
    public void user_should_see_the_following_products_in_order(DataTable dataTable) {
//        List<String> productList = page.getProductList();

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            assertThat(page.getProductByIndex(Integer.parseInt(columns.get("order")))
                    ,equalTo(columns.get("productName")));
        }
    }

}
