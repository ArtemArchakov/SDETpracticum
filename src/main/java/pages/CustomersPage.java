package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomersPage {
    WebDriver driver;
    WebElement firstNameSortButton;

    public CustomersPage(WebDriver driver) throws InterruptedException {
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

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) jsExecutor.executeScript("return arguments[0].scrollHeight", container);
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }

        return customerNames.stream().collect(Collectors.toList());
    }

    public void sortCustomersByName() {
        firstNameSortButton.click();
    }

    public void deleteCustomerByName(String name) {
        By deleteButton = By.xpath("//td[text()='" + name + "']/following-sibling::td/button");
        driver.findElement(deleteButton).click();
    }
}