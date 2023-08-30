package in.reqres.tests;

import in.reqres.pojo.ListUsersPojo;
import in.reqres.specs.ReqresSpecs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class TestListUsers {


    @CsvFileSource(resources = "/ListUsersData.csv")
    @ParameterizedTest(name = "Search")
    public void getListOfUsersTest(int page, int perPage) {
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
}
