package utilities.file;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class TestDataManager {
    private static final ISettingsFile DATABASE_PROPERTIES = new JsonSettingsFile(FileNames.DATABASE_PROPERTIES.getFileName());
    private static final ISettingsFile TEST_TABLE_DATA = new JsonSettingsFile(FileNames.TEST_TABLE_DATA.getFileName());
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile(FileNames.TEST_DATA.getFileName());
    private static final ISettingsFile API_TEST_DATA = new JsonSettingsFile(FileNames.API_TEST_DATA.getFileName());
    private static final ISettingsFile TABLE_FIELDS_DATA = new JsonSettingsFile(FileNames.TABLE_FIELDS_DATA.getFileName());

    public String getProperty(String property) {
        return DATABASE_PROPERTIES.getValue(property).toString();
    }

    public String getTableData(String data) {
        return TEST_TABLE_DATA.getValue(data).toString();
    }

    public String getApiTestData(String data) {
        return API_TEST_DATA.getValue(data).toString();
    }

    public String getTestData(String data) {
        return TEST_DATA.getValue(data).toString();
    }

    public Integer getTableFieldsData(String data) {
        return Integer.valueOf(TABLE_FIELDS_DATA.getValue(data).toString());
    }
}
