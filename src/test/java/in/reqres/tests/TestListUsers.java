package in.reqres.tests;

import in.reqres.pojo.ListUsersPojo;
import in.reqres.specs.ReqresSpecs;
import in.reqres.tests.test_data.DataForTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

@Epic("Test list of users API")
public class TestListUsers {


    //@CsvFileSource(resources = "/ListUsersData.csv")
    @Tag("PositiveTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#positiveDataForMethodSource")
    @ParameterizedTest(name = "Positive test of list users")
    @Description("Request list of users with positive data")
    public void getListOfUsersPositiveTest(int page, int perPage) {
        List<ListUsersPojo> listOfUsers = given(ReqresSpecs.requestSpecification())
                .when()
                .get(String.format("/users?page=%d&per_page=%d", page, perPage))
                .then()
                .log().all()
                .spec(ReqresSpecs.responseSpecification())
                .extract().jsonPath().getList("data", ListUsersPojo.class);

        assertThat(listOfUsers.size()).isEqualTo(perPage);
        assertThat(listOfUsers.get(perPage - 1).getId()).isEqualTo(perPage * page);
    }

    @Issue("Jira-1")
    @Tag("NegativeTest")
    @MethodSource(value = "in.reqres.tests.test_data.DataForTests#negativeDataForMethodSource")
    @ParameterizedTest(name = "Negative test of list users")
    public void getListOfUsersNegativeTest(int page, int perPage) {
        List<ListUsersPojo> listOfUsers = given(ReqresSpecs.requestSpecification())
                .when()
                .get(String.format("/users?page=%d&per_page=%d", page, perPage))
                .then()
                .log().all()
                .spec(ReqresSpecs.responseSpecification())
                .extract().jsonPath().getList("data", ListUsersPojo.class);

        assertThat(listOfUsers.size()).isEqualTo(0);
    }


}
