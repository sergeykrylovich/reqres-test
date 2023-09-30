package in.reqres.tests;


import in.reqres.pojo.ListUsersPojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static in.reqres.specs.ReqresSpecs.*;
import static io.restassured.RestAssured.given;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;

@Epic("Test list of users API")
public class UsersTest {


    //@CsvFileSource(resources = "/ListUsersData.csv")
    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForMethodSource")
    @ParameterizedTest(name = "with page: {0}, perpage: {1}")
    @Description("Request list of users with positive data")
    public void getListOfUsersPositiveTest(int page, int perPage) {
        List<ListUsersPojo> listOfUsers = given(requestSpecification())
                .queryParams("page", page, "per_page", perPage)
                .when()
                .get("/users")
                .then()
                .log().all()
                .spec(responseSpecification())
                .extract().jsonPath().getList("data", ListUsersPojo.class);

        assertThat(listOfUsers.size()).isEqualTo(perPage);
        assertThat(listOfUsers.get(perPage - 1).getId()).isEqualTo(perPage * page);
    }

    @Issue("Jira-1")
    @Tag("NegativeTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#negativeDataForMethodSource")
    @ParameterizedTest(name = "with page: {0}, perpage: {1}")
    @Description("Request single userId of users with negative data")
    public void getListOfUsersNegativeTest(int page, int perPage) {
        List<ListUsersPojo> listOfUsers = given(requestSpecification())
                .queryParams("page", page, "per_page", perPage)
                .when()
                .get("/users")
                .then()
                .log().all()
                .spec(responseSpecification())
                .extract().jsonPath().getList("data", ListUsersPojo.class);

        assertThat(listOfUsers.size()).isEqualTo(0);
    }

    @Tag("PositiveTest")
    @ValueSource(ints = {1, 2, 11, 12})
    @ParameterizedTest(name = "by userid: {0}")
    @Description("Request single userId of users with positive data")
    public void getSingleUserPositiveTest(int userId) {
        ListUsersPojo singleUser = given(requestSpecification())
                .when()
                .get("/users/" + userId)
                .then()
                .log().all()
                .spec(responseSpecification())
                .extract().jsonPath().getObject("data", ListUsersPojo.class);

        assertThat(singleUser.getId()).isEqualTo(userId);
        assertThat(singleUser.getAvatar()).contains(userId + "-image");
    }
    @Tag("NegativeTest")
    @ValueSource(ints = {-1, 0, 13})
    @ParameterizedTest(name = "by userid: {0}")
    @Description("Request single userId of users with negative data")
    public void getSingleUserNegativeTest(int userId) {
        ListUsersPojo singleUser = given(requestSpecification())
                .when()
                .get("/users/" + userId)
                .then()
                .log().all()
                .spec(responseSpecification404())
                .extract().jsonPath().getObject("data", ListUsersPojo.class);

    }
    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForPutSingleUser")
    @ParameterizedTest(name = " update userid: {0} with name: {1} and job: {2}")
    @Description("Update user")
    public void putSingleUsersPositiveTest(int userId, String name, String job) {
        Map<String, String> newmap = new HashMap<>();
        newmap.put("name",name);
        newmap.put("job",job);

        JsonPath jsonPath = given(requestSpecification())
                .body(newmap)
                .when()
                //.log().all()
                .put("/users/500")
                .then()
                .log().body()
                .spec(responseSpecification())
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .extract().body().jsonPath();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        LocalDateTime date = LocalDateTime.parse(jsonPath.get("updatedAt"), dateTimeFormatter);
        assertThat(date.getHour()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getHour()));
        assertThat(date.getMinute()).isEqualTo((LocalDateTime.now(ZoneOffset.UTC).getMinute()));



    }




}
