package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import sptint3.CourierClient;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;



public class FailedDeleteCourierTest {

    private CourierClient courierClient;
    private final int COURIER_ID = 0;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Выполнение запроса на удаление курьера с некорректными значениями. courierId = 0")
    @Description("Выполнение запроса на удаление курьера с некорректными значениями. courierId = 0 ")
    public void courierFailedDeleteCredentialsNoCourierId() {

        ValidatableResponse deleteResponse = courierClient.delete(COURIER_ID);
        int statusCode = deleteResponse.extract().statusCode();
        String errorMessage = deleteResponse.extract().path("message");

        assertThat("Удаление курьера не выполнилось, статус код:",statusCode, equalTo(SC_NOT_FOUND));
        assertEquals("Курьера с таким id нет.", errorMessage);
    }

    @Test
    @DisplayName("Выполнение запроса на удаление курьера с некорректными значениями. Отсутствует courierId")
    @Description("Выполнение запроса на удаление курьера с некорректными значениями. Отсутствует courierId.")
    @Issue("Тест написан корректно, информация о некорректном поведение системы передана в поддержку для исправления, не выводится корреткное сообщение")
    public void courierFailedDeleteCredentialsCourierIdNull() {

        ValidatableResponse deleteResponse = courierClient.deleteFailedINull(COURIER_ID);
        int statusCode = deleteResponse.extract().statusCode();
        String errorMessage = deleteResponse.extract().path("message");

        assertThat("Удаление курьера не выполнилось, статус код:", statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для удаления курьера", errorMessage);
    }
}
