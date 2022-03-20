package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import sptint3.OrdersClient;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetListOrdersTest {

    private OrdersClient ordersClient;
    private List<String> ordersStatus;


    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Выполнение запроса на получение списка заказов")
    @Description("Выполнение запроса на получение списка заказа: Все активные или все завершенные заказы")
    public void ordersCreateWithValidCredentials()     {

        ValidatableResponse createResponse = ordersClient.orderListAllActive();
        int statusCode = createResponse.extract().statusCode();
        List<String> ordersStatus = Collections.singletonList(String.valueOf(createResponse.extract().body()));

        assertThat("Успешное выполнение запроса на получение списка заказов, статус код:",statusCode,equalTo(200));
        assertThat("Значение возвращаемого тела не пустое",ordersStatus, notNullValue());
    }

}
