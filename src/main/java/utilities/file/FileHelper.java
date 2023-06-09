package utilities.file;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Paths;

@UtilityClass
public class FileHelper {

    private static final String RESOURCES_PATH = "/src/main/resources/";

    private static String getProjectBaseDir() {
        return System.getProperty("user.dir") != null ? System.getProperty("user.dir") : System.getProperty("project.basedir");
    }

    public static File getResourceFileByName(String fileName) {
        return Paths.get(getProjectBaseDir(), RESOURCES_PATH + fileName).toFile();
    }
}
