package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParameterIndexesForNewTestTable {

    NAME(1),
    STATUS_ID(2),
    METHOD_NAME(3),
    PROJECT_ID(4),
    SESSION_ID(5),
    START_TIME(6),
    END_TIME(7),
    ENV(8),
    BROWSER(9),
    AUTHOR_ID(10);

    private final int parameterIndex;
}
