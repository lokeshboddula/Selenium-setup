package tests;

import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.time.LocalDateTime;


@Slf4j
public class LoginTest extends BaseTest {

    @Test
    public void testGoogleSearch() {

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("Selenium TestNG");

        driver.findElement(By.name("q")).submit();


        String title = driver.getTitle();

        log.info("sdfsdf");
        log.info("dsdfdffdsfsdf");
        log.warn("warńnnnning loggggg");
        log.info("this is used to trigger pipeline{}", title);
        log.warn(LocalDateTime.now().toString());
    }
}