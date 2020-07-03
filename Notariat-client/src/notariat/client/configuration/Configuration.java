/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author user
 */
public class Configuration {
    
    private static Configuration instance;
    private final static String DEFAULT_PATCH = "config.properties";
    private final static String USER_PATCH = "userConfig.properties";
    private Properties properties;
    
    
    private Configuration() throws FileNotFoundException, IOException{
        Properties defaultProperties = new Properties();
        try{
            defaultProperties.load(new FileInputStream(this.USER_PATCH));
            properties = new Properties(defaultProperties);
        }
        catch(Exception ex){
        
            properties = new Properties(defaultProperties);
            try{
                defaultProperties.load(new FileInputStream(DEFAULT_PATCH));
            }
             catch(IOException e){
                throw new RuntimeException("Error. Ошибка чтения файла с настройкам.");
            }
        }
    }
    
    public static Configuration getInstance() throws IOException{
        if (instance == null)
            instance = new Configuration();
    return instance;
    }
    
    public String getProperty(String path){
        return properties.getProperty(path);
    }
    public boolean isProperty(String path){
        String str = properties.getProperty(path, "Error");
        if (str == "Error")
            return false;
        else
            return true;
    }
    
}
