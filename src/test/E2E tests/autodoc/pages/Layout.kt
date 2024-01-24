package autodoc.pages


import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration.ofMillis
import java.time.Duration.ofSeconds

abstract class Layout<T : Layout<T>>(private val driver: RemoteWebDriver) {

    @Step
    fun `accept cookies`(): T {
        driver.findElements(By.id("didomi-notice-agree-button")).firstOrNull()?.click()

        @Suppress("UNCHECKED_CAST")
        return this as T
    }

    @Step
    fun `go to login`(): LoginPO {
        WebDriverWait(driver, ofSeconds(10))
            .pollingEvery(ofMillis(50))
            .until(elementToBeClickable(cssSelector(".btn-login")))
            .click()
        return LoginPO(driver)
    }

    protected fun waitForAjaxLoaderDone() {
        WebDriverWait(driver, ofSeconds(10))
            .pollingEvery(ofMillis(50))
            .until(numberOfElementsToBe(cssSelector("cbmp-load-spinner .loader-bg"), 0))
    }
}
