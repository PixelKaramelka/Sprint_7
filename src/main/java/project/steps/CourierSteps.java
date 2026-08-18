package project.steps;

import project.constants.*;
import project.courier.*;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static project.constants.ApiConstants.*;


public class CourierSteps {

    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiConstants.BASE_URL);
    }

    @Step("Создание нового курьера")
    public ValidatableResponse courierCreate(Courier courier) {
        return requestSpecification()
                .body(courier)
                .post(COURIER_POST_CREATE)
                .then()
                .log().all();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierLogin(CourierLoginRequest courierLoginRequest) {
        return requestSpecification()
                .body(courierLoginRequest)
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public void courierDelete(int courierId) {
        requestSpecification()
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }

    @Step("Логин курьера, получение ID, удаление курьера")
    public void courierDeleteAfterLogin(CourierLoginRequest courierDeleteAfterLogin) {
        Response response = courierLogin(courierDeleteAfterLogin)
                .extract().response();
        CourierLoginResponse courierLoginResponse = response.as(CourierLoginResponse.class);
        int courierId = courierLoginResponse.getId();
        courierDelete(courierId);
    }
}