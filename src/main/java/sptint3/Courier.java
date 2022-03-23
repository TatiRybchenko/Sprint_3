package sptint3;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Генерация рандомных значений (логина, пароля, имени) для создания акаунта курьера")
    public static Courier getRandom(){

        final String courierLogin = RandomStringUtils.randomAlphabetic(10);
        final String courierPassword = RandomStringUtils.randomAlphabetic(10);
        final String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Логин курьера: ", courierLogin);
        Allure.addAttachment("Пароль курьера:", courierPassword);
        Allure.addAttachment("Имя курьера", courierFirstName);

        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

}
