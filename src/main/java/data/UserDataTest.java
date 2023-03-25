package data;

import io.qameta.allure.Step;
import objects.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserDataTest {
    @Step("Регистрация пользователя с корректным паролем")
    public static User getUserValidRegistration() {
        String name = "Ольга";
        String email = "adelikade@gmail.com";
        String password = "lalala111118";
        return new User(name, email, password);
    }

    @Step("Регистрация клиента с некорректным паролем")
    public static User getUserInvalidRegistration() {
        String name = RandomStringUtils.randomAlphabetic(9);
        String email = RandomStringUtils.randomAlphabetic(9) + "@gmail.com";
        String password = RandomStringUtils.randomAlphabetic(5);
        return new User(name, email, password);
    }
}