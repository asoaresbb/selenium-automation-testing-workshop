package autodoc

import DriverLifeCycle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.remote.RemoteWebDriver
import autodoc.pages.HomepagePO

@ExtendWith(DriverLifeCycle::class)
class HomepageTest(private val driver: RemoteWebDriver) {

    @Test
    fun `visit green`() {
        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`go to pisca green landing page`()
    }

    @Test
    fun `visit blog`() {
        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`visit the blog`()
    }

    @Test
    fun `back to homepage`() {
        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`open car detail`()
            .`return to homepage`()
    }

    @Test
    fun `login and logout`() {
        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`go to login`()
            .`login with`("****", "****")
            .`logout user`()
    }

    @Test
    fun `homepage filters`() {
        val brand = "BMW"
        val model = "i8"

        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`filter by brand`(brand)
            .`filter by model`(model)
            .`check results`(brand, model)
    }

    @Test
    fun `interact with brand filter footer`() {
        HomepagePO(driver).navigate()
            .`accept cookies`()
            .`find by brand`("seat")
    }

}
