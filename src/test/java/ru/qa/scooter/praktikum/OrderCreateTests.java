package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTests {

    private final String[] color;
    private CourierCard.OrderClient orderClient;
    private OrderRequest orderRequest;

    public OrderCreateTests(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters()
    public static Object[][] getOrderData() {
        return new Object[][]{
                {new String[]{"Черный"}},
                {new String[]{"Серый"}},
                {new String[]{"Черный", "Серый"}},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderClient = new CourierCard.OrderClient();
        orderRequest = OrderRequest.orderGenerator();
    }

    @Test
    @DisplayName("201 Created: successful order creation")
    public void successfulOrderCreate() {
        orderRequest.setColor(color);
        Response response = orderClient.create(orderRequest);
        response.then().assertThat().body("track", notNullValue()).and().statusCode(201);
    }
}
