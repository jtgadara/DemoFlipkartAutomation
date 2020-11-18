package demoPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.io.FileUtils;
import sun.tools.asm.SwitchData;

public class SupportedClass {
    protected WebDriver driver;
    WebDriverWait wait;

    public void setup(String browserName){
        if(browserName.toLowerCase().contains("firefox"))
        {
        System.out.println(System.getProperty("user.dir"));
        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "/src/test/resources/driver/geckodriver");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver,60);
        }
        else if(browserName.toLowerCase().contains("chrome"))
        {
            System.out.println(System.getProperty("user.dir"));
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/driver/chromedriver");
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver,60);
        }

    }

    public void clickOnElement(String xpathOfElement){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfElement)));
        driver.findElement(By.xpath(xpathOfElement)).click();
    }

    public void displayProductAvailability(List<String> productList, String pincode){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter delivery pincode']")));
        driver.findElement(By.xpath("//input[@placeholder='Enter delivery pincode']")).sendKeys(pincode);
        clickOnElement("//span[text()='Check']");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'_12cXX4')]//span[contains(text(),'"+pincode+"')]")));

        for(int i = 1;i<=productList.size();i++) {

            String ProductName = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div[1]/div/div["+Integer.toString(i+1)+"]//a[contains(@class,'_2Kn22P')]")).getText();
            String messageForProduct = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div[1]/div/div["+Integer.toString(i+1)+"]//a[contains(@class,'_2Kn22P')]//parent::div//following-sibling::div[3]")).getText();
            System.out.println("Product :"+ProductName + " Message :" + messageForProduct);
        }
    }

    public void addProductsToCart(){
        while(!driver.findElements(By.xpath("//button[text()='ADD TO CART']")).isEmpty()){
            List<WebElement> addToCartList = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
            addToCartList.get(0).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Place Order']")));
            if(addToCartList.size()!=1){
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='ADD TO CART']")));
            }
        }
    }

    public List<String> displayAvailableProductsInCompareScreen(){
        List<WebElement> allProduct = driver.findElements(By.xpath("//div[@class='row']//div[@class='col-4-5']//a"));
        List<String> productList = new ArrayList<String>();
        for(int i = 0;i<allProduct.size();i++) {
            System.out.println(allProduct.get(i).getText());
            productList.add(allProduct.get(i).getText());
        }
        return productList;
    }

    public void addProductToCompareScreenByTheirOrderInSearchResult(int [] arr){
        for(int i=0; i<arr.length;i++){
            clickOnElement("//body/div[@id='container']/div[1]/div[3]/div[2]/div[1]/div[2]/div["+String.valueOf(arr[i]+1)+"]/div[1]/div[1]/div[1]/a[1]/div[1]/div[2]/div[1]/label[1]/span[1]");
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='COMPARE']")));
        clickOnElement("//span[text()='COMPARE']");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='row']//div[@class='col-4-5']//a")));
    }

    public void navigateToSubCategory(String category, String subCategory){

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'"+category+"')]"))).build().perform();

        clickOnElement("//span[contains(text(),'"+category+"')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'"+subCategory+"')]")));
        clickOnElement("//a[contains(text(),'"+subCategory+"')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Add to Compare']//parent::label//parent::div//input")));
    }

    public void byPassLoginDialogIfPresent(){
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Get access to your Orders, Wishlist and Recommendations')]")));
        } catch (Exception e) {
            System.out.print("Login Dialog Box is not present, Continue with normal process");
        }
        if(!driver.findElements(By.xpath("//*[contains(text(),'Get access to your Orders, Wishlist and Recommendations')]")).isEmpty()){
            clickOnElement("//button[contains(text(),'✕')]");}
    }

    public void enterQueryToGoogleAndDisplaySuggestion(String queryValue){
        driver.findElement(By.xpath("//input[contains(@aria-label,'Search') and contains(@type,'text')]")).sendKeys("Flipkart");

        clickOnElement("//input[contains(@aria-label,'Search') and contains(@type,'text')]");
        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//ul[contains(@role,'listbox')]"))));

        List<WebElement> searchResults = driver.findElements(By.xpath("//ul[contains(@role,'listbox')]"));

        for (int j = 0; j < searchResults.size(); j++) {
            System.out.println(searchResults.get(j).getText());

        }
    }

    public void OpenFlipkartSite(){
        driver.findElement(By.xpath("//input[contains(@aria-label,'Search') and contains(@type,'text')]")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//a[@href='https://www.flipkart.com/']//h3"))));
        clickOnElement("//a[@href='https://www.flipkart.com/']//h3");

    }
}
