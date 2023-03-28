package database;

import models.TestModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.constants.ColumnDataValues;
import utilities.constants.ParameterIndexesForNewTestTable;
import utilities.constants.ParameterIndexesForStatusIdUpdate;
import utilities.constants.TestTable;
import utilities.file.TestDataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatementsHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private final TestDataManager DATA = new TestDataManager();
    private final String SET_TEST_DATA_QUERY = "UPDATE %s " + " " + "set %s = ? where %s = ?";
    private final String GET_LAST_ROW_FROM_TABLE_QUERY = "SELECT * FROM %s " + " ORDER BY ID DESC LIMIT 1";
    private final String CREATE_NEW_TEST_QUERY = "INSERT INTO " + TestTable.TABLE_NAME.getColumnName() + "(" + TestTable.NAME.getColumnName()
            + ", " + TestTable.STATUS_ID.getColumnName() + ", " + TestTable.METHOD_NAME.getColumnName() + ", "
            + TestTable.PROJECT_ID.getColumnName() + ", " + TestTable.SESSION_ID.getColumnName() + ", "
            + TestTable.START_TIME.getColumnName() + ", " + TestTable.END_TIME.getColumnName() + ", "
            + TestTable.ENV.getColumnName() + ", " + TestTable.BROWSER.getColumnName() + ", "
            + TestTable.AUTHOR_ID.getColumnName() + ")" + " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private final String CREATE_NEW_TABLE_QUERY = "CREATE TABLE %s " +
            "(%s INT AUTO_INCREMENT PRIMARY KEY, %s VARCHAR(%s) NOT NULL, %s INT NOT NULL, " +
            "%s VARCHAR(%s) NOT NULL, %s INT NOT NULL, %s INT NOT NULL, " +
            "%s TIMESTAMP NOT NULL, %s TIMESTAMP NOT NULL, %s VARCHAR(%s) NOT NULL, " +
            "%s VARCHAR(%s) NOT NULL, %s VARCHAR(%s) NOT NULL, %s VARCHAR(%s) NOT NULL)";
    private final String DROP_TABLE_QUERY = "DROP TABLE %s";
    private final String READ_TABLE_QUERY = "Select * " + "From %s";
    private final String INSERT_TESTS_INTO_NEW_TABLE = "INSERT INTO %s " +
            "SELECT test.id, test.name, test.status_id, test.method_name, " +
            "test.project_id, test.session_id,test.start_time, test.end_time, test.env, test.browser, author.name, " +
            "project.name FROM project, test, author WHERE test.id REGEXP '^([0-9])\\\\1.*$' " +
            "AND test.project_id = project.id LIMIT 10";

    public void createNewTable() {
        String sqlQuery = String.format(CREATE_NEW_TABLE_QUERY, DATA.getTestData("/temporaryTableName"),
                TestTable.ID.getColumnName(), TestTable.NAME.getColumnName(),
                DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()),
                TestTable.STATUS_ID.getColumnName(), TestTable.METHOD_NAME.getColumnName(),
                DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()), TestTable.PROJECT_ID.getColumnName(),
                TestTable.SESSION_ID.getColumnName(), TestTable.START_TIME.getColumnName(),
                TestTable.END_TIME.getColumnName(), TestTable.ENV.getColumnName(),
                DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()),
                TestTable.BROWSER.getColumnName(), DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()),
                TestTable.AUTHOR_NAME.getColumnName(), DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()),
                TestTable.PROJECT_NAME.getColumnName(), DATA.getTableFieldsData(ColumnDataValues.MaxVarCharValue.getValue()));
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.fatal("A new table was not created");
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement getPreparedStatement(String sqlQuery) {
        try {
            return DataBaseConnector.getInstance().prepareStatement(sqlQuery);
        } catch (SQLException e) {
            LOGGER.fatal("Incorrect SQL query");
            throw new RuntimeException(e);
        }
    }

    public void insertTestsIntoNewTable() {
        String sqlQuery = String.format(INSERT_TESTS_INTO_NEW_TABLE, DATA.getTestData("/temporaryTableName"));
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.fatal("New tests were not inserted into the table");
            throw new RuntimeException(e);
        }
    }

    public void createNewTest() {
        try (PreparedStatement preparedStatement = getPreparedStatement(CREATE_NEW_TEST_QUERY)) {
            preparedStatement.setString(ParameterIndexesForNewTestTable.NAME.getParameterIndex(),
                    DATA.getTableData("/name"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.STATUS_ID.getParameterIndex(),
                    DATA.getTableData("/status_id"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.METHOD_NAME.getParameterIndex(),
                    DATA.getTableData("/method_name"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.PROJECT_ID.getParameterIndex(),
                    DATA.getTableData("/project_id"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.SESSION_ID.getParameterIndex(),
                    DATA.getTableData("/session_id"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.START_TIME.getParameterIndex(),
                    DATA.getTableData("/start_time"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.END_TIME.getParameterIndex(),
                    DATA.getTableData("/end_time"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.ENV.getParameterIndex(),
                    DATA.getTableData("/env"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.BROWSER.getParameterIndex(),
                    DATA.getTableData("/browser"));
            preparedStatement.setString(ParameterIndexesForNewTestTable.AUTHOR_ID.getParameterIndex(),
                    DATA.getTableData("/author_id"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.fatal("A new test was not created");
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getListOfDataFromColumn(int columnIndex) {
        List<Integer> list = new ArrayList<>();
        String sqlQuery = String.format(READ_TABLE_QUERY, DATA.getTestData("/temporaryTableName"));
        try (PreparedStatement selectStatement = getPreparedStatement(sqlQuery)) {
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getInt(columnIndex));
            }
        } catch (SQLException e) {
            LOGGER.fatal("Could not get data from the result set");
            throw new RuntimeException(e);
        }
        return list;
    }

    public void updateStatuses(List<?> statusesBeforeUpdate) {
        int counter = 0;
        String sqlQuery = String.format(SET_TEST_DATA_QUERY, DATA.getTestData("/temporaryTableName"),
                TestTable.STATUS_ID.getColumnName(), TestTable.ID.getColumnName());
        while (counter < statusesBeforeUpdate.size()) {
            try (PreparedStatement updateStatement = getPreparedStatement(sqlQuery)) {
                updateStatement.setInt(ParameterIndexesForStatusIdUpdate.STATUS_ID.getParameterIndex(),
                        Integer.parseInt(DATA.getTestData("/passedStatusId")));
                updateStatement.setInt(ParameterIndexesForStatusIdUpdate.ID.getParameterIndex(),
                        (Integer) statusesBeforeUpdate.get(counter));
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.fatal("Data were not updated");
                throw new RuntimeException(e);
            }
            counter++;
        }
    }

    public void deleteTable() {
        String sqlQuery = String.format(DROP_TABLE_QUERY, DATA.getTestData("/temporaryTableName"));
        try (PreparedStatement preparedStatement = getPreparedStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.fatal("The table was not deleted");
            throw new RuntimeException(e);
        }
    }

    public TestModel readTest() {
        String sqlQuery = String.format(GET_LAST_ROW_FROM_TABLE_QUERY, DATA.getTestData("/testTableName"));
        try (ResultSet resultSet = getPreparedStatement(sqlQuery).executeQuery()) {
            TestModel testModel = null;
            while (resultSet.next()) {
                testModel = new TestModel(resultSet.getString(TestTable.NAME.getColumnName()),
                        resultSet.getInt(TestTable.STATUS_ID.getColumnName()),
                        resultSet.getString(TestTable.METHOD_NAME.getColumnName()),
                        resultSet.getInt(TestTable.PROJECT_ID.getColumnName()),
                        resultSet.getInt(TestTable.SESSION_ID.getColumnName()),
                        resultSet.getString(TestTable.START_TIME.getColumnName()),
                        resultSet.getString(TestTable.END_TIME.getColumnName()),
                        resultSet.getString(TestTable.ENV.getColumnName()),
                        resultSet.getString(TestTable.BROWSER.getColumnName()),
                        resultSet.getInt(TestTable.AUTHOR_ID.getColumnName()));
            }
            return testModel;
        } catch (SQLException e) {
            LOGGER.fatal("Could not read data from result set to the test model");
            throw new RuntimeException(e);
        }
    }
}


