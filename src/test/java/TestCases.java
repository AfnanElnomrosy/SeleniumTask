import konakartPages.WebsiteLinks;
import konakartPages.HomePage;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import java.util.*;

import java.util.ArrayList;

public class TestCases {

    public static void main(String arg[]){


        ChromeDriver myDriver = new ChromeDriver();
        myDriver.get(WebsiteLinks.getWebsiteLinks());
        WebElement games = myDriver.findElementByXPath(HomePage.gamesPath);
        games.click();
        List items =  myDriver.findElementsByClassName("items");
        int listSize = items.size();







    }


}
