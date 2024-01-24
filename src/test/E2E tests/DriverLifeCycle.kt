import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.allure.Allure
import org.junit.jupiter.api.extension.*
import org.openqa.selenium.OutputType
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.ByteArrayInputStream
import java.time.Duration.ofSeconds

class DriverLifeCycle :
    ParameterResolver,
    BeforeAllCallback,
    AfterEachCallback,
    AfterAllCallback,
    TestWatcher {
    init {
        WebDriverManager.chromedriver().setup()
    }

    private val driver = ChromeDriver(
        ChromeOptions()
            .addArguments("--disable-blink-features")
            .addArguments("--disable-blink-features=AutomationControlled")
            .addArguments("--disable-dev-shm-usage")
            .addArguments("--no-sandbox")
            .setExperimentalOption("excludeSwitches", arrayOf("enable-automation"))

    ).also {
        System.setProperty("webdriver.chrome.whitelistedIps", "")
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext) =
        parameterContext.parameter.type == RemoteWebDriver::class.java

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext) =
        driver

    override fun testFailed(context: ExtensionContext, throwable: Throwable) {
        Allure.addAttachment("screenshot on fail", ByteArrayInputStream(driver.getScreenshotAs(OutputType.BYTES)))
        Allure.link(driver.title ?: "Current page", driver.currentUrl)
        Allure.description(throwable.message)
    }

    override fun beforeAll(context: ExtensionContext) {
        driver.manage().apply {
            window().maximize()
            timeouts().implicitlyWait(ofSeconds(5))
        }
    }

    override fun afterEach(context: ExtensionContext) {
        driver.switchTo().window(driver.windowHandles.elementAt(0))
    }

    override fun afterAll(context: ExtensionContext) {
        driver.quit()
    }
}
