package utilities.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.TestModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JsonHandler {

    private static ObjectMapper mapper;
    private static final Logger LOGGER = LogManager.getLogger();

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static void writeDataToJsonFile(Object obj) {
        try {
            mapper.writeValue(FileHelper.getResourceFileByName(FileNames.TEST_TABLE_DATA.getFileName()), obj);
        } catch (IOException e) {
            LOGGER.fatal("Json file not found");
            throw new RuntimeException(e);
        }
    }

    public static TestModel parseJsonToTestObject() {
        try {
            return getObjectMapper().readValue(FileHelper.getResourceFileByName(FileNames.TEST_TABLE_DATA.getFileName()),
                    TestModel.class);
        } catch (IOException e) {
            LOGGER.fatal("Json file not found");
            throw new RuntimeException(e);
        }
    }
}