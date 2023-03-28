package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColumnDataValues {

    MaxVarCharValue("/maximumLengthForVarChar");

    private final String value;
}
