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
import static org.junit.Assert.assertEquals;


public class FailedReceivingOrderByNumberTest {

    private CourierClient courierClient;
    private Courier courier;
    private OrdersClient ordersClient;
    private Orders orders;
    private int courierId;
    private  int trackId;

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
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
        OrdersClient.cancelCorrectOrders(trackId);
    }

    @Test
    @DisplayName("Выполнение запроса на получение заказа по его номеру, с некорректными значениями. Запрос без номера")
    @Description("Выполнение запроса на получение заказа по его номеру, с некорректными значениями. Запрос без номера. Корректные значения для создания и входа генерируется рандомно. Данные по заказу создаются с постоянными значениями")
    public void  receivingFailedOrderNONumber() {

        ValidatableResponse receivingOrderResponse = OrdersClient.receivingOrderNoNumber();
        int statusCode = receivingOrderResponse.extract().statusCode();
        String errorMessage = receivingOrderResponse.extract().path("message");

        assertThat("Получить заказ по его номеру не получилось, статус код:",statusCode,equalTo(400));
        assertEquals("Недостаточно данных для поиска",errorMessage);
    }

    @Test
    @DisplayName("Выполнение запроса на получение заказа по его номеру, с некорректными значениями. Запрос c несуществующем номером")
    @Description("Выполнение запроса на получение заказа по его номеру, с некорректными значениями. Запрос c несуществующем номером. Корректные значения для создания и входа генерируется рандомно. Данные по заказу создаются с постоянными значениями")
    public void receivingFailedOrderNonExistentNumber() {

        ValidatableResponse receivingOrderResponse = OrdersClient.receivingOrderByNumber(0);
        int statusCode = receivingOrderResponse.extract().statusCode();
        String errorMessage = receivingOrderResponse.extract().path("message");

        assertThat("Получить заказ по его номеру не получилось, статус код:",statusCode,equalTo(404));
        assertEquals("Заказ не найден",errorMessage);
    }

}
