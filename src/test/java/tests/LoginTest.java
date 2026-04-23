package tests;

import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;


@Slf4j
public class LoginTest extends BaseTest {

    @Test
    public void testGoogleSearch() {

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("Selenium TestNG");

        driver.findElement(By.name("q")).submit();


        String title = driver.getTitle();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//        WebElement element = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Gmail')]"))
//        );
        Assert.assertNotNull(title);
        log.info("sdfsdf");
        log.info("dsdfdffdsfsdf");
        log.warn("warńnnnning loggggg");
        log.warn(LocalDateTime.now().toString());

        driver.quit();
    }
}