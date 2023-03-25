import action.UserAction;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import objects.Login;
import objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.practikum.AccountPage;
import ru.yandex.practikum.ConstructorPage;
import ru.yandex.practikum.LoginPage;
import ru.yandex.practikum.RegisterPage;

import java.util.concurrent.TimeUnit;

import static data.UserDataTest.getUserValidRegistration;

public class AccountTest {
    User user;
    UserAction userAction;
    Login login;
    RegisterPage registerPage;
    LoginPage loginPage;
    ConstructorPage constructorPage;
    AccountPage accountPage;
    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://stellarburgers.nomoreparties.site/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        user = getUserValidRegistration();
        userAction = new UserAction();
        login = new Login(user.getEmail(), user.getPassword());
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        constructorPage = new ConstructorPage(driver);
        accountPage = new AccountPage(driver);

        userAction.create(user);
        loginPage.clickLoginAccButton();
        loginPage.setEmailField(user.getEmail());
        loginPage.setPasswordInField(user.getPassword());
        loginPage.clickLoginButton();
    }

    @After
    public void cleanOut() {
        Login loginRequest = new Login(user.getEmail(), user.getPassword());
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
    @DisplayName("Переход к Личному кабинету")
    @Description("Проверка перехода по клику на \"Личный кабинет\"")
    public void transferToAccount() {
        accountPage.clickToPersonalAccountLink();
        accountPage.isDisplayedInfoAboutAccount();

    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода из аккаунта")
    public void logOutAccount() {
        accountPage.clickToPersonalAccountLink();
        accountPage.clickToLogOutLink();
        loginPage.isDisplayLogin();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    @Description("Проверка перехода из личного кабинета в конструктор по клику на \"Конструктор\"")
    public void transferToConstructor() {
        accountPage.clickToPersonalAccountLink();
        constructorPage.clickConstructorLink();
        constructorPage.isDisplayedTextPutBurger();
    }

    @Test
    @DisplayName("Переход из личного аккаунта в конструктор по клику на лого")
    @Description("Проверка перехода из личного аккаунта в конструктор по клику на лого \"Stellar Burgers\"")
    public void transferToConstructorLogo() {
        accountPage.clickToPersonalAccountLink();
        constructorPage.clickLogoLink();
        constructorPage.isDisplayedTextPutBurger();
    }

}