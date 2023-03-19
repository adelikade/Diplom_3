import action.UserAction;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pojo.Login;
import pojo.User;
import ru.yandex.practikum.AccountPage;
import ru.yandex.practikum.LoginPage;

import java.util.concurrent.TimeUnit;

import static data.UserDataTest.getUserValidRegistration;

public class LoginTest {
    User userRequest;
    UserAction userAction;
    LoginPage loginPage;
    AccountPage personalAccountPage;
    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://stellarburgers.nomoreparties.site/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        userAction = new UserAction();
        loginPage = new LoginPage(driver);
        personalAccountPage = new AccountPage(driver);
        userRequest = getUserValidRegistration();
        userAction.create(userRequest);
    }

    @After
    public void cleanOut() {
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
    @DisplayName("Войти в аккаунт с главной страницы")
    @Description("Проверка корректного входа через кнопку \"Войти в аккаунт\" на главной странице")
    public void loginToMainPage() {
        loginPage.clickLoginAccButton();
        loginPage.setEmailField(userRequest.getEmail());
        loginPage.setPasswordInField(userRequest.getPassword());
        loginPage.clickLoginButton();
    }

    @Test
    @DisplayName("Войти в аккаунт через ссылку под формой регистрации")
    @Description("Проверка корректного входа через кнопку \"Войти\" под формой регистрации")
    public void loginToRegistrationForm() {
        loginPage.clickLoginAccButton();
        loginPage.clickRegisterLinkStart();
        loginPage.clickRegisterLinkEnd();
        loginPage.setEmailField(userRequest.getEmail());
        loginPage.setPasswordInField(userRequest.getPassword());
        loginPage.clickLoginButton();
    }

    @Test
    @DisplayName("Войти в аккаунт через личный кабинет")
    @Description("Проверка корректного входа через форму авторизации в личном кабинете")
    public void loginToPersonalAccount() {
        personalAccountPage.clickToPersonalAccountLink();
        loginPage.setEmailField(userRequest.getEmail());
        loginPage.setPasswordInField(userRequest.getPassword());
        loginPage.clickLoginButton();
    }

    @Test
    @DisplayName("Войти в аккаунт через ссылку под формой восстановления пароля")
    @Description("Проверка корректного входа через кнопку \"Войти\" под формой восстановления пароля")
    public void loginToRecoverPassword() {
        personalAccountPage.clickToPersonalAccountLink();
        loginPage.clickRecoverPassword();
        loginPage.clickRegisterLinkEnd();
        loginPage.setEmailField(userRequest.getEmail());
        loginPage.setPasswordInField(userRequest.getPassword());
        loginPage.clickLoginButton();
    }
}