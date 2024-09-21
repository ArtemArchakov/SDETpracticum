package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddCustomerPage {
    private final WebDriverWait wait;
    WebDriver driver;

    WebElement firstNameField;
    WebElement lastNameField;
    WebElement postCodeField;
    WebElement addCustomerButton;

    public AddCustomerPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='First Name']")));
        firstNameField = driver.findElement(By.cssSelector("input[placeholder='First Name']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='lName']")));
        lastNameField = driver.findElement(By.xpath("//input[@ng-model='lName']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='postCd']")));
        postCodeField = driver.findElement(By.xpath("//input[@ng-model='postCd']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit'].btn.btn-default")));
        addCustomerButton = driver.findElement(By.cssSelector("button[type='submit'].btn.btn-default"));
    }

    public void enterFirstName(String firstName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.click();
        firstNameField.sendKeys(firstName);
        System.out.println("Entered First Name: " + firstName);
    }

    public void enterLastName(String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(lastNameField));
        lastNameField.click();
        lastNameField.sendKeys(lastName);
        System.out.println("Entered Last Name: " + lastName);
    }

    public void enterPostCode(String postCode) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(postCodeField));
        postCodeField.click();
        postCodeField.sendKeys(postCode);
        System.out.println("Entered Post Code: " + postCode);
    }

    public void submit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCustomerButton);
        System.out.println("Clicked 'Add Customer' button using JavascriptExecutor");
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }

    public WebElement getLastNameField() {
        return lastNameField;
    }

    public WebElement getPostCodeField() {
        return postCodeField;
    }

    public WebElement getSubmitButton() {
        return addCustomerButton;
    }
}