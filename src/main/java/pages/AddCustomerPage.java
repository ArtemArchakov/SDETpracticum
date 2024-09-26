package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Constants;
import utils.Wait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class AddCustomerPage {
    private final WebDriverWait wait;
    WebDriver driver;
    private WebElement firstNameField;
    private WebElement lastNameField;
    private WebElement postCodeField;
    private WebElement addCustomerButton;
    HomePage homePage;

    public AddCustomerPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        this.homePage = new HomePage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement getFirstNameField() {
        if (firstNameField == null) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='First Name']")));
            firstNameField = driver.findElement(By.cssSelector("input[placeholder='First Name']"));
        }
        return firstNameField;
    }

    private WebElement getLastNameField() {
        if (lastNameField == null) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='lName']")));
            lastNameField = driver.findElement(By.xpath("//input[@ng-model='lName']"));
        }
        return lastNameField;
    }

    private WebElement getPostCodeField() {
        if (postCodeField == null) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='postCd']")));
            postCodeField = driver.findElement(By.xpath("//input[@ng-model='postCd']"));
        }
        return postCodeField;
    }

    private WebElement getAddCustomerButton() {
        if (addCustomerButton == null) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit'].btn.btn-default")));
            addCustomerButton = driver.findElement(By.cssSelector("button[type='submit'].btn.btn-default"));
        }
        return addCustomerButton;
    }

    public void enterFirstName(String firstName) {
        By firstNameFieldLocator = By.cssSelector("input[placeholder='First Name']");
        Wait.waitUntilElementIsVisible(driver, firstNameFieldLocator);

        WebElement firstNameField = driver.findElement(firstNameFieldLocator); // Повторно находим элемент после ожидания
        firstNameField.click();
        firstNameField.sendKeys(firstName);
        System.out.println("Entered First Name: " + firstName);
    }

    public void enterLastName(String lastName) {
        By lastNameFieldLocator = By.xpath("//input[@ng-model='lName']");
        Wait.waitUntilElementIsVisible(driver, lastNameFieldLocator);

        WebElement lastNameField = driver.findElement(lastNameFieldLocator); // Повторно находим элемент после ожидания
        lastNameField.click();
        lastNameField.sendKeys(lastName);
        System.out.println("Entered Last Name: " + lastName);
    }

    public void enterPostCode(String postCode) {
        By postCodeFieldLocator = By.xpath("//input[@ng-model='postCd']");
        Wait.waitUntilElementIsVisible(driver, postCodeFieldLocator);

        WebElement postCodeField = driver.findElement(postCodeFieldLocator); // Повторно находим элемент после ожидания
        postCodeField.click();
        postCodeField.sendKeys(postCode);
        System.out.println("Entered Post Code: " + postCode);
    }

    public void submit() {
        By addCustomerButtonLocator = By.cssSelector("button[ng-click='addCustomer()']");
        Wait.waitUntilElementIsVisible(driver, addCustomerButtonLocator);

        WebElement addCustomerButton = driver.findElement(addCustomerButtonLocator); // Повторно находим элемент после ожидания
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCustomerButton);
        System.out.println("Clicked 'Add Customer' button using JavascriptExecutor");
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

    public void handleAlert() {
        Wait.waitUntilAlertIsPresent(driver);
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains(Constants.CUSTOMER_ADDED_MESSAGE), "Текст в алерте не содержит ожидаемое сообщение.");
        alert.accept();
    }

    public void submitForm() {
        By addCustomerButtonLocator = By.cssSelector("button[type='submit'].btn.btn-default");
        Wait.waitUntilElementIsVisible(driver, addCustomerButtonLocator);

        WebElement addCustomerButton = driver.findElement(addCustomerButtonLocator); // Повторно находим элемент после ожидания
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCustomerButton);
        System.out.println("Clicked 'Add Customer' button using JavascriptExecutor");
    }

    public void verifyErrorMessage() {
        By firstNameFieldLocator = By.cssSelector("input[placeholder='First Name']");

        WebElement firstNameField = driver.findElement(firstNameFieldLocator);
        String validationMessage = firstNameField.getAttribute("validationMessage");

        assert validationMessage != null;
        Assert.assertTrue("Заполните это поле.".contains(validationMessage), "Сообщение об ошибке не отображается или не соответствует ожиданиям.");
    }

    public void clearInputFields() {
        homePage.clickAddCustomer();

        List<By> fieldsSelectors = Arrays.asList(
                By.cssSelector("input[placeholder='First Name']"),
                By.xpath("//input[@ng-model='lName']"),
                By.xpath("//input[@ng-model='postCd']")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (By selector : fieldsSelectors) {
            try {
                WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));

                if (!field.getAttribute("value").isEmpty()) {
                    field.clear();
                }
            } catch (StaleElementReferenceException e) {
                // Обработка ситуации, когда элемент становится "устаревшим" из-за изменения структуры DOM
                handleStaleElement(selector);
            } catch (TimeoutException e) {
                System.out.println("Элемент не найден в течение указанного времени: " + selector);
            }
        }
    }
    // Вспомогательный метод для обработки StaleElementReferenceException
    private void handleStaleElement(By selector) {
        // Повторное нахождение элемента после возникновения StaleElementReferenceException
        WebElement field = driver.findElement(selector);

        // Используем явное ожидание для повторного ожидания видимости элемента
        Wait.waitUntilElementIsVisible(driver, selector);

        // Проверяем, если в поле есть значение, очищаем его
        if (!field.getAttribute("value").isEmpty()) {
            field.clear();
        }
    }
}