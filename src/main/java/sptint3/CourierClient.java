package sptint3;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static sptint3.EndPoints.*;

public class CourierClient extends ScooterRestClient {

    @Step("Выполнение запроса логина курьера, логин {credentials.login} и пароль {credentials.password}")
    public  ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Выполнение запроса на создание курьера со всеми параметрами: имя, логин, пароль.")
    public ValidatableResponse createCorrect(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierPassword = courier.getPassword();
        String courierFirstName = courier.getFirstName();

        String requestBodyCourierLogin = "{\"login\":\"" + courierLogin + "\","
                                    + "\"password\":\"" + courierPassword + "\","
                                    + "\"firstName\":\"" + courierFirstName + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: логин")
    public ValidatableResponse createFailedNoLogin(Courier courier) {
        String courierPassword = courier.getPassword();
        String courierFirstName = courier.getFirstName();

        String requestBodyCourierLogin = "{\"password\":\"" + courierPassword + "\","
                                    + "\"firstName\":\"" + courierFirstName + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: пароль")
    public ValidatableResponse createFailedNoPassword(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierFirstName = courier.getFirstName();

        String requestBodyCourierLogin = "{\"login\":\"" + courierLogin + "\","
                                    + "\"firstName\":\"" + courierFirstName + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: имя")
    public ValidatableResponse createFailedNoFirstName(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierPassword = courier.getPassword();

        String requestBodyCourierLogin = "{\"login\":\"" + courierLogin + "\","
                                    + "\"password\":\"" + courierPassword + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на удаление курьера с его ID: {courierId}")
    public ValidatableResponse delete(int courierId) {
                return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }

    @Step("Выполнение запроса на удаление курьера с его ID: {courierId}")
    public ValidatableResponse deleteFailedINull(int courierId) {
               return given()
                       .spec(getBaseSpec())
                       .queryParam("id", courierId)
                       .when()
                       .delete(COURIER_DELETE)
                       .then();
    }
}
