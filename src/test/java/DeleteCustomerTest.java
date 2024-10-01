import helpers.CustomerHelper;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomersPage;
import java.util.List;

@Epic("Управление клиентами")
@Feature("Удаление клиента")
public class DeleteCustomerTest extends BaseTest {

    private CustomersPage customersPage;
    private CustomerHelper customerHelper;
    private String closestName;

    @BeforeClass
    public void setUp() throws InterruptedException {
        super.setUp();
        homePage.clickCustomers();
        customersPage = new CustomersPage(getDriver());
        customerHelper = new CustomerHelper(getDriver());

        List<String> customerNames = customersPage.getCustomerNames();
        if (customerNames.isEmpty()) {
            System.out.println("Не найдено клиентов для удаления.");
            throw new IllegalStateException("Не найдено клиентов для удаления.");
        }

        customersPage.logCustomerNameLengths(customerNames);
        closestName = customersPage.findCustomerNameClosestToAverage(customerNames);
        System.out.println("Имя клиента, которое будет удалено: " + closestName);
    }

    @Test(description = "Проверка удаления клиента с именем, ближайшим к средней длине")
    @Description("Тест удаляет клиента, длина имени которого ближе всего к средней длине всех имен клиентов.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteCustomerTest() {
        customerHelper.deleteCustomer(closestName);
        Assert.assertFalse(customersPage.isCustomerPresent(closestName), "Клиент все еще существует после удаления!");
    }
}