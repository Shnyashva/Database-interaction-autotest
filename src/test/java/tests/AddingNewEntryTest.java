package tests;

import database.StatementsHandler;
import models.TestModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.file.JsonHandler;

public class AddingNewEntryTest extends BaseTest {

    @Test
    public void runTest() {
        StatementsHandler statementsHandler = new StatementsHandler();
        statementsHandler.createNewTest();
        TestModel testFromTestData = JsonHandler.parseJsonToTestObject();
        TestModel testFromTheTable = statementsHandler.readTest();
        Assert.assertEquals(testFromTheTable, testFromTestData, "Information was not added");
    }
}
