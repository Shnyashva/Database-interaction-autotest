package tests;

import database.DataBaseConnector;
import org.testng.annotations.AfterMethod;

public abstract class BaseTest {

    @AfterMethod
    public void tearDown() {
        DataBaseConnector.closeConnection();
    }
}
