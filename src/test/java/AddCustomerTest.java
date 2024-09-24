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
    @BeforeMethod
    public void clearInputFields() {
        addCustomerPage.clearFirstName();
        addCustomerPage.clearLastName();
        addCustomerPage.clearPostCode();
    }

    @Test(description = "Негативный тест: попытка добавить клиента с полностью пустыми полями")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если все поля пустые.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithEmptyFieldsTest() throws InterruptedException {
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'First Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'First Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyFirstNameTest() throws InterruptedException {
        addCustomerPage.enterFirstName("TestFirstName");
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'Last Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'Last Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyLastNameTest() throws InterruptedException {
        addCustomerPage.enterLastName("TestLastName");
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив только поле 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнено только поле 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithOnlyPostCodeTest() throws InterruptedException {
         addCustomerPage.enterPostCode("12345");
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'First Name' и 'Last Name'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'First Name' и 'Last Name'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithFirstNameAndLastNameTest() throws InterruptedException {
        addCustomerPage.enterFirstName("TestFirstName");
        addCustomerPage.enterLastName("TestLastName");
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'First Name' и 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'First Name' и 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithFirstNameAndPostCodeTest() throws InterruptedException {
        addCustomerPage.enterFirstName("TestFirstName");
        addCustomerPage.enterPostCode("12345");
        submitForm();
        verifyErrorMessage();
    }

    @Test(description = "Негативный тест: попытка добавить клиента, заполнив поля 'Last Name' и 'Post Code'")
    @Description("Тест проверяет, что форма не позволяет добавить клиента, если заполнены только поля 'Last Name' и 'Post Code'.")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithLastNameAndPostCodeTest() throws InterruptedException {
        addCustomerPage.enterLastName("TestLastName");
        addCustomerPage.enterPostCode("12345");
        submitForm();
        verifyErrorMessage();
    }

    @Step("Проверка отображения сообщения об ошибке при неправильном заполнении формы")
    public void verifyErrorMessage() {
        String validationMessage = addCustomerPage.getFirstNameField().getAttribute("validationMessage");
        assert validationMessage != null;
        Assert.assertTrue("Заполните это поле.".contains(validationMessage), "Сообщение об ошибке не отображается или не соответствует ожиданиям.");
    }

    @Test(description = "Проверка успешного добавления нового клиента")
    @Description("Тест проверяет возможность добавления нового клиента и подтверждает успешное добавление через сообщение в алерте.")
    @Severity(SeverityLevel.CRITICAL)
    public void addCustomerTest() throws InterruptedException {
        String postCode = TestDataGenerator.generatePostCode();
        String firstName = TestDataGenerator.generateFirstName(postCode);
        String lastName = TestDataGenerator.fixLastName();

        addCustomerPage.enterCustomerDetails(firstName,lastName,postCode);
        submitForm();
        handleAlert();
    }


    @Step("Отправка формы для добавления клиента")
    public void submitForm() {
        Wait.waitUntilClickable(getDriver(), addCustomerPage.getSubmitButton());
        addCustomerPage.submit();
    }

    @Step("Обработка и проверка сообщения в алерте")
    public void handleAlert() {
        Wait.waitUntilAlertIsPresent(getDriver());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains(Constants.CUSTOMER_ADDED_MESSAGE), "Текст в алерте не содержит ожидаемое сообщение.");
        alert.accept();
    }
}