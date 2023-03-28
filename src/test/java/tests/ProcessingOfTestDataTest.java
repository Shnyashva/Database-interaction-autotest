package tests;

import database.DataBaseConnector;
import database.StatementsHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.file.TestDataManager;

import java.util.List;

public class ProcessingOfTestDataTest {

    @BeforeMethod
    public void setUp() {
        StatementsHandler statementsHandler = new StatementsHandler();
        statementsHandler.createNewTable();
        statementsHandler.insertTestsIntoNewTable();
    }

    @Test
    public void runTest() {
        StatementsHandler statementsHandler = new StatementsHandler();
        TestDataManager data = new TestDataManager();
        SoftAssert softAssert;
        List<Integer> idList = statementsHandler
                .getListOfDataFromColumn(data.getTableFieldsData("/idColumn"));
        statementsHandler.updateStatuses(idList);
        List<Integer> statusesAfterUpdate = statementsHandler
                .getListOfDataFromColumn(data.getTableFieldsData("/statusIdColumn"));
        softAssert = new SoftAssert();
        for (Integer status : statusesAfterUpdate) {
            softAssert.assertEquals(status, Integer.valueOf(data.getTestData("/passedStatusId")),
                    "Test failed or skipped");
        }
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        StatementsHandler statementsHandler = new StatementsHandler();
        statementsHandler.deleteTable();
        DataBaseConnector.closeConnection();
    }
}
