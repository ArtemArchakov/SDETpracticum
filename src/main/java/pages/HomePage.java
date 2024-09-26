package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Wait;


public class HomePage {
    private WebDriver driver;
    private WebElement addCustomerButton;
    private WebElement customersButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        Wait.waitUntilVisible(driver, By.xpath("//button[contains(text(), 'Customers')]"), 20);
        customersButton = driver.findElement(By.xpath("//button[contains(text(), 'Customers')]"));
        addCustomerButton = driver.findElement(By.xpath("//button[contains(text(), 'Add Customer')]"));
        driver.findElement(By.xpath("//button[contains(text(), 'Home')]"));
        driver.findElement(By.xpath("//button[contains(text(), 'Open Account')]"));
        driver.findElement(By.xpath("//strong[contains(text(), 'XYZ Bank')]"));
    }

    public void clickAddCustomer() {
        try {
            Wait.waitUntilClickable(driver, addCustomerButton);

            if (addCustomerButton.isDisplayed()) {
                addCustomerButton.click();
            } else {
                System.err.println("Кнопка 'Add Customer' не отображается на странице.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("Ошибка: кнопка 'Add Customer' не найдена.");
        } catch (Exception e) {
            System.err.println("Ошибка при попытке кликнуть на кнопку 'Add Customer': " + e.getMessage());
        }
    }

    public void clickCustomers() {
        try {
            Wait.waitUntilClickable(driver, customersButton);

            if (customersButton.isDisplayed()) {
                customersButton.click();
            } else {
                System.err.println("Кнопка 'Customers' не отображается на странице.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("Ошибка: кнопка 'Customers' не найдена.");
        } catch (Exception e) {
            System.err.println("Ошибка при попытке кликнуть на кнопку 'Customers': " + e.getMessage());
        }
    }
}