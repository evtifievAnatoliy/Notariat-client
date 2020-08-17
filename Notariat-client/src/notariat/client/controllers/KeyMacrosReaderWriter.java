/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import notariat.client.configuration.Configuration;
import notariat.client.models.FishCategory;
import notariat.client.models.FishSubCategory;
import notariat.client.models.KeyMacro;

/**
 *
 * @author eag
 */
public class KeyMacrosReaderWriter {
    
    // метод чтения из файла Макросов и запись их в базу данных MySQL
    
    public ArrayList<KeyMacro> readKeyMacros() throws IOException, ParseException, SQLException{
    
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                    Configuration.getInstance().getProperty("user.Db"), 
                    Configuration.getInstance().getProperty("password.Db"))){
            try(Statement st = connection.createStatement()){
                final String report = "SELECT * FROM keyMacros km " +
                                        "INNER JOIN departments d "
                                            + "on km.department_id = d.id " +
                                        "WHERE d.name = '" + Configuration.getInstance().getProperty("department") + "'";
                try (ResultSet rs = st.executeQuery(report)){
                        // создаем коллекцию Сатегорий Шаблонов
                        ArrayList<KeyMacro> keyMacros = new ArrayList<KeyMacro>();
                        while (rs.next()) {
                            // пробуем создать объект шаблон и добавить его в коллекцию
                            KeyMacro keyMacro = new KeyMacro(rs.getInt("keyMacro_id"), rs.getString("keyMacro"), rs.getString("macro_body"));
                            keyMacros.add(keyMacro);
                        }
                        return keyMacros;
                    }
            }
            
        }
        catch(SQLException ex){
            throw new IllegalArgumentException("Error. Ошибка чтения  макросов из базы данных.!!!\n" + ex.getMessage());
        }
           
    }
    
    
    
    
    
    
    public void readKeyMacrosFromFileAndWriteToMySQL() throws IOException, ParseException, SQLException{
    
        File file = new File(Configuration.getInstance().getProperty("keyMacrosFromVdovkin.Path"));
        if(!file.exists()){
            ArrayList<KeyMacro> keyMacros = new ArrayList<KeyMacro>();
        }
        // создаем коллекцию макросов
        ArrayList<KeyMacro> keyMacros = new ArrayList<KeyMacro>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))
        {
            String str;
            KeyMacro lastKeyMacro = new KeyMacro("","");
            while ((str = br.readLine()) != null) {
                byte[] bytes = str.getBytes();
                String strToUtf8 = new String(bytes, "UTF-8");
                String[] strSplit=strToUtf8.split(";");
                strSplit[0] = strSplit[0].replaceAll(" ","");
                if(strSplit[0].equals(lastKeyMacro.getKey())){
                    lastKeyMacro.setMacro_body(lastKeyMacro.getMacro_body() + " " + strSplit[1]);
                }
                else{
                    KeyMacro keyMacro = new KeyMacro(strSplit[0], strSplit[1]);
                    lastKeyMacro = keyMacro;
                    keyMacros.add(keyMacro);
                }
            }
            
        }
            
        // загружаем макросы в MySql
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                Configuration.getInstance().getProperty("user.Db"),
                Configuration.getInstance().getProperty("password.Db"))){
            
            // получаем Id подразделения
            int departmentId = 0;
            try(Statement st = connection.createStatement()){
                final String findDepartmentId = "SELECT * FROM departments dep WHERE dep.name = '" + 
                Configuration.getInstance().getProperty("department") + "'";
                try (ResultSet rs = st.executeQuery(findDepartmentId)){
                    while (rs.next()) {
                        departmentId = rs.getInt("id");
                    }
                }
            }
            
            for(KeyMacro keyMacro: keyMacros){
              
                String reportKeyMacro = "INSERT INTO keyMacros (department_id, keyMacro, macro_body) VALUES (?, ?, ?)";
                    try(PreparedStatement predStat = connection.prepareStatement(reportKeyMacro)){
                        predStat.setObject(1, departmentId);
                        predStat.setObject(2, keyMacro.getKey());
                        predStat.setObject(3, keyMacro.getMacro_body());
                        predStat.execute();
                    }
            }    
        }
    }
}
