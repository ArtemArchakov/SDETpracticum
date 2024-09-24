import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomersPage;
import java.util.List;
import java.util.OptionalDouble;

@Epic("Управление клиентами")
@Feature("Удаление клиента")
public class DeleteCustomerTest extends BaseTest {

    CustomersPage customersPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        super.setUp();
        homePage.clickCustomers();
        customersPage = new CustomersPage(getDriver());
    }

    @Test(description = "Проверка удаления клиента с именем, ближайшим к средней длине")
    @Description("Тест удаляет клиента, длина имени которого ближе всего к средней длине всех имен клиентов, и логирует длину каждого имени, а также среднеарифметическую длину.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteCustomerTest() {
        List<String> customerNames = customersPage.getCustomerNames();

        if (customerNames.isEmpty()) {
            System.out.println("Не найдено клиентов для удаления.");
            return;
        }

        customersPage.logCustomerNameLengths(customerNames);
        String closestName = findCustomerNameClosestToAverage(customerNames);
        deleteCustomer(closestName);
        Assert.assertFalse(customersPage.isCustomerPresent(closestName), "Клиент все еще существует после удаления!");
    }


    @Step("Поиск имени клиента, ближайшего к средней длине")
    public String findCustomerNameClosestToAverage(List<String> customerNames) {
        OptionalDouble averageLength = customerNames.stream().mapToInt(String::length).average();

        return customerNames.stream()
                .min((name1, name2) -> {
                    double diff1 = Math.abs(name1.length() - averageLength.orElse(0));
                    double diff2 = Math.abs(name2.length() - averageLength.orElse(0));
                    return Double.compare(diff1, diff2);
                }).orElse(null);
    }

    @Step("Удаление клиента с именем: {0}")
    public void deleteCustomer(String customerName) {
        try {
            customersPage.deleteCustomerByName(customerName);
            System.out.println("Клиент с именем удален: " + customerName);
        } catch (Exception e) {
            System.err.println("Не удалось удалить клиента: " + e.getMessage());
        }
    }
}