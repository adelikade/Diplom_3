package ru.yandex.practikum;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    public final By textPutBurger = By.xpath("//h1[text()='Соберите бургер']");
    private final WebDriver driver;
    private final By loginAccButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By emailField = By.xpath("//input[@name='name']");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By loginRegistration = By.xpath("//a[text()='Войти']");
    private final By recoverPassword = By.xpath("//a[text()='Восстановить пароль']");
    private final By loginLink = By.xpath("//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Кликнуть по кнопке \"Войти в аккаунт\"")
    public void clickLoginAccButton() {
        driver.findElement(loginAccButton).click();
    }

    @Step("Заполнить пароль")
    public void setPasswordInField(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Заполнить емейл")
    public void setEmailField(String name) {
        driver.findElement(emailField).sendKeys(name);
    }

    @Step("Кликнуть на кнопку \"Войти\"")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Кликнуть на ссылку\"Зарегистрироваться\"(до начала регистрации)")
    public void clickRegisterLinkStart() {
        driver.findElement(registerLink).click();
    }

    @Step("Кликнуть на ссылку\"Войти\"(после окончания регистрации)")
    public void clickRegisterLinkEnd() {
        driver.findElement(loginRegistration).click();
    }

    @Step("Кликнуть на ссылку для восстановления пароля")
    public void clickRecoverPassword() {
        driver.findElement(recoverPassword).click();
    }

    @Step("Отображается кнопка для входа")
    public void isDisplayLogin() {
        Assert.assertTrue(driver.findElement(loginLink).isDisplayed());
    }

    @Step("Отображается текст \"Соберите бургер\"")
    public void isDisplayedTextPutBurger() {
        Assert.assertTrue(driver.findElement(textPutBurger).isDisplayed());
    }
}