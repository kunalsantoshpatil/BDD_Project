package stepdefinitions;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import pages.LoginPage;
import utilities.ScreenshotUtil;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class LoginSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
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
        loginPage.enterCredentials("testuser@example.com", "test123"); // Use valid creds
        loginPage.clickLogin();
    }

    @Then("User should be logged in successfully")
    public void verify_login_success() {
        boolean result = loginPage.isLoggedIn();
        Assert.assertTrue(result, "Login failed!");
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {
        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, scenario.getName());

        // Relative path from Spark.html which is in "Report/"
        String relativePath = "../" + screenshotPath; // because screenshot is in root/Screenshots

        if (scenario.isFailed()) {
            ExtentCucumberAdapter.addTestStepLog("❌ Test Failed: " + scenario.getName());
            MediaEntityBuilder.createScreenCaptureFromBase64String(relativePath);
        } else {
            ExtentCucumberAdapter.addTestStepLog("✅ Test Passed: " + scenario.getName());
        }

        // Embed screenshot manually
        ExtentCucumberAdapter.addTestStepLog(
            "<a href='" + relativePath + "' target='_blank'>📸 Screenshot</a><br><img src='" + relativePath + "' height='300'/>"
        );

        driver.quit();
    }

}
