package utilities.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileNames {

    DATABASE_PROPERTIES("database_properties.json"),
    TEST_TABLE_DATA("test_table_data.json"),
    TEST_DATA("test_data.json"),
    API_TEST_DATA("api_test_data.json"),
    TABLE_FIELDS_DATA("table_fields_data.json");

    private final String fileName;
}
