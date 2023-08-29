package in.reqres.tests;

import in.reqres.pojo.ListUsersPojo;
import in.reqres.specs.ReqresSpecs;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class TestListUsers {

    @Test
    public void getListOfUsersTest() {
        List<ListUsersPojo> listOfUsers = given(ReqresSpecs.requestSpecification())
                .when()
                .get("/users?page=1&per_page=6")
                .then()
                .log().all()
                .spec(ReqresSpecs.responseSpecification())
                .extract().jsonPath().getList("data", ListUsersPojo.class);

        assertThat(listOfUsers.size()).isEqualTo(6);
        assertThat(listOfUsers.get(5).getId()).isEqualTo(6);
    }
}
