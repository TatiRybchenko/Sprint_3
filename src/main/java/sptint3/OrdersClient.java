package sptint3;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class OrdersClient extends ScooterRestClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Выполнение запроса на создание заказа")
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
        String requestBodyTrackID = "{\"track\":" + trackId + "}";
        return given()
                .spec(getBaseSpec())
                .body(requestBodyTrackID)
                .when()
                .put(ORDERS_PATH + "/cancel")
                .then();
    }

}