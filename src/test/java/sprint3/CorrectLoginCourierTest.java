package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import sptint3.Courier;
import sptint3.CourierClient;
import sptint3.CourierCredentials;


public class CorrectLoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.createCorrect(courier);
    }

  @After
  public void tearDown(){
       courierClient.delete(courierId);
   }

    @Test
    @DisplayName("Выполнение запроса на выполнение логина курьера с корректными значениями")
    @Description("Выполнение запроса на выполнение логина курьера с корректными значениями. Корректные значения для создания и входа генерируется рандомно.")
    public void courierCanLoginWithValidCredentials() {

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
       int statusCode = loginResponse.extract().statusCode();
       courierId = loginResponse.extract().path("id");

        assertThat("Курьер выполних логин, статус код:",statusCode,equalTo(200));
        assertThat("Идентификатор курьера ID",courierId,is(not(0)));

    }

}
