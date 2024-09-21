import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.HomePage;
import utils.Constants;


public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected HomePage homePage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        WebDriver chromeDriver = new ChromeDriver(options);
        driver.set(chromeDriver);

        driver.get().manage().window().maximize();
        driver.get().manage().deleteAllCookies();

        driver.get().get(Constants.BASE_URL);
        homePage = new HomePage(driver.get());
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.remove();
            }
        }
    }
}