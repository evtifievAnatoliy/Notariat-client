/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import java.util.Properties;
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
                final String report = "SELECT * FROM fish_categories";
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
                final String report = "SELECT * FROM fish_subcategories subCategory " +
                                        "INNER JOIN fish_category_subcategories subCategoryOfCategory "
                                            + "on subCategoryOfCategory.subcategory_id = subCategory.subcategory_id " +
                                        "INNER JOIN fish_categories category "
                                            + "on category.category_id = subCategoryOfCategory.category_id " +
                                        "INNER JOIN department_fish_categories dfcs "
                                            + "on dfcs.category_id = category.category_id " +
                                        "INNER JOIN departments department " 
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
    // метод записи Подкатегории шаблонов базу данных MySQL
    public int addFishSubCategoryToMySQL(FishSubCategory fishSubCategory, int fishCategoryId){
          
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                        Configuration.getInstance().getProperty("user.Db"),
                        Configuration.getInstance().getProperty("password.Db"))){
            int lastFishSubCategoryId = 0;
            
            // вставляе запись в таблицу Шаблоны
            String reportFish = "INSERT INTO fish_subcategories (cod_vdovkin, name) VALUES (?, ?)";
            try(PreparedStatement predStat = connection.prepareStatement(reportFish, Statement.RETURN_GENERATED_KEYS)){
                predStat.setObject(1, fishSubCategory.getCodVdovkin());
                predStat.setObject(2, fishSubCategory.getName());
                predStat.executeUpdate();
                // получаем Id последней вставленной записи
                ResultSet rs = predStat.getGeneratedKeys();
                if(rs.next())
                    {
                        lastFishSubCategoryId = rs.getInt(1);
                    }
                }
            
                // устанавливаем связь между таблицей Шаблонов и Подкатегория Шаблонов
                String reportSubCategoryOfCategory = "INSERT INTO fish_category_subcategories (category_id, subcategory_id) VALUES (?, ?)";
                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategoryOfCategory)){
                    predStat.setObject(1, fishCategoryId);
                    predStat.setObject(2, lastFishSubCategoryId);
                    predStat.execute();
                }
                return lastFishSubCategoryId;
        }catch (Exception ex){
                throw new IllegalArgumentException("Error. Ошибка записи Категории шаблонов в MySql. Категория Шаблонов:!!!" + fishSubCategory.getName() + "\n" + ex.getMessage());
        }
    }
    
    // метод чтения Шаблонов. 
    public ArrayList<Fish> readFishes(FishSubCategory fishSubCategory) throws IOException, ParseException{
        
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                    Configuration.getInstance().getProperty("user.Db"), 
                    Configuration.getInstance().getProperty("password.Db"))){
            try(Statement st = connection.createStatement()){
                final String report = "SELECT * FROM fishes fishes " +
                                        "INNER JOIN fish_subcategories_fishes fsf  "
                                            + "on fishes.fish_id = fsf.fish_id " +
                                        "INNER JOIN fish_subcategories fs "
                                            + "on fs.subcategory_id = fsf.subcategory_id " +
                                        "WHERE fs.subcategory_id = " + fishSubCategory.getId();
                try (ResultSet rs = st.executeQuery(report)){
                        // создаем коллекцию Сатегорий Шаблонов
                        ArrayList<Fish> fishes = new ArrayList<Fish>();
                        while (rs.next()) {
                            // пробуем создать объект шаблон и добавить его в коллекцию
                            Fish fish = new Fish(rs.getInt("fish_id"), rs.getString("name"), rs.getString("body"));
                            fishes.add(fish);
                        }
                        return fishes;
                    }
            }
            
        }
        catch(SQLException ex){
            throw new IllegalArgumentException("Error. Ошибка чтения  Шаблонов из базы данных.!!!\n" + ex.getMessage());
        }
    }
    
    public void updateFishInMySQL(Fish fish) throws IOException, SQLException{
            
        // загружаем макросы в MySql
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                Configuration.getInstance().getProperty("user.Db"),
                Configuration.getInstance().getProperty("password.Db"))){
            
            final String reportKeyMacro = "UPDATE fishes SET body = ? WHERE fish_id = ?";
            try(PreparedStatement predStat = connection.prepareStatement(reportKeyMacro)){
                predStat.setObject(1, fish.getFish_body());
                predStat.setObject(2, fish.getKey());
                predStat.executeUpdate();
            }
                
        }
        
    }
    
    // метод записи Шаблона базу данных MySQL
    public int addFishToMySQL(Fish fish, int subCategoryId){
          
        try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                        Configuration.getInstance().getProperty("user.Db"),
                        Configuration.getInstance().getProperty("password.Db"))){
            int lastFishId = 0;
            
            // вставляе запись в таблицу Шаблоны
            String reportFish = "INSERT INTO fishes (name, body) VALUES (?, ?)";
            try(PreparedStatement predStat = connection.prepareStatement(reportFish, Statement.RETURN_GENERATED_KEYS)){
                predStat.setObject(1, fish.getFish_name());
                predStat.setObject(2, fish.getFish_body());
                predStat.executeUpdate();
                // получаем Id последней вставленной записи
                ResultSet rs = predStat.getGeneratedKeys();
                if(rs.next())
                    {
                        lastFishId = rs.getInt(1);
                    }
                }
            
                // устанавливаем связь между таблицей Шаблонов и Подкатегория Шаблонов
                String reportSubCategoryOfCategory = "INSERT INTO fish_subcategories_fishes (subcategory_id, fish_id) VALUES (?, ?)";
                try(PreparedStatement predStat = connection.prepareStatement(reportSubCategoryOfCategory)){
                    predStat.setObject(1, subCategoryId);
                    predStat.setObject(2, lastFishId);
                    predStat.execute();
                }
                return lastFishId;
        }catch (Exception ex){
                throw new IllegalArgumentException("Error. Ошибка записи шаблонов в MySql. Шаблон:!!!" + fish.getFish_name() + "\n" + ex.getMessage());
        }
        
    }

    //-------------------------------------------
    
    
    
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
    
    
    
    
    // методы подключаемые для переноса данных из старой программы 
    // метод чтения Шаблонов из файла . 
    public ArrayList<Fish> readFishesFromFile(FishSubCategory subCategoryFishes, MainController mainController) throws IOException, ParseException{
        
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
                    else{
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
                        throw new IllegalArgumentException("Error. Ошибка построения шаблона.!!!\n" + e.getMessage());
                        }
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
                            }
                            lastIdSubCategoryFishes = Integer.parseInt(strSplitCategory[1]);
                    }
                }
            
            }
    }
    
    // метод записи Шаблоновв базу данных MySQL
    public void writeFishesToMySQL(ArrayList<Fish> fishes, int subCategoryId){
            
            String currentFish = "";
            try(Connection connection = DriverManager.getConnection(Configuration.getInstance().getProperty("url.Db"), 
                            Configuration.getInstance().getProperty("user.Db"),
                            Configuration.getInstance().getProperty("password.Db"))){
                int lastFishId = 0;
                for (Fish fish: fishes){
                    currentFish = fish.getFish_name();
                    // вставляе запись в таблицу Шаблоны
                    String reportFish = "INSERT INTO fishes (name, body) VALUES (?, ?)";
                    try(PreparedStatement predStat = connection.prepareStatement(reportFish, Statement.RETURN_GENERATED_KEYS)){
                        predStat.setObject(1, fish.getFish_name());
                        predStat.setObject(2, fish.getFish_body());
                        predStat.executeUpdate();
                        // получаем Id последней вставленной записи
                        ResultSet rs = predStat.getGeneratedKeys();
                        if(rs.next())
                        {
                            lastFishId = rs.getInt(1);
                        }
                    }
                    // устанавливаем связь между таблицей Шаблонов и Подкатегория Шаблонов
                    String reportSubCategoryOfCategory = "INSERT INTO fish_subcategories_fishes (subcategory_id, fish_id) VALUES (?, ?)";
                    try(PreparedStatement predStat = connection.prepareStatement(reportSubCategoryOfCategory)){
                        predStat.setObject(1, subCategoryId);
                        predStat.setObject(2, lastFishId);
                        predStat.execute();
                    }
                }
            }catch (Exception ex){
                throw new IllegalArgumentException("Error. Ошибка записи шаблонов в MySql. Шаблон:!!!" + currentFish + "\n" + ex.getMessage());
            }
    }
}
