package model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConfigureLog {

    public static Logger configureLogFile() {
        Logger logger = Logger.getLogger("Logger");

        try {
            FileHandler fileHandler= new FileHandler("E:\\Facultate\\Anul III\\TW\\Macao\\src\\main\\java\\logger\\LogFile.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            return logger;
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
