import io.qameta.allure.*;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AddCustomerPage;
import utils.Constants;
import utils.TestDataGenerator;
import utils.Wait;

@Epic("Управление клиентами")
@Feature("Добавление клиента")
public class AddCustomerTest extends BaseTest {

    AddCustomerPage addCustomerPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        super.setUp();
        homePage.clickAddCustomer();
        addCustomerPage = new AddCustomerPage(getDriver());
    }

    @DataProvider(name = "customerData")
    public Object[][] customerData() {
        String postCode = TestDataGenerator.generatePostCode();
        String firstName = TestDataGenerator.generateFirstName(postCode);
        return new Object[][]{
                {firstName, "TestLastName", postCode}
        };
    }

    @Test(dataProvider = "customerData", description = "Проверка успешного добавления нового клиента")
    @Description("Тест проверяет возможность добавления нового клиента и подтверждает успешное добавление через сообщение в алерте.")
    @Severity(SeverityLevel.CRITICAL)
    public void addCustomerTest(String firstName, String lastName, String postCode) throws InterruptedException {
        enterCustomerDetails(firstName, lastName, postCode);
        submitForm();
        handleAlert();
    }

    @Step("Ввод данных клиента: Имя = {0}, Фамилия = {1}, Почтовый код = {2}")
    public void enterCustomerDetails(String firstName, String lastName, String postCode) {
        Wait.waitUntilVisible(getDriver(), addCustomerPage.getFirstNameField());
        addCustomerPage.enterFirstName(firstName);

        Wait.waitUntilVisible(getDriver(), addCustomerPage.getLastNameField());
        addCustomerPage.enterLastName(lastName);

        Wait.waitUntilVisible(getDriver(), addCustomerPage.getPostCodeField());
        addCustomerPage.enterPostCode(postCode);

        Allure.addAttachment("Введенные данные клиента",
                "Имя: " + firstName + "\n" +
                        "Фамилия: " + lastName + "\n" +
                        "Почтовый код: " + postCode);
    }

    @Step("Отправка формы для добавления клиента")
    public void submitForm() {
        Wait.waitUntilClickable(getDriver(), addCustomerPage.getSubmitButton());
        addCustomerPage.submit();
    }

    @Step("Обработка и проверка сообщения в алерте")
    public void handleAlert() throws InterruptedException {
        Wait.waitUntilAlertIsPresent(getDriver());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains(Constants.CUSTOMER_ADDED_MESSAGE), "Текст в алерте не содержит ожидаемое сообщение.");
        alert.accept();
    }
}