package ru.yandex.practikum;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {

    private final WebDriver driver;
    private final By personalAccountLink = By.xpath("//p[text()='Личный Кабинет']");
    private final By logOutLink = By.xpath("//button[text()='Выход']");
    private final By textAboutAccount = By.xpath("//p[text()='В этом разделе вы можете изменить свои персональные данные']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Кликнуть по кнопке \"Личный кабинет\"")
    public void clickToPersonalAccountLink() {
        driver.findElement(personalAccountLink).click();
    }

    @Step("Отображается информация о личном кабинете")
    public void isDisplayedInfoAboutAccount() {
        Assert.assertTrue(driver.findElement(textAboutAccount).isDisplayed());
    }

    @Step("Кликнуть по кнопке \"Выход\"")
    public void clickToLogOutLink() {
        driver.findElement(logOutLink).click();
    }
}