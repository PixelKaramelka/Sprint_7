import project.steps.*;
import project.constants.*;
import project.courier.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters (name = "Цвет самоката - {0}")
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с самокатами цвета grey, black и без цвета")
    public void orderCreate() {

        OrderCreate orderCreate = new OrderCreate(color);
        OrderSteps orderSteps = new OrderSteps();

        orderSteps.orderCreate(orderCreate)
                .assertThat().body("track", instanceOf(Integer.class))
                .and()
                .statusCode(201);

    }

}