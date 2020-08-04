/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.SortedMap;
import notariat.client.configuration.Configuration;
import notariat.client.models.FishCategory;
import notariat.client.models.Fish;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author eag
 */
public class FishesReaderWriter {
    
    // метод получения Категорий Шаблонов(Рыб)
    public ArrayList<FishCategory> readFishCategories() throws IOException{
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                    Configuration.getInstance().getProperty("user.Db"), 
                    Configuration.getInstance().getProperty("password.Db"))){
            try(Statement st = connection.createStatement()){
                final String report = "SELECT * FROM FISH_CATEGORIES";
                try (ResultSet rs = st.executeQuery(report)){
                        // создаем коллекцию Сатегорий Шаблонов
                        ArrayList<FishCategory> fishCategories = new ArrayList<FishCategory>();
                        while (rs.next()) {
                            // пробуем создать объект шаблон и добавить его в коллекцию
                            FishCategory fishCategory = new FishCategory(rs.getInt("category_id"), rs.getInt("cod_vdovkin"), rs.getString("name"));
                            fishCategories.add(fishCategory);
                        }
                        return fishCategories;
                    }
            }
            
        }
        catch(SQLException ex){
            throw new IllegalArgumentException("Error. Ошибка чтения Категории Шаблонов из базы данных.!!!\n" + ex.getMessage());
        }
    }
    // метод получения Подкатегорий Шаблонов(Рыб)
    public ArrayList<FishSubCategory> readFishSubCategories(FishCategory fishCategory) throws IOException{
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                    Configuration.getInstance().getProperty("user.Db"), 
                    Configuration.getInstance().getProperty("password.Db"))){
            try(Statement st = connection.createStatement()){
                final String report = "SELECT * FROM FISH_SUBCATEGORIES subCategory " +
                                        "inner join FISH_CATEGORY_SUBCATEGORIES subCategoryOfCategory "
                                            + "on subCategoryOfCategory.subcategory_id = subCategory.subcategory_id " +
                                        "inner join FISH_CATEGORIES category "
                                            + "on category.category_id = subCategoryOfCategory.category_id " +
                                        "inner join DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES dfcs "
                                            + "on dfcs.category_subcategories_id = subCategoryOfCategory.id " +
                                        "inner join DEPARTMENTS department " 
                                            + "on department.id = dfcs.department_id " +
                                        "WHERE category.category_id = " + fishCategory.getId()
                                            + " AND department.name = '" + Configuration.getInstance().getProperty("department") +"'";
                try (ResultSet rs = st.executeQuery(report)){
                        // создаем коллекцию Сатегорий Шаблонов
                        ArrayList<FishSubCategory> fishSubCategories = new ArrayList<FishSubCategory>();
                        while (rs.next()) {
                            // пробуем создать объект шаблон и добавить его в коллекцию
                            FishSubCategory fishSubCategory = new FishSubCategory(rs.getInt("subcategory_id"), fishCategory.getId(), rs.getInt("cod_vdovkin"), rs.getString("name"));
                            fishSubCategories.add(fishSubCategory);
                        }
                        return fishSubCategories;
                    }
            }
            
        }
        catch(SQLException ex){
            throw new IllegalArgumentException("Error. Ошибка чтения Категории Шаблонов из базы данных.!!!\n" + ex.getMessage());
        }
    }
    //-------------------------------------------
    
    
    
    // метод записи в MYSQL. 
    public void writeFishes(ArrayList<Fish> fishes) throws IOException{
//      Connection conn = getConnection();
        
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                    Configuration.getInstance().getProperty("user.Db"), 
                    Configuration.getInstance().getProperty("password.Db"))){
                /*
                if (newItem != null){
                    String report = "INSERT INTO ITEMS (ARTICLE, NAME, COLOR, PRICE, STOCK_BALANCE) VALUES (?, ?, ?, ?, ?)";
                    try(PreparedStatement predStat = connection.prepareStatement(report)){
                        predStat.setObject(1, newItem.getArticle());
                        predStat.setObject(2, newItem.getName());
                        predStat.setObject(3, newItem.getColor());
                        predStat.setObject(4, newItem.getPrice());
                        predStat.setObject(5, newItem.getStockBalance());
                        predStat.execute();
                    }
                }
                if (updateItems != null){
                    for(Item i : updateItems){
                        String report = "UPDATE ITEMS SET NAME = ?, COLOR = ?, PRICE = ?, STOCK_BALANCE = ? WHERE ARTICLE = ?";
                        try(PreparedStatement predStat = connection.prepareStatement(report)){
                            predStat.setObject(1, i.getName());
                            predStat.setObject(2, i.getColor());
                            predStat.setObject(3, i.getPrice());
                            predStat.setObject(4, i.getStockBalance());
                            predStat.setObject(5, i.getArticle());
                            predStat.execute();
                        }
                    }
                }*/
           }
        
            catch(SQLException ex){
                throw new IllegalArgumentException("Error. Ошибка соединения с базой данных.!!!\n" + ex.getMessage());
            }
    }
    
    public Connection getConnection() {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://192.168.250.133:3306/Notariat?characterEncoding=utf8";
        Connection conn = null;
        try {
            Class.forName(driver);
            
            Properties connInfo = new Properties();
            connInfo.put("user", "webadmin");
            connInfo.put("password", "WebAdmin1");
            connInfo.put("charSet", "utf8");
            
            conn = DriverManager.getConnection(url, connInfo);
            
            String t = "";
            t += "";
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return conn;
    }
    
    
    // метод чтения Шаблонов из файла . 
    public ArrayList<Fish> readFishes(FishSubCategory subCategoryFishes, MainController mainController) throws IOException, ParseException{
        
        File file = new File(Configuration.getInstance().getProperty("fishesFromVdovkin.Path"));
        if(!file.exists()){
            ArrayList<Fish> fishes = new ArrayList<Fish>();
            return fishes;
        }
        // создаем коллекцию шаблонов
        ArrayList<Fish> fishes = new ArrayList<Fish>();
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String str;
                while ((str = br.readLine()) != null) {
                    String[] strSplit=str.split(";");
                    // пробуем создать объект товар и добавить его в коллекцию
                    StringBuilder stringBuilder = new StringBuilder();
                    File fileFish = new File("D:\\Java\\FISHVDOVKIN\\NK2FISH\\" + strSplit[3].replaceAll(" ","") + ".fis");
                    if(!fileFish.exists()){
                        stringBuilder.append("Шаблон пуст");
                        //break;
                    }
                    //try(BufferedReader brFish = new BufferedReader(new FileReader(fileFish))){
                    try(BufferedReader brFish = new BufferedReader(new InputStreamReader(new FileInputStream(fileFish), "CP866"))){
                        String strFish;
                        while((strFish = brFish.readLine()) != null){
                            
                            byte[] bytes = strFish.getBytes();
                            String strFishIncoding = new String(bytes, "UTF-8");
                            stringBuilder.append(strFishIncoding);
                            stringBuilder.append("\n");
                            
                        }
                    }
                    catch(Exception e){
                        
                    }
                    if (strSplit[0].replaceAll(" ", "").equals(
                            mainController.getFishCategories().findFishCategoryById(subCategoryFishes.getCategoryId()).getCodVdovkin()
                                    + "," + subCategoryFishes.getCodVdovkin()/*"13,3"*/)){
                        Fish fish = new Fish(strSplit[2], stringBuilder.toString());
                        fishes.add(fish);
                    }
            }
            return fishes;
        }
        //return fishes;
    }
    
    // метод чтения из файла Подкатегорий и запись их в базу данных MySQL
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
                                    final String findDepartmentId = "SELECT * FROM DEPARTMENTS dep WHERE dep.name = '" + 
                                            Configuration.getInstance().getProperty("department") + "'";
                                    try (ResultSet rs = st.executeQuery(findDepartmentId)){
                                        while (rs.next()) {
                                            departmentId = rs.getInt("id");
                                        }
                                    }
                                }
                                
                                
                                // вставляе запись в таблицу Подкатегорию Шаблонов
                                String reportSubCategory = "INSERT INTO FISH_SUBCATEGORIES (cod_vdovkin, name) VALUES (?, ?)";
                                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategory)){
                                    //predStat.setObject(1, idCategoryFishes);
                                    predStat.setObject(1, strSplitCategory[1]);
                                    predStat.setObject(2, strSplit[1]);
                                    predStat.execute();
                                }
                                // получаем Id последней вставленной записи
                                int lastSubcategoryId = 0;
                                try(Statement st = connection.createStatement()){
                                    final String findLastId = "SELECT MAX(subcategory_id) FROM FISH_SUBCATEGORIES";
                                    try (ResultSet rs = st.executeQuery(findLastId)){
                                        while (rs.next()) {
                                            lastSubcategoryId = rs.getInt("MAX(subcategory_id)");
                                        }
                                    }
                                }
                                
                                // устанавливаем связь между таблицей Категория и Подкатегория Шаблонов
                                String reportSubCategoryOfCategory = "INSERT INTO FISH_CATEGORY_SUBCATEGORIES (category_id, subcategory_id) VALUES (?, ?)";
                                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategoryOfCategory)){
                                    //predStat.setObject(1, idCategoryFishes);
                                    predStat.setObject(1, fishCategory.getId());
                                    predStat.setObject(2, lastSubcategoryId);
                                    predStat.execute();
                                }
                                // получаем Id последней вставленной записи
                                int lastSubCategoryOfCategoriesId = 0;
                                try(Statement st = connection.createStatement()){
                                    final String findLastId = "SELECT MAX(id) FROM FISH_CATEGORY_SUBCATEGORIES";
                                    try (ResultSet rs = st.executeQuery(findLastId)){
                                        while (rs.next()) {
                                            lastSubCategoryOfCategoriesId = rs.getInt("MAX(id)");
                                        }
                                    }
                                }
                                
                                // устанавливаем связь между таблицей Подразделение и FISH_CATEGORY_SUBCATEGORIES
                                String reportDepartmentSubCategoryOfCategory = "INSERT INTO DEPARTMENT_FISH_CATEGORY_SUBCATEGORIES (category_subcategories_id, department_id) VALUES (?, ?)";
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
