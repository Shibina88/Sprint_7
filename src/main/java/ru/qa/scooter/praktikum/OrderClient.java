package ru.qa.scooter.praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";
    private static final String CREATE_PATH = "api/v1/orders";
    private static final String ACCEPT_PATH = "api/v1/orders/accept/";
    private static final String GET_ORDER_BY_TRACK_PATH = "api/v1/orders/track";
    private static final String GET_ORDER_LIST_PATH = "api/v1/orders";
    private static final String FINISH_PATH = "api/v1/orders/finish/";

    public OrderClient() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Create Order")
    public Response create(OrderRequest orderRequest) {
        return given()
                .header("Content-type", "application/json")
                .body(orderRequest)
                .when()
                .post(CREATE_PATH);
    }

    @Step("Accept Order")
    public void accept(int orderId, int courierId) {
        given()
                .header("Content-type", "application/json")
                .param("courierId", courierId)
                .when()
                .put(ACCEPT_PATH + orderId);
    }

    @Step("Get order by track number")
    public Order getOrderByTrack(int track) {
        return given().header("Content-type", "application/json")
                .param("t", track)
                .get(GET_ORDER_BY_TRACK_PATH).body().as(Order.class);
    }

    @Step("Get order list")
    public OrderList getOrderList(OrderListFilter filter) {
        return given().header("Content-type", "application/json")
                .formParams(getOrderListParam(filter))
                .get(GET_ORDER_LIST_PATH).body().as(OrderList.class);
    }

    @Step("Finish order")
    public void finish(int orderId) {
        given().header("Content-type", "application/json")
                .delete(FINISH_PATH + orderId);
    }

    private Map<String, String> getOrderListParam(OrderListFilter filter) {
        HashMap<String, String> map = new HashMap<>();
        map.computeIfAbsent("courierId", k -> String.valueOf(filter.getCourierId()));
        map.computeIfAbsent("nearestStation", k -> filter.getNearestStation());
        map.computeIfAbsent("limit", k -> String.valueOf(filter.getLimit()));
        map.computeIfAbsent("page", k -> String.valueOf(filter.getPage()));
        return map;
    }
}
