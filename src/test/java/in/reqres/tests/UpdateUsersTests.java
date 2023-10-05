package in.reqres.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static in.reqres.specs.ReqresSpecs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Reqres API tests - update users")
public class UpdateUsersTests {


    @Tag("PositiveTest")
    @DisplayName(value = "Update user with method PUT")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForUpdateSingleUser")
    @ParameterizedTest(name = "using userid: {0}, name: {1} and job: {2}")
    @Description("Update user")
    public void putSingleUserPositiveTest(int userId, String name, String job) {
        installSpecification(requestSpecification(), responseSpecification200());
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name",name);
        requestBody.put("job",job);

        JsonPath jsonPath = given()
                .body(requestBody)
                .when()
                //.log().all()
                .put("/users/" + userId)
                .then()
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .extract().body().jsonPath();

        //asserting date creation with current date of UTC locale
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        LocalDateTime date = LocalDateTime.parse(jsonPath.get("updatedAt"), dateTimeFormatter);
        assertThat(date.getHour()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getHour()));
        assertThat(date.getMinute()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getMinute()));
    }

    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForUpdateSingleUser")
    @DisplayName(value = "Update user with method PATCH")
    @ParameterizedTest(name = "using userid: {0}, name: {1} and job: {2}")
    @Description("Update user")
    public void patchSingleUserPositiveTest(int userId, String name, String job) {
        installSpecification(requestSpecification(), responseSpecification200());

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name",name);
        requestBody.put("job",job);

        JsonPath jsonPath = given()
                .body(requestBody)
                .when()
                //.log().all()
                .patch("/users/" + userId)
                .then()
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .extract().body().jsonPath();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        LocalDateTime date = LocalDateTime.parse(jsonPath.get("updatedAt"), dateTimeFormatter);
        assertThat(date.getHour()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getHour()));
        assertThat(date.getMinute()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getMinute()));
    }

    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForCreateSingleUser")
    @DisplayName(value = "Create user with method POST")
    @ParameterizedTest(name = "using name: {0} and job: {1}")
    @Description("Update user")
    public void createSingleUserPositiveTest(String name, String job) {
        installSpecification(requestSpecification(), responseSpecification201());

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name",name);
        requestBody.put("job",job);

        JsonPath jsonPath = given()
                .body(requestBody)
                .when()
                //.log().all()
                .post("/users")
                .then()
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .body("id", notNullValue())
                .extract().body().jsonPath();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        LocalDateTime date = LocalDateTime.parse(jsonPath.get("createdAt"), dateTimeFormatter);
        assertThat(date.getHour()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getHour()));
        assertThat(date.getMinute()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getMinute()));
    }

    @Tag("PositiveTest")
    @ValueSource(ints = {2,3})
    @DisplayName(value = "Delete user with method DELETE")
    @ParameterizedTest(name = "using userid: {0}")
    @Description("Delete user")
    public void deleteSingleUserPositiveTest(int userId) {
        installSpecification(requestSpecification(), responseSpecification204());

        given()
                .when()
                //.log().all()
                .delete("/users/" + userId)
                .then()
                .log().body();

    }


}
