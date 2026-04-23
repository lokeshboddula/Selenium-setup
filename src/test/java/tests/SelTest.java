package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class SelTest extends BaseTest {

//    1. Navigate to bing.com Search page with the query param
//2. Find the locator of each & every result title link and print it
//3. Assert that link is valid or not
//4. Assert that link is relevant to search query param

    @Test
    public void getResults() {
        driver.get("https://www.bing.com/search?q=cars");

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='b_results']//h2/a"));

        for (WebElement e : list) {
            System.out.println(e.getAttribute("href"));
        }

//        Response response  = given().get("https://www.reddit.com/r/cars/new/?feedViewType=classicView").
//
//                when().
//                then().assert().statusCode(200)..statusCode(302).log().all().Response();
        driver.quit();
    }
}
