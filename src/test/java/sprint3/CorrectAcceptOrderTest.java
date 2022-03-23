package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sptint3.*;

import java.util.List;
import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;


public class CorrectAcceptOrderTest {

    private CourierClient courierClient;
    private Courier courier;
    private OrdersClient ordersClient;
    private Orders orders;
    private int courierId;
    private int trackId;
    private int orderID;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        ordersClient = new OrdersClient();
        courier = Courier.getRandom();
        courierClient.createCorrect(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        Orders orders = Orders.builder()
                .firstName("Naruto")
                .lastName("Uchiha")
                .address("Konoha, 142 apt.")
                .metroStation(valueOf(4))
                .phone("+7 800 355 35 35")
                .rentTime(5)
                .deliveryDate("2020-06-06")
                .comment("Saske, come back to Konoha")
                .color(List.of(new String[]{"BLACK"}))
                .build();
        ValidatableResponse createResponse = ordersClient.createCorrectOrders(orders);
        trackId = createResponse.extract().path("track");
        ValidatableResponse receivingOrderResponse = OrdersClient.receivingOrderByNumber(trackId);
        orderID = receivingOrderResponse.extract().jsonPath().getInt("order.id");
    }

        @After
        public void tearDown(){
            courierClient.delete(courierId);
            OrdersClient.cancelCorrectOrders(trackId);
        }
    @Test
    @DisplayName("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с корректными значениями")
    @Description("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с корректными значениями. Корректные значения для создания курьера и его логина генерируется рандомно. Данные по заказу создаются с постоянными значениями.")
    public void  acceptOrderWithValidCredentials() {

        ValidatableResponse acceptOrderResponse = OrdersClient.acceptOrderByNumber(orderID, courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        boolean ordersOk =  acceptOrderResponse.extract().path("ok");

        assertThat("Запрос на получение заказа по его номеру выполнен, статус код:", statusCode, equalTo(SC_OK));
        assertTrue("Корреткное сообщение о завершение создания", ordersOk);

    }

}
