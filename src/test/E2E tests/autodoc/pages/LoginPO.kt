
package autodoc.pages

import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.WebDriverWait
import seleniumtestinglib.locators.ByPlaceholderText
import seleniumtestinglib.locators.ByText
import java.time.Duration.ofMillis
import java.time.Duration.ofSeconds
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class LoginPO(private val driver: RemoteWebDriver) : Layout<LoginPO>(driver) {

    init {
        WebDriverWait(driver, ofSeconds(5))
            .until(urlContains("/public/auth/0/1"))
    }

    @Step
    fun `select register as particular`(): LoginPO {
        driver.findElements(cssSelector(".mat-tab-label-content"))
            .first { it.text == "Registo Particular" }
            .click()
        return this
    }

    @Step
    fun `fill email`(address: String): LoginPO {
        driver.findElement(cssSelector("input[placeholder='Escreve o email']"))
            .sendKeys(address)
        return this
    }

    @Step
    fun `confirm email`(): LoginPO {
        driver.findElements(cssSelector("cbmp-button"))
            .first { it.text.contains("Continuar", ignoreCase = true) }
            .click()
        return this
    }

    @Step
    fun `insert password`(password: String): LoginPO {
        driver.findElement(cssSelector("input[placeholder='Insere a palavra-passe']"))
            .sendKeys(password)
        return this
    }

    @Step
    fun `repeat password`(password: String): LoginPO {
        driver.findElement(cssSelector("input[placeholder='Repetir palavra-passe']"))
            .sendKeys(password)
        return this
    }

    @Step
    fun `confirm password`(): LoginPO {
        driver.executeScript("document.querySelector('cbmp-button button').click()")
        return this
    }

    @Step
    fun `fill first name`(name: String): LoginPO {
        driver.findElement(By.id("first_name")).sendKeys(name)
        return this
    }

    @Step
    fun `fill last name`(name: String): LoginPO {
        driver.findElement(By.id("last_name")).sendKeys(name)
        return this
    }

    @Step
    fun `fill phone number`(number: String): LoginPO {
        driver.findElement(By.id("phone_number")).sendKeys(number)
        return this
    }

    @Step
    fun `accept terms and conditions`(): LoginPO {
        driver.executeScript("window.scrollBy(0,700)")
        driver.executeScript("document.getElementById('terms').click()")
        return this
    }

    @Step
    fun `accept privacy policy`(): LoginPO {
        driver.executeScript("document.getElementById('privacy').click()")
        return this
    }

    @Step
    fun `finalize registration`(): LoginPO {
        driver.executeScript("document.querySelectorAll('cbmp-button button')[0].click()")
        assertNotNull(driver.findElements(cssSelector(".bold-24"))
            .firstOrNull { it.text.contains("Informação") || it.text.contains("Alerta") }, "Modal dialog not found"
        )
        return this
    }

    @Step
    fun `login with`(user: String, password: String): LoginPO {

        WebDriverWait(driver, ofSeconds(5))
            .until(textToBePresentInElement(driver.findElement(By.id("show-login")), "ACEDE COM EMAIL OU TELEMÓVEL"))

        driver.findElement(ByText("Acede com Email ou Telemóvel", exact = false)).click()

        driver.findElement(ByPlaceholderText("Escreve o teu email ou telemóvel", exact = false))
            .sendKeys(user)
        driver.findElement(ByPlaceholderText("Escreve a tua palavra passe", exact = false))
            .sendKeys(password)
        WebDriverWait(driver, ofSeconds(5))
            .until(elementToBeClickable(cssSelector(".mx-0")))
            .click()
        return this
    }

    @Step
    fun `open profile`(): ProfilePO {
        WebDriverWait(driver, ofSeconds(5))
            .withMessage("Credentials are wrong")
            .until(elementToBeClickable(ByText("Os meus anúncios")))

        driver.findElement(cssSelector("[alt='perfil']")).click()
        driver.findElement(ByText("Dados do Utilizador"))
            .click()
        return ProfilePO(driver)
    }

    @Step
    fun `close helper`(): LoginPO {
        WebDriverWait(driver, ofSeconds(10))
            .until(elementToBeClickable(cssSelector(".icon-close"))).click()
        return this
    }

    @Step
    fun `go to messages`(): MessagesPO {
        WebDriverWait(driver, ofSeconds(8))
            .until(elementToBeClickable(cssSelector(".icon-chat")))
            .click()
        return MessagesPO(driver)
    }

    @Step
    fun `go to my cars`(): MyCarsPO {
        WebDriverWait(driver, ofSeconds(8))
            .pollingEvery(ofMillis(5))
            .until(elementToBeClickable(ByText("Os meus", exact = false)))
            .click()
        return MyCarsPO(driver)
    }

    @Step
    fun `go to advanced search`(): AdvancedSearchPO {
        waitForAjaxLoaderDone()
        driver.findElement(cssSelector("[text='Pesquisa Avançada']"))
            .click()
        return AdvancedSearchPO(driver)
    }

    @Step
    fun `logout user`(): HomepagePO {
        driver.findElement(cssSelector("[alt='perfil']")).click()
        driver.findElement(xpath("//a[contains(.,'Terminar sessão')]")).click()

        assertEquals("Login", driver.findElement(ByText("Login", exact = false)).text)
        return HomepagePO(driver)
    }
}
