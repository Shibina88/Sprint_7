package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.IsNull.notNullValue;

public class CourierDataLogTests {

    private CourierClient courierClient;
    private CourierData courierData;

    @Before
    public void setUp() {
        courierData = CourierData.courierGenerator();
        courierClient = new CourierClient();
        courierClient.create(courierData);
    }

    @Test
    @DisplayName("200 OK: successful courier login")
    public void successCourierLog() {
        Response response = courierClient.login(CourierCard.getCredsFrom(courierData));
        response.then().assertThat().statusCode(SC_OK).and().body("id", notNullValue());
    }

    @After
    public void tearDown() {
        courierClient.delete(courierClient.login(CourierCard.getCredsFrom(courierData))
                .then()
                .extract()
                .body().path("id"));
    }
}


