package sptint3;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static sptint3.EndPoints.*;
import static org.apache.http.HttpStatus.SC_CONFLICT;
public class OrdersClient extends ScooterRestClient {


    @Step("Выполнение запроса на создание заказа {orders.color}")
    public  ValidatableResponse createCorrectOrders(Orders orders){

        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDERS_CREATE)
                .then();
}

    @Step("Выполнение запроса на получение списка заказов: успешный запрос без courierId ")
    public  ValidatableResponse orderListAllActive(){

        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_LIST)
                .then();

    }

    @Step("Выполнение запроса на получение заказа по его номеру, номер трека {trackId} ")
    public static ValidatableResponse receivingOrderByNumber(int trackId){

        return given()
                .spec(getBaseSpec())
                .queryParam("t", trackId)
                .when()
                .get(ORDERS_RECEIVING)
                .then();
    }

    @Step("Выполнение запроса на получение заказа без номера трека")
    public static ValidatableResponse receivingOrderNoNumber(){

        return given()
                .spec(getBaseSpec())
                .queryParam("t")
                .when()
                .get(ORDERS_RECEIVING)
                .then();
    }
    @Step("Выполнение запроса на отмену заказа, номер трека {trackId} ")
    public static ValidatableResponse cancelCorrectOrders(int trackId){

        return given()
                .spec(getBaseSpec())
                .queryParam("track", trackId)
                .when()
                .put(ORDERS_CANCEL)
                .then();
    }

    @Step("Выполнение запроса на принятие заказа по id заказа {ordersId} и id курьера {courierId} ")
    public static ValidatableResponse acceptOrderByNumber(int orderId, int courierId){

        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER + orderId)
                .then();
    }

    @Step("Выполнение запроса на приянтие заказа, нет ID заказа {trackId} ")
    public static ValidatableResponse acceptOrderNoOrderId(int courierId){

        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER)
                .then();

    }

    @Step("Выполнение запроса на принятие заказа по id заказа {ordersId} и id курьера {courierId} ")
    public static ValidatableResponse acceptOrderNoCourierId(int orderId){

        return given()
                .spec(getBaseSpec())
                .when()
                .put(ACCEPT_ORDER + orderId)
                .then();
    }

}