import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.practikum.ConstructorPage;

import java.util.concurrent.TimeUnit;

public class ConstructorTest {
    ConstructorPage constructorPage;
    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://stellarburgers.nomoreparties.site/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        constructorPage = new ConstructorPage(driver);
    }

    @After
    public void cleanOut() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход к булкам")
    @Description("Среди ингредиентов можно выбрать вкладку \"Булки\"")
    public void bunTab() {
        constructorPage.clickBun();
        Assert.assertEquals("Булки", constructorPage.getTextActiveTab());
    }

    @Test
    @DisplayName("Переход к соусам")
    @Description("Среди ингредиентов можно выбрать вкладку \"Соусы\"")
    public void sauceTab() {
        constructorPage.clickSauce();
        Assert.assertEquals("Соусы", constructorPage.getTextActiveTab());
    }

    @Test
    @DisplayName("Переход к начинкам")
    @Description("Среди ингредиентов можно выбрать вкладку \"Начинки\"")
    public void fillingsTab() {
        constructorPage.clickFilling();
        Assert.assertEquals("Начинки", constructorPage.getTextActiveTab());
    }

}