import helpers.CustomerHelper;
import io.qameta.allure.*;
import org.testng.annotations.*;
import pages.AddCustomerPage;
import static utils.TestDataGenerator.*;

@Epic("Управление клиентами")
@Feature("Добавление клиента")
public class AddCustomerTest extends BaseTest {

    private AddCustomerPage addCustomerPage;
    private CustomerHelper customerHelper;

    @BeforeClass
    public void setUp() throws InterruptedException {
        super.setUp();
        homePage.clickAddCustomer();
        addCustomerPage = new AddCustomerPage(getDriver());
        homePage.clickCustomers();
        customerHelper = new CustomerHelper(getDriver());
    }

    @BeforeMethod
    public void clearFields() {
        addCustomerPage.clearInputFields();
    }

    @Test(description = "Негативный тест: попытка добавить клиента с полностью пустыми полями")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если все поля пустые.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithEmptyFieldsTest() {
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'First Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'First Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyFirstNameTest() {
        addCustomerPage.enterFirstName("TestFirstName");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'Last Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'Last Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyLastNameTest() {
        addCustomerPage.enterLastName("TestLastName");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyPostCodeTest() {
        addCustomerPage.enterPostCode("12345");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'First Name' и 'Last Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'First Name' и 'Last Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithFirstNameAndLastNameTest() {
        addCustomerPage.enterFirstName("TestFirstName");
        addCustomerPage.enterLastName("TestLastName");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'First Name' и 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'First Name' и 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithFirstNameAndPostCodeTest() {
        addCustomerPage.enterFirstName("TestFirstName");
        addCustomerPage.enterPostCode("12345");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'Last Name' и 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'Last Name' и 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithLastNameAndPostCodeTest() {
        addCustomerPage.enterLastName("TestLastName");
        addCustomerPage.enterPostCode("12345");
        addCustomerPage.submitForm();
        addCustomerPage.verifyErrorMessage();
    }

    @Test(description = "Проверка успешного добавления нового клиента")
    @Description("Тест проверяет возможность добавления нового клиента и подтверждает успешное добавление через сообщение в алерте.")
    @Severity(SeverityLevel.CRITICAL)
    public void addCustomerTest() {
        addCustomerPage.addCustomerWithData();
        customerHelper.deleteCustomer(getGeneratedFirstName());
    }
}