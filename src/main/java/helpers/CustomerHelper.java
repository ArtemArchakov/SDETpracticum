package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.CustomersPage;
import java.util.List;

public class CustomerHelper {
    private final WebDriver driver;
    private final CustomersPage customersPage;

    public CustomerHelper(WebDriver driver) {
        this.driver = driver;
        this.customersPage = new CustomersPage(driver);
    }

    public void deleteCustomer(String firstName) {
        List<String> customerNames = customersPage.getCustomerNames();

        if (customerNames.isEmpty()) {
            System.out.println("Не найдено клиентов для удаления.");
        } else {
            try {
                By deleteButton = By.xpath("//td[text()='" + firstName + "']/following-sibling::td/button");
                driver.findElement(deleteButton).click();
                System.out.println("Клиент с именем удален: " + firstName);
            } catch (Exception e) {
                System.err.println("Не удалось удалить клиента: " + e.getMessage());
            }
        }
    }
}