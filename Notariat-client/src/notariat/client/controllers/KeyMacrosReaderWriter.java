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
    
    public ArrayList<KeyMacro> readKeyMacrosFromFile() throws IOException, ParseException, SQLException{
    
        File file = new File(Configuration.getInstance().getProperty("keyMacrosFromVdovkin.Path"));
        if(!file.exists()){
            ArrayList<KeyMacro> keyMacros = new ArrayList<KeyMacro>();
            return keyMacros;
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
            return keyMacros;
            }
    }
    
    
    
    
    
    
    public void readFishSubCategoryAndWriteToMySQL(FishCategory fishCategory) throws IOException, ParseException, SQLException{
    
        File file = new File(Configuration.getInstance().getProperty("fishesFromVdovkin.Path"));
        if(!file.exists()){
            ArrayList<FishSubCategory> subCategoriesFishes = new ArrayList<FishSubCategory>();
            
        }
        // создаем коллекцию шаблонов
        ArrayList<FishSubCategory> subCategoriesFishes = new ArrayList<FishSubCategory>();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")); 
                    Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                            Configuration.getInstance().getProperty("user.Db"),
                            Configuration.getInstance().getProperty("password.Db"))){
                
                
                String str;
                int lastIdSubCategoryFishes = 0;
                while ((str = br.readLine()) != null) {
                    byte[] bytes = str.getBytes();
                    String strToUtf8 = new String(bytes, "UTF-8");
                    String[] strSplit=strToUtf8.split(";");
                    strSplit[0] = strSplit[0].replaceAll(" ","");
                    String[] strSplitCategory = strSplit[0].split(",");
                    if (Integer.parseInt(strSplitCategory[0]) == fishCategory.getCodVdovkin()){
                            if (lastIdSubCategoryFishes != Integer.parseInt(strSplitCategory[1]))
                            {
                                
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
                                // вставляе запись в таблицу Подкатегорию Шаблонов
                                int lastSubcategoryId = 0;
                                String reportSubCategory = "INSERT INTO fish_subcategories (cod_vdovkin, name) VALUES (?, ?)";
                                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategory, Statement.RETURN_GENERATED_KEYS)){
                                    //predStat.setObject(1, idCategoryFishes);
                                    predStat.setObject(1, strSplitCategory[1]);
                                    predStat.setObject(2, strSplit[1]);
                                    predStat.executeUpdate();
                                    // получаем Id последней вставленной записи
                                    ResultSet rs = predStat.getGeneratedKeys();
                                    if(rs.next())
                                    {
                                        lastSubcategoryId = rs.getInt(1);
                                    }
                                }
                                // устанавливаем связь между таблицей Категория и Подкатегория Шаблонов
                                int lastSubCategoryOfCategoriesId = 0;
                                String reportSubCategoryOfCategory = "INSERT INTO fish_category_subcategories (category_id, subcategory_id) VALUES (?, ?)";
                                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategoryOfCategory, Statement.RETURN_GENERATED_KEYS)){
                                    //predStat.setObject(1, idCategoryFishes);
                                    predStat.setObject(1, fishCategory.getId());
                                    predStat.setObject(2, lastSubcategoryId);
                                    predStat.executeUpdate();
                                    // получаем Id последней вставленной записи
                                    ResultSet rs = predStat.getGeneratedKeys();
                                    if(rs.next())
                                    {
                                        lastSubCategoryOfCategoriesId = rs.getInt(1);
                                    }
                                }
                                // устанавливаем связь между таблицей Подразделение и FISH_CATEGORY_SUBCATEGORIES
                                String reportDepartmentSubCategoryOfCategory = "INSERT INTO department_fish_category_subcategories (category_subcategories_id, department_id) VALUES (?, ?)";
                                try(PreparedStatement predStat = connection.prepareStatement(reportDepartmentSubCategoryOfCategory)){
                                    predStat.setObject(1, lastSubCategoryOfCategoriesId);
                                    predStat.setObject(2, departmentId);
                                    predStat.execute();
                                }
                                
                            }
                            lastIdSubCategoryFishes = Integer.parseInt(strSplitCategory[1]);
                    }
                }
            
            }
    }
    
}
