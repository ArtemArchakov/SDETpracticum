package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Wait;
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='First Name']")));
        firstNameField = driver.findElement(By.cssSelector("input[placeholder='First Name']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='lName']")));
        lastNameField = driver.findElement(By.xpath("//input[@ng-model='lName']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='postCd']")));
        postCodeField = driver.findElement(By.xpath("//input[@ng-model='postCd']"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit'].btn.btn-default")));
        addCustomerButton = driver.findElement(By.cssSelector("button[type='submit'].btn.btn-default"));

        String firstNamePlaceholder = firstNameField.getAttribute("placeholder");
        Assert.assertEquals(firstNamePlaceholder, "First Name", "Плейсхолдер поля 'First Name' не соответствует ожидаемому значению.");

        String lastNamePlaceholder = lastNameField.getAttribute("placeholder");
        Assert.assertEquals(lastNamePlaceholder, "Last Name", "Плейсхолдер поля 'Last Name' не соответствует ожидаемому значению.");

        String postCodePlaceholder = postCodeField.getAttribute("placeholder");
        Assert.assertEquals(postCodePlaceholder, "Post Code", "Плейсхолдер поля 'Post Code' не соответствует ожидаемому значению.");

    }
    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.click();
        firstNameField.sendKeys(firstName);
        System.out.println("Entered First Name: " + firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameField));
        lastNameField.click();
        lastNameField.sendKeys(lastName);
        System.out.println("Entered Last Name: " + lastName);
    }

    public void enterPostCode(String postCode) {
        wait.until(ExpectedConditions.visibilityOf(postCodeField));
        postCodeField.click();
        postCodeField.sendKeys(postCode);
        System.out.println("Entered Post Code: " + postCode);
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCustomerButton);
        System.out.println("Clicked 'Add Customer' button using JavascriptExecutor");
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }
    public void enterCustomerDetails(String firstName, String lastName, String postCode) {
        Wait.waitUntilVisible(driver, getFirstNameField());
        enterFirstName(firstName);

        Wait.waitUntilVisible(driver, getLastNameField());
        enterLastName(lastName);

        Wait.waitUntilVisible(driver, getPostCodeField());
        enterPostCode(postCode);

        Allure.addAttachment("Введенные данные клиента",
                "Имя: " + firstName + "\n" +
                        "Фамилия: " + lastName + "\n" +
                        "Почтовый код: " + postCode);
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

    public void clearFirstName() {
        firstNameField.clear();
    }

    public void clearLastName() {
        lastNameField.clear();
    }

    public void clearPostCode() {
        postCodeField.clear();
    }
}