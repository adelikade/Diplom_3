import action.UserAction;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pojo.Login;
import pojo.User;
import ru.yandex.practikum.LoginPage;
import ru.yandex.practikum.RegisterPage;

import java.util.concurrent.TimeUnit;

import static data.UserDataTest.getUserInvalidRegistration;
import static data.UserDataTest.getUserValidRegistration;

public class RegisterTest {
    User userRequest;
    RegisterPage registrationPage;
    LoginPage loginPage;
    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://stellarburgers.nomoreparties.site/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        registrationPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    }

    @After
    public void cleanOut() {
        UserAction userAction = new UserAction();
        Login loginRequest = new Login(userRequest.getEmail(), userRequest.getPassword());
        Response response = userAction.login(loginRequest);
        String accessToken = response
                .then()
                .extract()
                .path("accessToken");

        if (accessToken != null) {
            userAction.delete(accessToken);
        }
        driver.quit();
    }

    @Test
    @DisplayName("Корректная регистрация")
    @Description("Проверка успешной регистрации с валидным паролем")
    public void validRegistration() {
        userRequest = getUserValidRegistration();
        loginPage.clickLoginAccButton();
        loginPage.clickRegisterLinkStart();
        registrationPage.setRegisterForm(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        registrationPage.clickRegisterButton();
        registrationPage.isDisplayedLoginButton();
    }

    @Test
    @DisplayName("Некорректная регистрация")
    @Description("Нельзя зарегистрироваться с паролем меньше 6 символов")
    public void invalidRegistration() {
        userRequest = getUserInvalidRegistration();
        loginPage.clickLoginAccButton();
        loginPage.clickRegisterLinkStart();
        registrationPage.setRegisterForm(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        registrationPage.clickRegisterButton();
        Assert.assertEquals("Некорректный пароль", registrationPage.getTextPasswordError());
    }
}