package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Slf4j
public class GoogleSearchSteps {

    private final WebDriver driver;

    // PicoContainer will inject the WebDriver from CucumberHooks
    public GoogleSearchSteps(CucumberHooks hooks) {
        this.driver = hooks.getDriver();
    }

    @Given("I am on the Google homepage")
    public void iAmOnTheGoogleHomepage() {
        log.info("Navigating to Google homepage");
        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        driver.findElement(By.name("q")).sendKeys(searchTerm);
        driver.findElement(By.name("q")).submit();
    }

    @Then("I should see search results")
    public void iShouldSeeSearchResults() {
        log.info("Verifying search results are displayed");
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        log.info("Search results page title: {}", title);
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedText) {
        log.info("Verifying page title contains: {}", expectedText);
        String title = driver.getTitle();
        Assert.assertTrue(title.contains(expectedText),
            "Page title '" + title + "' should contain '" + expectedText + "'");
        log.info("Page title verification passed: {}", title);
    }
}
