package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.TestModel;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import resttest.RequestHandler;
import utilities.file.JsonHandler;
import utilities.file.TestDataManager;

@Listeners(listeners.Listener.class)
public class SendGetRequestTest {

    @Test
    public void sendGetRequestToNonExistencePostTest() {
        RequestHandler requestHandler = new RequestHandler();
        TestDataManager data = new TestDataManager();
        RestAssured.baseURI = data.getApiTestData("/baseUrl");
        Response response = requestHandler.sendGetRequest(data.getApiTestData("/nonExistentPostNumber"));
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_NOT_FOUND,
                "Status code is not 404");
        Assert.assertEquals(response.body().asString(), data.getApiTestData("/emptyRequestBody"),
                "Response body is not empty");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        TestModel testModel = JsonHandler.parseJsonToTestObject();
        testModel.setMethod_name(result.getMethod().getMethodName());
        testModel.setStatus_id(result.getStatus());
        JsonHandler.writeDataToJsonFile(testModel);
    }
}
