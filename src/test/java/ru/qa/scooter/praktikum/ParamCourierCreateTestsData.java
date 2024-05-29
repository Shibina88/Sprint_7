package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class ParamCourierCreateTestsData {

    private final String login;
    private final String password;
    private CourierClient courierClient;

    public ParamCourierCreateTestsData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters(name = "login: {0}, password: {1}")
    public static Object[][] getCourierData() {
        return new Object[][]{
                {null, "JKKBTF"},
                {null, null},
                {"Bantik", null}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test()
    @DisplayName("400 Bad Request: creating couriers without required fields")
    public void badRequestCreatingCourierWithoutRequiredFields() {
        Response response = courierClient.create(new CourierData(login, password, ""));
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(SC_BAD_REQUEST);
    }
}