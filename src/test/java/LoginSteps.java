import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSteps {
    WebDriver driver;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver").getPath());
        driver = new ChromeDriver();
        driver.get("http://localhost:3000");
    }

    @When("I enter {string} in the username field")
    public void i_enter_in_the_username_field(String username) {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(username);
    }

    @When("I enter {string} in the password field")
    public void i_enter_in_the_password_field(String password) {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonText) {
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'" + buttonText + "')]"));
        button.click();
    }

    @Then("I should be redirected to the dashboard page")
    public void i_should_be_redirected_to_the_dashboard_page() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:3000/dashboard"));
        Assert.assertEquals("http://localhost:3000/dashboard", driver.getCurrentUrl());
    }

    @Then("I should see an alert with {string}")
    public void i_should_see_an_alert_with(String expectedMessage) {
        Alert alert = new WebDriverWait(driver, 10).until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(expectedMessage, alert.getText());
        alert.accept();
    }

    @Then("I should see validation errors on the username and password fields")
    public void i_should_see_validation_errors() {
        WebElement usernameError = driver.findElement(By.id("username-helper-text")); // Assuming Material-UI's helper text
        WebElement passwordError = driver.findElement(By.id("password-helper-text"));
        Assert.assertNotNull(usernameError);
        Assert.assertNotNull(passwordError);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
