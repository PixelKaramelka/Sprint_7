import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
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
    @Before
    public void setUp() {
        Courier courier = new Courier(login, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .statusCode(201);
    }
    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void loginCourier() {

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

        CourierLoginRequest invalidLoginRequest =
                new CourierLoginRequest(null, password);

        courierSteps.courierLogin(invalidLoginRequest)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
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
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("Проверка, что курьер не может авторизоваться с несуществующим логином")
    public void loginCourierWithIncorrectLogin() {

        CourierLoginRequest courierLoginRequest =
                new CourierLoginRequest("incorrectLogin", password);

        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка, что курьер не может авторизоваться с неправильным паролем")
    public void loginCourierWithIncorrectPassword() {

        CourierLoginRequest courierLoginRequest =
                new CourierLoginRequest(login, "incorrectPassword");

        CourierSteps courierSteps = new CourierSteps();

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}