package sptint3;

import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class OrdersClient extends ScooterRestClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Выполнение запроса на создание заказа {orders.color}")
    public  ValidatableResponse createCorrectOrders(Orders orders){

        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDERS_PATH)
                .then();
}

    @Step("Выполнение запроса на получение списка заказов: все активные и завершенные заказы курьера")
    public  ValidatableResponse orderListAllActive(){

        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH + "?courierId")
                .then();

    }

    @Step("Выполнение запроса на отмену заказа, номер трека {trackId} ")
    public static ValidatableResponse cancelCorrectOrders(int trackId){

        return given()
                .spec(getBaseSpec())
                .queryParam("track", trackId)
                .when()
                .put(ORDERS_PATH + "/cancel")
                .then();
    }

    @Step("Выполнение запроса на получение заказа по его номеру, номер трека {trackId} ")

    public static ValidatableResponse receivingOrderByNumber(int trackId){

        return given()
                .spec(getBaseSpec())
                .queryParam("t", trackId)
                .when()
                .get(ORDERS_PATH + "/track")
                .then();
    }
    @Step("Выполнение запроса на получение заказа без номера трека")

    public static ValidatableResponse receivingOrderNoNumber(){

        return given()
                .spec(getBaseSpec())
                .queryParam("t")
                .when()
                .get(ORDERS_PATH + "/track")
                .then();
    }

    /*
    @Step("Выполнение запроса на принятие заказа, номер трека {trackId} ")

    public static ValidatableResponse acceptCorrectOrders(accept){
      //  String requestBodyTrackID = "{\"track\":" + trackId + "}";
        return given()
                .spec(getBaseSpec())
                .queryParam("parameter1", trackId)
                .queryParam("parameter2", courierId)
                .when()
                .put(ORDERS_PATH + "/accept/"+ "?courierId="+ trackId)
                .then();
    }
*/

}