import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import project.steps.CourierSteps;
import project.courier.Courier;
import project.courier.CourierLoginRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class LoginCourierTest {

    public static String login = "karamel";
    public static String password = "123456";
    public static String firstName = "Настя";

    private CourierSteps courierSteps = new CourierSteps();

    private CourierLoginRequest validCourierLoginRequest =
            new CourierLoginRequest(login, password);

    @After
    public void deleteCourierAfterTest() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        courierSteps.courierDeleteAfterLogin(courierLoginRequest);

    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void loginCourier() {

        Courier courier = new Courier(login, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .statusCode(201);

        courierSteps.courierLogin(validCourierLoginRequest)
                .assertThat()
                .body("id", instanceOf(Integer.class))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер не может авторизоваться без введенного логина")
    public void loginCourierWithoutLogin() {

        Courier courier = new Courier(login, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .statusCode(201);

        CourierLoginRequest invalidLoginRequest =
                new CourierLoginRequest(null, password);

        courierSteps.courierLogin(invalidLoginRequest)
                .assertThat()
                .body("message",
                        equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер не может авторизоваться без введенного пароля")
    public void loginCourierWithoutPassword() {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, null);
        courierSteps.courierLogin(courierLoginRequest)
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Авторизация курьера, используя несуществующие данные")
    @Description("Проверка, что курьер не может авторизоваться, используя несуществующие данные для входа")
    public void loginCourierWithNonExistentCredential () {

        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }
}