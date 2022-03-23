package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
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
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;
;

public class FailedAcceptOrderTest {

    private CourierClient courierClient;
    private Courier courier;
    private OrdersClient ordersClient;
    private Orders orders;
    private int courierId;
    private int trackId;
    private int orderId;
    private final int ORDER_ID = 0;
    private final int COURIER_ID = 0;

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
        orderId = receivingOrderResponse.extract().jsonPath().getInt("order.id");
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
        OrdersClient.cancelCorrectOrders(trackId);
    }

    @Test
    @DisplayName("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями.")
    @Description("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями. Запрос выполняется без номера заказа. Корректные значения для создания курьера и его логина генерируется рандомно. Данные по заказу создаются с постоянными значениями.")
    @Issue("Тест написан корректно, информация о некорректном поведение системы передана в поддержку для исправления, не выводится корреткное сообщение")
    public void  acceptFailedOrderWithCredentialsNoIdOrders() {

        ValidatableResponse acceptOrderResponse = OrdersClient.acceptOrderNoOrderId(courierId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String errorMessage = acceptOrderResponse.extract().path("message");

        assertThat("Запрос на принятие закза, без ID заказа , статус код:", statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для поиска", errorMessage);
    }

    @Test
    @DisplayName("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями.")
    @Description("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями. Запрос выполняется без номера курьера. Корректные значения для создания курьера и его логина генерируется рандомно. Данные по заказу создаются с постоянными значениями.")
    public void  acceptFailedOrderWithCredentialsNoIdCourier() {

        ValidatableResponse acceptOrderResponse = OrdersClient.acceptOrderNoCourierId(orderId);
        int statusCode = acceptOrderResponse.extract().statusCode();
        String errorMessage = acceptOrderResponse.extract().path("message");

        assertThat("Запрос на принятие закза, без ID курьера , статус код:", statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals("Недостаточно данных для поиска", errorMessage);
    }


        @Test
        @DisplayName("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями.")
        @Description("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями. Запрос выполняется с несуществующим номером заказа. Корректные значения для создания курьера и его логина генерируется рандомно. Данные по заказу создаются с постоянными значениями.")

        public void  acceptFailedOrderWithCredentialsNonExistentIdOrders() {

            ValidatableResponse acceptNonExistentIdOrdersResponse = OrdersClient.acceptOrderByNumber(ORDER_ID, courierId);
            int statusCode = acceptNonExistentIdOrdersResponse.extract().statusCode();
            String errorMessage = acceptNonExistentIdOrdersResponse.extract().path("message");

            assertThat("Запрос на принятие заказа, с несуществующим номером заказа,  статус код:", statusCode, equalTo(SC_NOT_FOUND));
            assertEquals("Заказа с таким id не существует", errorMessage);
    }


    @Test
    @DisplayName("Выполнение запроса , с некорректными значениями.")
    @Description("Выполнение запроса на принятие заказа по ID заказа и ID курьера, с некорректными значениями. Запрос выполняется с несуществующим номером курьера. Корректные значения для создания курьера и его логина генерируется рандомно. Данные по заказу создаются с постоянными значениями.")
    public void  acceptFailedOrderWithCredentialsNonExistentIdCourier() {

        ValidatableResponse acceptNonExistentIdOrdersResponse = OrdersClient.acceptOrderByNumber(orderId, COURIER_ID);
        int statusCode = acceptNonExistentIdOrdersResponse.extract().statusCode();
        String errorMessage = acceptNonExistentIdOrdersResponse.extract().path("message");

        assertThat("Запрос на принятие заказа, с несуществующим номером курьера , статус код:", statusCode, equalTo(SC_NOT_FOUND));
        assertEquals("Курьера с таким id не существует", errorMessage);


    }

}
