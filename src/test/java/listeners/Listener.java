package listeners;

import models.TestModel;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;
import utilities.date.DateFormatHelper;
import utilities.file.JsonHandler;

public class Listener extends TestListenerAdapter {

    @Override
    public void onFinish(ITestContext result) {
        super.onFinish(result);
        TestModel testModel = JsonHandler.parseJsonToTestObject();
        testModel.setEnd_time(DateFormatHelper.getCurrentDate());
        JsonHandler.writeDataToJsonFile(testModel);
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        TestModel testModel = JsonHandler.parseJsonToTestObject();
        testModel.setStart_time(DateFormatHelper.getCurrentDate());
        JsonHandler.writeDataToJsonFile(testModel);
    }
}
