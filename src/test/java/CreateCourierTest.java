import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import project.courier.Courier;
import project.courier.CourierLoginRequest;
import project.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    public static String login = "karamel";
    public static String password = "123456";
    public static String firstName = "Настя";

    private final CourierSteps courierSteps = new CourierSteps();

    @After
    public void deleteCourier() {
        CourierLoginRequest courierLoginRequest =
                new CourierLoginRequest(login, password);
        courierSteps.courierDeleteAfterLogin(courierLoginRequest);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем создание нового курьера с валидно заполненными полями")
    public void createNewCourier() {

        Courier courier = new Courier(login, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Попытка создать двух одинаковых курьеров с одинаковыми логином, паролем и именем")
    public void createTwoIdenticalCouriers() {

        Courier courier = new Courier(login, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierSteps.courierCreate(courier)
                .assertThat()
                .statusCode(409)
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без введенного пароля")
    public void createCourierWithoutPassword() {

        Courier courier = new Courier(login, null, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без введенного логина")
    public void createCourierWithoutLogin() {

        Courier courier = new Courier(null, password, firstName);

        courierSteps.courierCreate(courier)
                .assertThat()
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}