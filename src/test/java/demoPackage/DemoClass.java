package demoPackage;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class DemoClass extends  SupportedClass{

    String applicationURL = "https://www.google.com/";
    String googleSearchButton = "//input[contains(@value,'Google Search')]";

    @BeforeClass
    @Parameters(value={"browser"})
    public void setup(String browser) {
        super.setup(browser);
    }
    @Test()
    public void testLogin() throws Exception {

        driver.get(applicationURL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(googleSearchButton)));

        enterQueryToGoogleAndDisplaySuggestion("Flipkart");

        OpenFlipkartSite();

        byPassLoginDialogIfPresent();

        navigateToSubCategory("TVs & Appliances", "Window ACs");

        int [] arr = {2,3,6};
        addProductToCompareScreenByTheirOrderInSearchResult(arr);

        List<String> productList = displayAvailableProductsInCompareScreen();

        addProductsToCart();

        displayProductAvailability(productList, "380058");

        driver.findElement(By.xpath("//div[contains(@class,'_12cXX4')]")).click();
        displayProductAvailability(productList, "363641");
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
