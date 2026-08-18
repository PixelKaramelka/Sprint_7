package project.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import project.constants.ApiConstants;
import project.courier.OrderCreate;

import static io.restassured.RestAssured.given;
import static project.constants.ApiConstants.ORDER_POST_CREATE;


public class OrderSteps {

    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiConstants.BASE_URL);
    }

    @Step("Создание нового заказа")
    public ValidatableResponse orderCreate(OrderCreate orderCreate) {
        return requestSpecification()
                .body(orderCreate)
                .post(ORDER_POST_CREATE)
                .then();
    }
}