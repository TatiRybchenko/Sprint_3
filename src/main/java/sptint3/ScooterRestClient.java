package sptint3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class ScooterRestClient {

    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    protected static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
