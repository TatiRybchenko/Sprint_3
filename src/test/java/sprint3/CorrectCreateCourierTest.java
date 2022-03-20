package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import sptint3.Courier;
import sptint3.CourierClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CorrectCreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @DisplayName("Создание курьера с валидными значениями в параметрах: имя, логин, пароль.")
    @Description("Создание курьера с валидными значениями (имя, логин, пароль). Корректные значения генерируются рандомно.")
    public void courierCanCreateWithValidCredentials() {

        ValidatableResponse createResponse = courierClient.createCorrect(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean courierOk = createResponse.extract().path("ok");

        assertThat("Создание курьера выполнилось без ошибок, статус код:",statusCode,equalTo(201));
        assertTrue("Корреткное сообщениео завершение создания",courierOk);
    }

    @Test
    @DisplayName("Создание курьера с валидными значениями, у которого отсутствует один из передаваемых параметров для создания: имя")
    @Description("Создание курьера с валидными значениями (логин, пароль). Корректные значения генерируются рандомно.")
    public void courierCanCreateWithValidCredentialsNoFirstName() {

        ValidatableResponse createResponse = courierClient.createFailedNoFirstName(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean courierOk = createResponse.extract().path("ok");

        assertThat("Создание курьера выполнилось без ошибок, статус код:",statusCode,equalTo(201));
        assertTrue("Корреткное сообщениео завершение создания",courierOk);
    }

}
