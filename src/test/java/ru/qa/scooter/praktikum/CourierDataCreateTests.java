package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierDataCreateTests {

    private CourierClient courierClient;
    private CourierData courierData;

    @Before
    public void setUp() {
        courierData = CourierData.courierGenerator();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("201 OK: Successful courier creation")
    public void successCourierCreate() {
        Response response = courierClient.create(courierData);
        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("409 Сonflict: creating identical couriers")
    public void conflictCreateIdCouriers() {
        courierClient.create(courierData);
        Response response = courierClient.create(courierData);
        response.then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    @Test
    @DisplayName("409 Сonflict: creating identical couriers")
    public void conflictCreateCouriersWithTheSameLog() {
        courierClient.create(courierData);
        Response response = courierClient.create(new CourierData(courierData.getLogin(), "password", "First name"));
        response.then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    @Test
    @DisplayName("200 OK: creating couriers without first Name")
    public void badRequestCreateCourierWithoutFirstName() {
        Response response = courierClient.create(new CourierData(courierData.getLogin(), courierData.getPassword(), null));
        response.then().assertThat().statusCode(201);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierClient.login(CourierCard.getCredsFrom(courierData))
                .then()
                .extract()
                .body().path("id"));
    }
}
