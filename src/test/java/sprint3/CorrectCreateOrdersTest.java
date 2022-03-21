package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sptint3.Orders;
import sptint3.OrdersClient;
import static java.lang.String.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.apache.http.HttpStatus.SC_CREATED;



@RunWith(Parameterized.class)
public class CorrectCreateOrdersTest  {

     private final List<String> colorScooter;
     private OrdersClient ordersClient;
     private Orders orders;
     private int trackId;

     public CorrectCreateOrdersTest(List<String> colorScooter) {
         this.colorScooter = colorScooter;
     }
     @Parameterized.Parameters
      public static Object[][] getColor() {
       return new Object[][] {
               {List.of("BLACK")},
               {List.of("GREY")},
               {List.of("BLACK", "GREY")},
               {List.of()},
        };
      }

      @Before
      public void setUp() {
        ordersClient = new OrdersClient();
             }

    @After
    public void tearDown(){
 OrdersClient.cancelCorrectOrders(trackId);
 }

    @Test
    @DisplayName("Выполнение запроса на создание заказа с корректными значениями")
    @Description("Выполнение запроса на создание заказа с корректными значениями. Корректные значения для создания заказа постоянные, цвет самоката параметризован и изменяется {color}")
    public void ordersCreateWithValidCredentials()     {

         Orders orders = Orders.builder()
                .firstName("Naruto")
                .lastName("Uchiha")
                .address("Konoha, 142 apt.")
                .metroStation(valueOf(4))
                .phone("+7 800 355 35 35")
                .rentTime(5)
                .deliveryDate("2020-06-06")
                .comment("Saske, come back to Konoha")
                .color(colorScooter)
                .build();

         ValidatableResponse createResponse = ordersClient.createCorrectOrders(orders);
        int statusCode = createResponse.extract().statusCode();
        trackId = createResponse.extract().path("track");

        assertThat("Курьер выполнил логин, статус код:",statusCode,equalTo(SC_CREATED));
        assertThat("Идентификатор курьера ID",trackId,is(not(0)));
    }


}
