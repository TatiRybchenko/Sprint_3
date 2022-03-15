package sptint3;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;


public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Login by courier {credentials.login}")
    public  ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH +"/login")
                .then();
    }

    @Step("Creating correct courier")
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

    @Step("Creating Failed (No Login) courier")
    public ValidatableResponse createFailed(Courier courier) {
      //  String courierLogin = courier.getLogin();
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

    @Step("Delete the courier {courierId}")
    public ValidatableResponse delete(int courierId) {
                return given()
                .spec(getBaseSpec())
                        .body(courierId)
                .when()
                .delete(COURIER_PATH +"/" + courierId)
                .then();
    }
}
