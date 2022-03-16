package sptint3;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Выполнение запроса логина курьера, логин и пароль: {credentials.login}")
    public  ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH +"/login")
                .then();
    }

    @Step("Выполнение запроса на создание курьера со всеми параметрами: имя, логин, пароль.")
    public ValidatableResponse createCorrect(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierPassword = courier.getPassword();
        String courierFirstName = courier.getFirstName();

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: логин")
    public ValidatableResponse createFailedNoLogin(Courier courier) {
        String courierPassword = courier.getPassword();
        String courierFirstName = courier.getFirstName();

        String registerRequestBody = "{\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров:  пароль")
    public ValidatableResponse createFailedNoPassword(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierFirstName = courier.getFirstName();

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из апараметров: имя")
    public ValidatableResponse createFailedNoFirstName(Courier courier) {
        String courierLogin = courier.getLogin();
        String courierPassword = courier.getPassword();

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\"}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Выполнение запроса на удаление курьера с его ID: {courierId}")
    public ValidatableResponse delete(int courierId) {
                return given()
                        .spec(getBaseSpec())
                        .body(courierId)
                        .when()
                        .delete(COURIER_PATH +"/" + courierId)
                        .then();
    }
}
