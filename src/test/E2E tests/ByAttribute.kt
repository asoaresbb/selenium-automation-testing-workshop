import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

data class ByAttribute(private val name: String, private val value: String) : By() {
    override fun findElements(context: SearchContext): List<WebElement> =
        context.findElements(
            cssSelector("[$name='${value.replace("'", "\\''")}']")
        )
}
