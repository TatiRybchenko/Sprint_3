package sptint3;

import io.qameta.allure.Step;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Login by courier {credentials.login}")
    public int login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH +"login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

    }

    @Step("Creating a courier")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ок");
    }
    @Step("Delete the courier {courierId}")
    public boolean delete(int courierId) {
                return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ок");
    }
}
