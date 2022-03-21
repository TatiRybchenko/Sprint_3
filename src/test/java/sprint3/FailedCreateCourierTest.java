package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import sptint3.Courier;
import sptint3.CourierClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;


public class FailedCreateCourierTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
            }

     @Test
     @DisplayName("Создание курьера, у которого один из параметров, уже используется в системе: логин = null")
    @Description("Необходимо описание теста")
    public void courierFailedCredentialsNoLogin() {

        Courier courier = Courier.builder()
             .password(RandomStringUtils.randomAlphabetic(10))
             .firstName(RandomStringUtils.randomAlphabetic(10))
             .build();

      ValidatableResponse createResponse = courierClient.createCorrect(courier);
      int statusCode = createResponse.extract().statusCode();
      String errorMessage = createResponse.extract().path("message");

      assertThat("Создание курьера не выполнилось, статус код:",statusCode,equalTo(SC_CONFLICT));
      assertEquals("Этот логин уже используется. Попробуйте другой.",errorMessage);
      }

    @Test
    @DisplayName("Создание курьера, у которого отсутствует один из передаваемых параметров для создания: логин")
    @Description("Необходимо описание что делает тест")
    public void courierFailedCredentialsNoAccountsLogin() {
        Courier courier = Courier.getRandom();

        ValidatableResponse createResponse = courierClient.createFailedNoLogin(courier);
        int statusCode = createResponse.extract().statusCode();
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание курьера не выполнилось, статус код:",statusCode,equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для создания учетной записи",errorMessage);
    }

    @Test
    @DisplayName("Создание курьера, у которого отсутствует один из передаваемых параметров для создания: пароль")
    @Description("Необходимо описание что делает тест")
    public void courierFailedCredentialsNoAccountsPassword() {
        Courier courier = Courier.getRandom();

        ValidatableResponse createResponse = courierClient.createFailedNoPassword(courier);
        int statusCode = createResponse.extract().statusCode();
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание курьера не выполнилось, статус код:",statusCode,equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для создания учетной записи",errorMessage);
    }
}


