package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParameterIndexesForStatusIdUpdate {

    STATUS_ID(1),
    ID(2);

    private final int parameterIndex;
}
