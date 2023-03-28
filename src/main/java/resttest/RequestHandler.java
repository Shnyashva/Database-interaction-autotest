package resttest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;


public class RequestHandler {

    public Response sendGetRequest(String endpoint) {
        return RestAssured.given().request(Method.GET, endpoint);
    }
}