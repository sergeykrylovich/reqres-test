package in.reqres.tests;

import in.reqres.pojo.ListUsersPojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static in.reqres.specs.ReqresSpecs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

@Epic("Test list of users API")
public class GetUsersTest {


    //@CsvFileSource(resources = "/ListUsersData.csv")
    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForMethodSource")
    @ParameterizedTest(name = "Positive test of list users")
    @Description("Request list of users with page:{page} and per page: {perPage} with positive data")
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
    @ParameterizedTest(name = "Negative test of list users with page: {page} and per page: {perPage}")
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
    @ParameterizedTest(name = "Positive test for request single user {userId}")
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
    @ParameterizedTest(name = "Negative test for request single user {userId}")
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



}
