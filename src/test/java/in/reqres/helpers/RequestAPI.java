package in.reqres.helpers;

import in.reqres.pojo.ListUsersPojo;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import static in.reqres.specs.ReqresSpecs.*;
import static io.restassured.RestAssured.given;

public class RequestAPI {

    public List<ListUsersPojo> getListOfUsers(int page, int perPage) {
        installSpecification(requestSpecification(), responseSpecification200());

        return  given()
                .queryParams("page", page, "per_page", perPage)
                .when()
                .get("/users")
                .then()
                .log().all()
                .extract().jsonPath().getList("data", ListUsersPojo.class);

    }
   public ListUsersPojo getSingleUser(int userId, ResponseSpecification responseSpecification) {
        installSpecification(requestSpecification(), responseSpecification);

        return  given()
                .when()
                .get("/users/" + userId)
                .then()
                .log().all()
                .extract().jsonPath().getObject("data", ListUsersPojo.class);

    }

}
