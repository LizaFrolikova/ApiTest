import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.matcher.RestAssuredMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class Test_UI {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    @Test
    public void testFindPurchaseOrderById() {
        // Подготовка данных
        int orderId = 1111;
        int expectedStatusCode = 404;
        String expectedOrderStatus = "placed"; // Ожидаемый статус заказа

        // Выполнение действия
        Response response = given()
                .baseUri(BASE_URL)
                .pathParam("id", orderId)
                .when()
                .get("/store/order/{id}");

        // Выполнение проверок
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Test
    public void testDeletePurchaseOrderById() {
        // Подготовка данных
        int orderId = 1111; //ID существующего заказа
        int expectedStatusCode = 404;

        // Выполнение действия
        Response response = given()
                .baseUri(BASE_URL)
                .pathParam("id", orderId)
                .when()
                .delete("/store/order/{id}");

        // Выполнение проверок
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Test
    public void testPlaceOrderForPet() {
        // Подготовка данных
        String requestBody = "{ \"petId\": 12345, \"quantity\": 5, \"shipDate\": \"2025-03-15T07:36:41.044Z\", \"status\": \"placed\", \"complete\": true }";
        int expectedStatusCode = 200;

        // Выполнение действия
        Response response = given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/store/order");

        // Выполнение проверок
        assertEquals(expectedStatusCode, response.getStatusCode());
        assertEquals("placed", response.jsonPath().getString("status")); // Проверка статуса заказа
    }
}
