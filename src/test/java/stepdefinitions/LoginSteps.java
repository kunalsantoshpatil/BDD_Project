package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import pages.LoginPage;
import utilities.ScreenshotUtil;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class LoginSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Given("User navigates to the login page")
    public void navigate_to_login() {
        driver.get("https://automationexercise.com");
        loginPage = new LoginPage(driver);
        loginPage.goToLogin();
    }

    @When("User enters valid email and password")
    public void enter_valid_credentials() {
        loginPage.enterCredentials("testuser@example.com", "test123");
        loginPage.clickLogin();
    }

    @Then("User should be logged in successfully")
    public void verify_login_success() {
        boolean result = loginPage.isLoggedIn();
        Assert.assertTrue(result, "Login failed!");
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {
        // Always capture screenshot
        String base64Screenshot = ScreenshotUtil.takeScreenshotBase64(driver);

        if (scenario.isFailed()) {
            ExtentCucumberAdapter.addTestStepLog("❌ Test Failed: " + scenario.getName());
        } else {
            ExtentCucumberAdapter.addTestStepLog("✅ Test Passed: " + scenario.getName());
        }

        // Embed screenshot directly as Base64 (CSP safe for Jenkins)
        ExtentCucumberAdapter.addTestStepLog(
            "<img src='data:image/png;base64," + base64Screenshot + "' height='300' />"
        );

        driver.quit();
    }
}
