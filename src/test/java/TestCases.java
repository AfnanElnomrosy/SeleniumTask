import konakartPages.WebsiteLinks;
import konakartPages.HomePage;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.ArrayList;


public class TestCases {

    ChromeDriver myDriver;
    WebDriverWait waitTill;
    boolean lastItemAdded;
    boolean fisrtItemName;
    boolean galleryQuantities;
    boolean firstProductMatches;
    boolean lastProductMatches;
    String priceSliderPath = "//*[@id="+"price-range-slider"+"]/span[2]";
    String lastItemTitlePath = "//*[@id="+"item-overview"+"]/div[2]/ul/li[5]/div/a";
    String shoppingCartItemTitlePath = "//*[@id="+"shopping-cart-items"+"]/div/a[2]";
    String addToCartPath = "//*[@id="+"508atc-24"+"]";
    String firstItemtitleLink = "//*[@id="+"item-overview"+"]/div[2]/ul/li[1]/div/a";
    String pageTitleText;



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

        WebElement priceSlider = myDriver.findElementByXPath(priceSliderPath);
        priceSlider.click();
        wait(20);
        List items =  myDriver.findElementsByClassName("items");
        int listSize = items.size();

        //Add the last one to the cart
        Actions actions = new Actions(myDriver);
        String lastItem = "//*[@id="+"item-overview"+"]/div[2]/ul/li["+listSize+"]";
        WebElement myLastItem = myDriver.findElementByXPath(lastItem);

        WebElement lastItemTitle = myDriver.findElementByXPath(lastItemTitlePath);
        String lastItemTitleText = lastItemTitle.getText();

        actions.moveToElement(myLastItem);
        actions.build().perform();


        WebElement addToCartBtn = myDriver.findElementByXPath(addToCartPath);
        addToCartBtn.click();

        //Verify the item added in the cart without opening the cart page hint “Use the menu header”.
        WebElement shoppingCart = myDriver.findElementById("shopping-cart");
        Actions myActions2 = new Actions(myDriver);
        myActions2.moveToElement(shoppingCart);
        myActions2.build().perform();
        WebElement shoppingCartItemTitle = myDriver.findElementByXPath(shoppingCartItemTitlePath);
        String getShoppingCartTitleText = shoppingCartItemTitle.getText();
        WebElement lastProductPrice = myDriver.findElementByXPath("//*[@id="+"item-overview"+"]/div[2]/ul/li[5]/div/div[3]");
        String lastProductsPriceText = lastProductPrice.getText();

        if (lastItemTitleText == getShoppingCartTitleText){
            lastItemAdded = true;
        }
        else lastItemAdded = false;

        //Open the first item (game details page):
        //String firstItem = "//*[@id="+"item-overview"+"]/div[2]/ul/li[1]";
        //WebElement myfirstItem = myDriver.findElementByXPath(firstItem);

        WebElement myfirstItemLink = myDriver.findElementByXPath(firstItemtitleLink);
        String firstItemLinkText = myfirstItemLink.getText();
        myfirstItemLink.click();
        wait(30);

        //Verify that the game name is the same as the one you clicked.
        WebElement pageTitle = myDriver.findElementById("page-title");
        pageTitleText = pageTitle.getText();

        if (firstItemLinkText == pageTitleText){
            fisrtItemName = true;
        }
        else fisrtItemName = false;
        Assert.assertTrue(fisrtItemName);


        //Verify that the game has 4 screenshots.
        List galleryElements = myDriver.findElementsById("gallery_nav");

        if (galleryElements.size() ==4){
            galleryQuantities = true;
        }
        else galleryQuantities = false;
        Assert.assertTrue(galleryQuantities);

        //Select 2 quantity.
        WebElement selectQuantity = myDriver.findElementById("prodQuantityId");
        Select selectElement = new Select(selectQuantity);
        selectElement.selectByVisibleText("2");
        wait(10);
        WebElement addToCartBtn2 = myDriver.findElementByXPath("//*[@id="+"AddToCartForm"+"]/div[6]/a");
        addToCartBtn2.click();
        wait(20);
        WebElement firstProductPrice = myDriver.findElementById("product-price");
        String firstProductsPriceText = firstProductPrice.getText();

        //Open shopping cart

        shoppingCart.click();
        wait(30);

        //Verify that the added items there.
        String firstProductTitlePath = "//*[@id="+"form1"+"]/table/tbody/tr[1]/td[2]/a[1]";
        WebElement firstProductTitle = myDriver.findElementByXPath(firstProductTitlePath);
        String firstProductTitleText = firstProductTitle.getText();

        String lastProductTitlePath = "//*[@id="+"form1"+"]/table/tbody/tr[2]/td[2]/a[1]";
        WebElement lastProductTitle = myDriver.findElementByXPath(lastProductTitlePath);
        String lastProductTitleText = lastProductTitle.getText();

        if (firstProductTitleText == firstItemLinkText){
            firstProductMatches = true;
        }
        else firstProductMatches = false;

        Assert.assertTrue("first product doesn't exist in the cart",firstProductMatches);

        if (lastProductTitleText == lastItemTitleText){
            lastProductMatches = true;
        }
        else lastProductMatches= false;

        Assert.assertTrue("last product doesn't exist in the cart",lastProductMatches);

        String subtotalPricePath = "//*[@id=\"+\"cost-overview\"+\"]/table/tbody/tr[1]/td[2]";
        WebElement subtotalPrice = myDriver.findElementByXPath(subtotalPricePath);
        String subtotalPricetext = subtotalPrice.getText();
        subtotalPricetext.replace("$","");
        subtotalPricetext.trim();
        int subtotalPriceINT = Integer.parseInt(subtotalPricetext);

        firstProductsPriceText.replace("$","");
        firstProductsPriceText.trim();
        int firstProdPriceINT = Integer.parseInt(firstProductsPriceText);

        lastProductsPriceText.replace("$","");
        lastProductsPriceText.trim();
        int lastProdPriceINT = Integer.parseInt(lastProductsPriceText);

        int expectedSubtotal = lastProdPriceINT + (firstProdPriceINT*2);

        Assert.assertEquals(expectedSubtotal, subtotalPriceINT);


    }




}
