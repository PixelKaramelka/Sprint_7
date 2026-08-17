
import project.constants.ApiConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов и проверка, что поле orders не пустое")
    public void orderGetList() {
        given().log().all()
                .baseUri(ApiConstants.BASE_URL)
                .get(ApiConstants.ORDER_GET_LIST)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}