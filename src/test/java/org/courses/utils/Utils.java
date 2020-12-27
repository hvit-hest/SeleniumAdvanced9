package org.courses.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.courses.testdata.MenuItemModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

public class Utils {

    public void printTestingProperties() {
        Properties prop = loadPropertiesFile("testconfig.properties");
        prop.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private Properties loadPropertiesFile(String filePath) {
        Properties prop = new Properties();
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            prop.load(resourceAsStream);
        } catch (IOException ioe) {
            System.err.println("Unable to load properties file:" + filePath);
        }
        return prop;
    }

    public Properties getTestProperties() {
        return loadPropertiesFile("testconfig.properties");
    }

    public List<MenuItemModel> readMenuFromJson (String jsonArrayFile) {
        Type MenuType = new TypeToken<List<MenuItemModel>>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try  {
             InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(jsonArrayFile);
             reader = new JsonReader(new InputStreamReader(resourceAsStream));
        } catch (NullPointerException ioe) {
            System.err.println("Unable to read json file:" + jsonArrayFile);
        }
        return gson.fromJson(reader, MenuType);
    }
}
