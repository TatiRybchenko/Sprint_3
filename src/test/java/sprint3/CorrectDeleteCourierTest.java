package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;


import sptint3.Courier;
import sptint3.CourierClient;
import sptint3.CourierCredentials;

public class CorrectDeleteCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.createCorrect(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Выполнение запроса на удаление курьера с корректными значениями")
    @Description("Выполнение запроса на удаление курьера с корректными значениями. Данные по созданию и логину курьера генерируются рандомно.")
    public void courierCanDeleteWithValidCredentials() {

        ValidatableResponse deleteResponse = courierClient.delete(courierId);
        int statusCode = deleteResponse.extract().statusCode();
        boolean courierOk = deleteResponse.extract().path("ok");

        assertThat("Удаление курьера выполнилось без ошибок, статус код:",statusCode,equalTo(200));
        assertTrue("Корреткное сообщениео завершение создания",courierOk);

    }
}
