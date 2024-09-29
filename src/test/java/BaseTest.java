import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.HomePage;
import utils.Constants;

public class BaseTest {
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected HomePage homePage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--no-sandbox")
                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        driver.set(new ChromeDriver(options));

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

        getDriver().get(Constants.BASE_URL);
        homePage = new HomePage(getDriver());
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.remove();
            }
        }
    }
}