package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sptint3.OrdersClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;


public class GetListOrdersTest {

    private OrdersClient ordersClient;
    private String ordersListId;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Выполнение запроса на получение списка заказов")
    @Description("Выполнение запроса на получение списка заказа: Успешный запрос без courierID")
    public void ordersCreateWithValidCredentials()     {

        ValidatableResponse createOrderResponse = ordersClient.orderListAllActive();
        int statusCode = createOrderResponse.extract().statusCode();
        String ordersListId = createOrderResponse.extract().jsonPath().getString("orders.id");


        assertThat("Успешное выполнение запроса на получение списка заказов, статус код:", statusCode, equalTo(SC_OK));
        assertThat("Значение возвращаемого тела не пустое", ordersListId, notNullValue());

    }

}
