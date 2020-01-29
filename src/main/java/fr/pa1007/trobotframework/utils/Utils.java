package fr.pa1007.trobotframework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

    public static final Gson   GSON =
            new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private static      Logger logger;

    private Utils() {}


    public static Logger getLogger() {
        if (logger == null) {
            logger = LogManager.getLogger("T-Robot");
        }
        return logger;
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            GSON.fromJson(jsonInString, Object.class);
            return true;
        }
        catch (JsonSyntaxException ex) {
            return false;
        }
    }

}
