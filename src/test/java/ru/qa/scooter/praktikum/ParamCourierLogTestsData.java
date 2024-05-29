package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class ParamCourierLogTestsData {
    private final String login;
    private final String password;
    private final String messageError;
    private final int code;
    private CourierClient courierClient;

    public ParamCourierLogTestsData(String login, String password, String messageError, int code) {
        this.login = login;
        this.password = password;
        this.messageError = messageError;
        this.code = code;
    }

    @Parameterized.Parameters(name = "login: {0}, password: {1}")
    public static Object[][] getCourierData() {
        return new Object[][]{
                {null, "JKKBTF", "Недостаточно данных для входа", SC_BAD_REQUEST},
                {"Bantik", "", "Недостаточно данных для входа", SC_BAD_REQUEST},
                {null, "", "Недостаточно данных для входа", SC_BAD_REQUEST},
                {"login", "JKKBTF", "Учетная запись не найдена", SC_NOT_FOUND},
                {"Bantik", "password", "Учетная запись не найдена", SC_NOT_FOUND},
                {"Bantik25622", "JKKBTFnHGfdr", "Учетная запись не найдена", SC_NOT_FOUND}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("400 Bad Request: creating couriers without required fields")
    public void badRequestCourierLoginWithoutRequiredFields() {
        Response response = courierClient.login(new CourierCard(login, password));
        response.then().assertThat().body("message", equalTo(messageError))
                .and().statusCode(code);
    }
}
