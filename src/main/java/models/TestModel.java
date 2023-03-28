package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TestModel {
    private String name;
    private int status_id;
    private String method_name;
    private int project_id;
    private int session_id;
    private String start_time;
    private String end_time;
    private String env;
    private String browser;
    private int author_id;
}
