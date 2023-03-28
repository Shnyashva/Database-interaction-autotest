package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestTable {

    ID("id"),
    TABLE_NAME("test"),
    NAME("name"),
    STATUS_ID("status_id"),
    METHOD_NAME("method_name"),
    PROJECT_ID("project_id"),
    SESSION_ID("session_id"),
    START_TIME("start_time"),
    END_TIME("end_time"),
    ENV("env"),
    BROWSER("browser"),
    AUTHOR_ID("author_id"),
    AUTHOR_NAME("author_name"),
    PROJECT_NAME("project_name");

    private final String columnName;
}
