import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomersPage;
import java.util.List;

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
        String closestName = customersPage.findCustomerNameClosestToAverage(customerNames);
        customersPage.deleteCustomer(closestName);
        Assert.assertFalse(customersPage.isCustomerPresent(closestName), "Клиент все еще существует после удаления!");
    }
}