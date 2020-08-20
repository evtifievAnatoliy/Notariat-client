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
    
    public void updateKeyMacroInMySQL(KeyMacro keyMacro) throws IOException, SQLException{
            
        // загружаем макросы в MySql
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                Configuration.getInstance().getProperty("user.Db"),
                Configuration.getInstance().getProperty("password.Db"))){
            
            final String reportKeyMacro = "UPDATE keyMacros SET macro_body = ? WHERE keyMacro_id = ?";
            try(PreparedStatement predStat = connection.prepareStatement(reportKeyMacro)){
                predStat.setObject(1, keyMacro.getMacro_body());
                predStat.setObject(2, keyMacro.getId());
                predStat.executeUpdate();
            }
                
        }
        
    }
    
    
    public void readKeyMacrosFromFileAndWriteToMySQL() throws IOException, ParseException, SQLException{
        
        // создаем  пустую коллекцию макросов
        ArrayList<KeyMacro> keyMacros = getEmptyKeyMakrosArrayList();
        
        File file = new File(Configuration.getInstance().getProperty("keyMacrosFromVdovkin.Path"));
        
        if(file.exists()){
        // наполняем коллекцию макросов
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))
        {
            String str;
            while ((str = br.readLine()) != null) {
                byte[] bytes = str.getBytes();
                String strToUtf8 = new String(bytes, "UTF-8");
                String[] strSplit=strToUtf8.split(";");
                strSplit[0] = strSplit[0].replaceAll(" ","");
                
                for(KeyMacro keyMacro: keyMacros){
                    if(strSplit[0].equals(keyMacro.getKey())){
                        keyMacro.setMacro_body(keyMacro.getMacro_body() + " " + strSplit[1] );
                        break;
                    }
                }
                
            }
            
        }
        // обрезаем последний пробел
        for(KeyMacro keyMacro: keyMacros){
            try{
                    keyMacro.setMacro_body(keyMacro.getMacro_body().substring(1));
                    
            }
            catch (Exception e){
                //подавляем исключение
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
    
    private ArrayList<KeyMacro> getEmptyKeyMakrosArrayList(){
     
        ArrayList<KeyMacro> keyMacros = new ArrayList<KeyMacro>();
        KeyMacro keyMacro = new KeyMacro("0","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("1","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("2","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("3","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("4","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("5","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("6","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("7","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("8","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("9","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("А","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Б","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("В","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Г","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Д","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Е","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ж","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("З","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("И","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Й","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("К","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Л","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("М","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Н","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("О","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("П","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Р","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("С","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Т","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("У","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ф","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Х","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ц","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ч","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ш","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Щ","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ъ","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ы","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ь","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Э","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Ю","");
        keyMacros.add(keyMacro);
        keyMacro = new KeyMacro("Я","");
        keyMacros.add(keyMacro);
        
        return keyMacros;
    }
}
