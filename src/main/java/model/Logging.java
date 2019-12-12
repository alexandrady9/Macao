package model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {

    static Logger logger;
    public FileHandler fileHandler;
    SimpleFormatter formatter;

    public Logging() throws IOException{
        logger = Logger.getLogger(Logging.class.getName());
        fileHandler = new FileHandler("E:\\Facultate\\Anul III\\TW\\Macao\\src\\main\\java\\logger\\LogFile.log",true);
        formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
    }

    private static Logger getLogger(){
        if(logger == null){
            try {
                new Logging();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
        System.out.println(msg);
    }
}
