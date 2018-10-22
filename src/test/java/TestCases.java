import konakartPages.WebsiteLinks;
import konakartPages.HomePage;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.ArrayList;


public class TestCases {

    ChromeDriver myDriver;
    WebDriverWait waitTill;
    Boolean lastItemAdded;

    @Before
    public void setUp(){
        /*create webdriver
        Open website http://www.konakart.com/konakart/Welcome.action
        */
        myDriver = new ChromeDriver();
        myDriver.get(WebsiteLinks.getWebsiteLinks());
        waitTill = new WebDriverWait(myDriver,10);
    }

    @After
    public void tearDown(){
        //closes the webdriver
        myDriver.quit();
    }
    public static void main(String arg[]) throws InterruptedException {
        TestCases Mytask = new TestCases();
        new WebsiteLinks();
        Mytask.seleniumTask();
    }

    @Test
    public void seleniumTask() throws InterruptedException {
        //Select Games categories.
        WebElement games = myDriver.findElementByXPath(HomePage.gamesPath);
        games.click();
        wait(20);
        String priceSliderPath = "//*[@id="+"price-range-slider"+"]/span[2]";
        WebElement priceSlider = myDriver.findElementByXPath(priceSliderPath);
        priceSlider.click();
        wait(20);
        List items =  myDriver.findElementsByClassName("items");
        int listSize = items.size();

        //Add the last one to the cart
        Actions actions = new Actions(myDriver);
        String lastItem = "//*[@id="+"item-overview"+"]/div[2]/ul/li["+listSize+"]";
        WebElement myLastItem = myDriver.findElementByXPath(lastItem);
        String lastItemTitlePath = "//*[@id="+"item-overview"+"]/div[2]/ul/li[5]/div/a";
        WebElement lastItemTitle = myDriver.findElementByXPath(lastItemTitlePath);
        String lastItemTitleText = lastItemTitle.getText();

        actions.moveToElement(myLastItem);
        actions.build().perform();

        String addToCartPath = "//*[@id="+"508atc-24"+"]";
        WebElement addToCartBtn = myDriver.findElementByXPath(addToCartPath);
        addToCartBtn.click();

        //Verify the item added in the cart without opening the cart page hint “Use the menu header”.
        WebElement shoppingCart = myDriver.findElementById("shopping-cart");
        Actions myActions2 = new Actions(myDriver);
        myActions2.moveToElement(shoppingCart);
        myActions2.build().perform();
        String shoppingCartItemTitlePath = "//*[@id="+"shopping-cart-items"+"]/div/a[2]";
        WebElement shoppingCartItemTitle = myDriver.findElementByXPath(shoppingCartItemTitlePath);
        String getShoppingCartTitleText = shoppingCartItemTitle.getText();

        if (lastItemTitleText == getShoppingCartTitleText){
            lastItemAdded = true;
        }
        else lastItemAdded = false;

        //Open the first item (game details page):
        String firstItem = "//*[@id="+"item-overview"+"]/div[2]/ul/li[1]";
        //WebElement myfirstItem = myDriver.findElementByXPath(firstItem);
        String firstItemtitleLink = "//*[@id="+"item-overview"+"]/div[2]/ul/li[1]/div/a";
        WebElement myfirstItemLink = myDriver.findElementByXPath(firstItemtitleLink);
        String firstItemLinkText = myfirstItemLink.getText();
        myfirstItemLink.click();
        wait(30);

        //Verify that the game name is the same as the one you clicked.




    }




}
