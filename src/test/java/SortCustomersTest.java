import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomersPage;

@Epic("Управление клиентами")
@Feature("Сортировка клиентов")
public class SortCustomersTest extends BaseTest {

    CustomersPage customersPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        super.setUp();
        homePage.clickCustomers();
        customersPage = new CustomersPage(getDriver());
    }

    @Test(description = "Проверка сортировки клиентов по имени в алфавитном порядке")
    @Description("Тест сортирует список клиентов и выводит результат сортировки.")
    @Severity(SeverityLevel.NORMAL)
    public void sortCustomersByNameTest() {
        customersPage.sortCustomersByName();
        customersPage.resultSorting();
    }
}