package in.reqres.tests;


import in.reqres.helpers.RequestAPI;
import in.reqres.pojo.ListUsersPojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static in.reqres.specs.ReqresSpecs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

@Epic("Reqres API tests - get users")
public class RetrieveUsersTest {

    RequestAPI requestAPI = new RequestAPI();
    //@CsvFileSource(resources = "/ListUsersData.csv")
    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForMethodSource")
    @DisplayName(value = "Get list of users with method GET")
    @ParameterizedTest(name = "using page: {0}, perpage: {1}")
    @Description("Request list of users with positive data")
    public void getListOfUsersPositiveTest(int page, int perPage) {


        List<ListUsersPojo> listOfUsers = requestAPI.getListOfUsers(page, perPage);

        assertThat(listOfUsers.size()).isEqualTo(perPage);
        assertThat(listOfUsers.get(perPage - 1).getId()).isEqualTo(perPage * page);
    }

    @Issue("Jira-1")
    @Tag("NegativeTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#negativeDataForMethodSource")
    @DisplayName(value = "Get list of users with method GET")
    @ParameterizedTest(name = "using page: {0}, perpage: {1}")
    @Description("Request list of users with negative data")
    public void getListOfUsersNegativeTest(int page, int perPage) {


        List<ListUsersPojo> listOfUsers = requestAPI.getListOfUsers(page, perPage);

        assertThat(listOfUsers.size()).isEqualTo(0);
    }

    @Tag("PositiveTest")
    @ValueSource(ints = {1, 2, 11, 12})
    @DisplayName(value = "Get single user with method GET")
    @ParameterizedTest(name = "using userid: {0}")
    @Description("Request single user with positive data")
    public void getSingleUserPositiveTest(int userId) {

        ListUsersPojo singleUser = requestAPI.getSingleUser(userId, responseSpecification200());

        assertThat(singleUser.getId()).isEqualTo(userId);
        assertThat(singleUser.getAvatar()).contains(userId + "-image");

    }

    @Tag("NegativeTest")
    @ValueSource(ints = {-1, 0, 13})
    @DisplayName(value = "Get single user with method GET")
    @ParameterizedTest(name = "using userid: {0}")
    @Description("Request single user with negative data")
    public void getSingleUserNegativeTest(int userId) {

        ListUsersPojo singleUser = requestAPI.getSingleUser(userId, responseSpecification404());

    }


}
