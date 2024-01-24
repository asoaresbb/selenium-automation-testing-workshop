package autodoc.pages


import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.WebDriverWait
import seleniumtestinglib.locators.ByText
import java.time.Duration.ofMillis
import java.time.Duration.ofSeconds
import kotlin.test.assertEquals


class HomepagePO(private val driver: RemoteWebDriver) : Layout<HomepagePO>(driver) {

    @Step
    fun navigate(): HomepagePO {
        driver.get("https://www.auto-doc.pt")
        return this
    }

    @Step
    fun `visit the blog`(): HomepagePO {
        driver.findElement(ByText("Visita o blog")).click()

        driver.switchTo().window(driver.windowHandles.elementAt(driver.windowHandles.size - 1))

        assertEquals("****", driver.currentUrl)
        return this
    }

    @Step
    fun `go to pisca green landing page`(): HomepageGreenPO {
        WebDriverWait(driver, ofSeconds(10))
            .pollingEvery(ofMillis(50))
            .until(presenceOfElementLocated(ByText("Green", exact = false)))
            .click()

        assertEquals("****", driver.currentUrl)
        return HomepageGreenPO(driver)
    }

    @Step
    fun `open car detail`(): CarDetailPO {
        driver.findElements(By.className("wrap-vehicle-card"))
            .first()
            .click()
        return CarDetailPO(driver)
    }


    @Step
    fun `filter by brand`(brand: String): HomepagePO {
        driver.findElement(cssSelector("[inputmode='search']")).click()
        driver.findElement(ByText(brand, exact = false)).click()

        return this
    }

    @Step
    fun `filter by model`(model: String): HomepagePO {
        WebDriverWait(driver, ofSeconds(10))
            .pollingEvery(ofMillis(50))
            .until(elementToBeClickable(cssSelector("[placeholder='Modelo']")))

        driver.findElement(cssSelector("[placeholder='Modelo']")).click()
        driver.findElement(ByText(model, exact = false)).click()

        return this
    }

    @Step
    fun `check results`(brand: String, model: String): AdvancedSearchPO {
        driver.findElement(xpath("//button[@class='secondary']")).click()

        val lowCaseBrand = brand.lowercase()
        val lowCaseModel = model.lowercase()

        WebDriverWait(driver, ofSeconds(8))
            .pollingEvery(ofMillis(50))
            .until(urlToBe("****/carros/marca/${lowCaseBrand}/${lowCaseModel}"))

        return AdvancedSearchPO(driver)
    }

    @Step
    fun `find by brand`(brand: String): AdvancedSearchPO {
        driver.findElement(cssSelector("[href='/carros/marca/${brand}']")).click()
        WebDriverWait(driver, ofSeconds(10))
            .pollingEvery(ofMillis(50))
            .until(urlContains("/carros/marca/${brand}"))
        return AdvancedSearchPO(driver)
    }


}
