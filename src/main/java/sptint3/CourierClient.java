package sptint3;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
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

        Map<String,String> requestBodyCourierLogin = new HashMap<>();
        requestBodyCourierLogin.put("login", courier.getLogin());
        requestBodyCourierLogin.put("password", courier.getPassword());
        requestBodyCourierLogin.put("firstName", courier.getFirstName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: логин")
    public ValidatableResponse createFailedNoLogin(Courier courier) {

        Map<String,String> requestBodyCourierLogin = new HashMap<>();
        requestBodyCourierLogin.put("password", courier.getPassword());
        requestBodyCourierLogin.put("firstName", courier.getFirstName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: пароль")
    public ValidatableResponse createFailedNoPassword(Courier courier) {

        Map<String,String> requestBodyCourierLogin = new HashMap<>();
        requestBodyCourierLogin.put("login", courier.getLogin());
        requestBodyCourierLogin.put("firstName", courier.getFirstName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyCourierLogin)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание курьера, у которого отсутствует один из параметров: имя")
    public ValidatableResponse createFailedNoFirstName(Courier courier) {

        Map<String,String> requestBodyCourierLogin = new HashMap<>();
        requestBodyCourierLogin.put("login", courier.getLogin());
        requestBodyCourierLogin.put("password", courier.getPassword());

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
