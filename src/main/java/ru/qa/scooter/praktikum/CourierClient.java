package ru.qa.scooter.praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";
    private static final String CREATE_PATH = "api/v1/courier";
    private static final String LOGIN_PATH = "api/v1/courier/login";
    private static final String DELETE_PATH = "api/v1/courier/";

    public CourierClient() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("создание курьера")
    public Response create(CourierData courierData) {
        return given()
                .header("Content-type", "application/json")
                .body(courierData)
                .when()
                .post(CREATE_PATH);
    }

    @Step("Логин курьера")
    public Response login(CourierCard courierCard) {
        return given()
                .header("Content-type", "application/json")
                .body(courierCard)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Удаление курьера")
    public void delete(int id) {
        given()
                .header("Content-type", "application/json")
                .delete(DELETE_PATH + id);
    }
}
