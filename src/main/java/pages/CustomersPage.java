package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CustomersPage {
    private final WebDriver driver;
    private final WebElement firstNameSortButton;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.marTop")));
        WebElement tableContainer = driver.findElement(By.cssSelector("div.marTop"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'First Name')]")));
        firstNameSortButton = driver.findElement(By.xpath("//a[contains(text(), 'First Name')]"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Last Name')]")));
        WebElement lastNameSortButton = driver.findElement(By.xpath("//a[contains(text(), 'Last Name')]"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Post Code')]")));
        WebElement postCodeSortButton = driver.findElement(By.xpath("//a[contains(text(), 'Post Code')]"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        List<WebElement> tableRows = driver.findElements(By.xpath("//tbody/tr"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Search Customer']")));
        WebElement searchCustomerField = driver.findElement(By.cssSelector("input[placeholder='Search Customer']"));
    }

    public List<String> getCustomerNames() {
        Set<String> customerNames = new HashSet<>();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement container = driver.findElement(By.cssSelector("div.marTop"));

        AtomicLong lastHeight = new AtomicLong((long) jsExecutor.executeScript("return arguments[0].scrollHeight", container));
        AtomicInteger attempts = new AtomicInteger(0);

        while (attempts.get() < 3) { // Ограничиваем цикл до 3 неудачных попыток
            List<WebElement> nameElements = driver.findElements(By.xpath("//tbody/tr/td[1]"));
            int previousSize = customerNames.size();
            customerNames.addAll(nameElements.stream().map(WebElement::getText).collect(Collectors.toSet()));

            jsExecutor.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", container);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            try {
                wait.until((ExpectedCondition<Boolean>) wd -> {
                    long newHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);
                    return newHeight > lastHeight.get() || driver.findElements(By.xpath("//tbody/tr/td[1]")).size() > previousSize;
                });

                long newHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);
                if (newHeight == lastHeight.get()) {
                    attempts.incrementAndGet(); // Увеличиваем счетчик попыток
                } else {
                    attempts.set(0); // Сбрасываем счетчик попыток при изменении высоты
                    lastHeight.set(newHeight);
                }
            } catch (TimeoutException e) {
                System.out.println("Время ожидания истекло: прокрутка не увеличила высоту.");
                break;
            }
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

    public String findCustomerNameClosestToAverage(List<String> customerNames) {
        OptionalDouble averageLength = customerNames.stream().mapToInt(String::length).average();

        return customerNames.stream()
                .min((name1, name2) -> {
                    double diff1 = Math.abs(name1.length() - averageLength.orElse(0));
                    double diff2 = Math.abs(name2.length() - averageLength.orElse(0));
                    return Double.compare(diff1, diff2);
                }).orElse(null);
    }
}