import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomersPage;
import java.util.List;

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
    @Description("Тест сортирует список клиентов по их именам в алфавитном порядке и проверяет результат сортировки.")
    @Severity(SeverityLevel.NORMAL)
    public void sortCustomersByNameTest() {
        sortCustomerNames();
        verifySorting();
    }

    @Step("Сортировка имен клиентов по алфавиту")
    public void sortCustomerNames() {
        customersPage.sortCustomersByName();
    }

    @Step("Проверка сортировки имен клиентов в алфавитном порядке")
    public void verifySorting() {
        List<String> customerNames = customersPage.getCustomerNames();
        String customerNamesStr = String.join(", ", customerNames);
        Allure.addAttachment("Имена клиентов после сортировки: ", customerNamesStr);
    }
}