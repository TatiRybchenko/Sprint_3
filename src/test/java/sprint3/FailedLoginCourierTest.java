package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import sptint3.Courier;
import sptint3.CourierClient;
import sptint3.CourierCredentials;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;


public class FailedLoginCourierTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
            }

    @Test
    @DisplayName("Выполнение логина курьера с некорректными значениями, нет логина")
    @Description("Выполнение логина курьера с некорректными значениями. Отсутствует параметр для входа: логин")
    public void courierFailedLoginCredentialsNoLogin() {

        Courier courier = Courier.builder()
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().path("message");

        assertThat("Логин курьера не выполнился, статус код:", statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для входа", errorMessage);
    }

    @Test
    @DisplayName("Выполнение логина курьера с некорректными значениями, нет пароля")
    @Description("Выполнение логина курьера с некорректными значениями. Отсутствует параметр для входа: пароль")
    @Issue("Тест написан корректно, информация о некорректном поведение системы передана в поддержку для исправления/ Status code 504")
    public void courierFailedLoginCredentialsNoPassword() {

        Courier courier = Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(10))
                .build();

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().path("message");

        assertThat("Логин курьера не выполнился, статус код:", statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для входа", errorMessage);

    }

    @Test
    @DisplayName("Выполнение логина курьера с некорректными значениями, нет пароля и логина.")
    @Description("Выполнение логина курьера с некорректными значениями. Несуществующая параметры: пароль и логин")
    public void courierFailedLoginCredentialsNoPasswordNoLogin() {

        Courier courier = Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        String errorMessage = loginResponse.extract().path("message");

        assertThat("Логин курьера не выполнился, статус код:", statusCode, equalTo(SC_NOT_FOUND));
        assertEquals("Учетная запись не найдена", errorMessage);

    }
}
