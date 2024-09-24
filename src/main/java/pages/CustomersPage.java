package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class CustomersPage {
    WebDriver driver;
    WebElement firstNameSortButton;
    private long finalLastHeight;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        firstNameSortButton = driver.findElement(By.xpath("//a[contains(text(), 'First Name')]"));
    }

    public List<String> getCustomerNames() {
        Set<String> customerNames = new HashSet<>();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;


        WebElement container = driver.findElement(By.cssSelector("div.marTop"));

        long lastHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);

        while (true) {
            List<WebElement> nameElements = driver.findElements(By.xpath("//tbody/tr/td[1]"));
            customerNames.addAll(nameElements.stream().map(WebElement::getText).collect(Collectors.toSet()));

            jsExecutor.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", container);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until((ExpectedCondition<Boolean>) wd -> {
                long newHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);
                return newHeight > finalLastHeight || driver.findElements(By.xpath("//tbody/tr/td[1]")).size() > customerNames.size();
            });

            long newHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }

        return new ArrayList<>(customerNames);
    }

    public void sortCustomersByName() {
        firstNameSortButton.click();
    }

    public void resultSorting() {
        List<String> customerNames = getCustomerNames();
        String customerNamesStr = String.join(", ", customerNames);
        Allure.addAttachment("Имена клиентов после сортировки: ", customerNamesStr);
    }

    public void deleteCustomerByName(String name) {
        By deleteButton = By.xpath("//td[text()='" + name + "']/following-sibling::td/button");
        driver.findElement(deleteButton).click();
    }

    public boolean isCustomerPresent(String customerName) {
        try {
            driver.findElement(By.xpath("//td[text()='" + customerName + "']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void logCustomerNameLengths(List<String> customerNames) {
        System.out.println("Длины имен клиентов:");
        customerNames.forEach(name -> System.out.println("Имя: " + name + ", Длина: " + name.length()));

        OptionalDouble averageLength = customerNames.stream()
                .mapToInt(String::length)
                .average();

        if (averageLength.isPresent()) {
            System.out.println("Средняя длина имен клиентов: " + averageLength.getAsDouble());
            Allure.addAttachment("Средняя длина имен клиентов", String.valueOf(averageLength.getAsDouble()));
        } else {
            System.out.println("Не удалось вычислить среднюю длину имен клиентов.");
        }
    }
}