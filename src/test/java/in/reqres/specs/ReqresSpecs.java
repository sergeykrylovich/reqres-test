package in.reqres.specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresSpecs {

    private static final String BASEURL = "https://reqres.in";

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASEURL)
                .setBasePath("/api")
                .setContentType("application/json; charset=UTF-8")
                .addFilter(withCustomTemplates())
                .build();
    }

    public static ResponseSpecification responseSpecification200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody(notNullValue())
                .expectContentType("application/json; charset=UTF-8")
                .build();
    }

    public static ResponseSpecification responseSpecification201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectBody(notNullValue())
                .expectContentType("application/json; charset=UTF-8")
                .build();
    }

    public static ResponseSpecification responseSpecification204() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }

    public static ResponseSpecification responseSpecification404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                //.expectBody(empty())
                .expectContentType("application/json; charset=UTF-8")
                .build();
    }

    public static void installSpecification(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

}
