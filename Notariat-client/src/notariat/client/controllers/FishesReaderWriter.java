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
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.SortedMap;
import notariat.client.configuration.Configuration;
import notariat.client.models.Fish;

/**
 *
 * @author eag
 */
public class FishesReaderWriter {
    
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
    
    
    // метод чтения из файла. На входе путь записи и  коллекция Товаров
    public ArrayList<Fish> readFishes() throws IOException, ParseException{
        
        /*
        ArrayList<Fish> fishes = new ArrayList<Fish>();
        fishes.add(new Fish("Доверенность на распоряжение счетом"));
        fishes.add(new Fish("Доверенность на распоряжение счетом (Общая)"));
        fishes.add(new Fish("Доверенность на распоряжение счетом (Сбербанк)"));
        fishes.add(new Fish("Доверенность на распоряжение вкладом"));
        fishes.add(new Fish("Доверенность на распоряжение вкладом (пенсия)"));
        fishes.add(new Fish("Доверенность на распоряжение картой"));
        fishes.add(new Fish("Доверенность на получение з/п"));
        fishes.add(new Fish("Доверенность на ведение дел в суде"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (Арбитраж)"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (общая)"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (Уголовные дела)"));
        fishes.add(new Fish("Доверенность на получении пособия"));
        fishes.add(new Fish("Доверенность (образцы)"));
        
        */
        
        
        File file = new File(Configuration.getInstance().getProperty("fishesFromVdovkin.Path"));
        if(!file.exists()){
            ArrayList<Fish> fishes = new ArrayList<Fish>();
            return fishes;
        }
        // создаем коллекцию шаблонов
        ArrayList<Fish> fishes = new ArrayList<Fish>();
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String str;
                SortedMap<String,Charset> charsets = Charset.availableCharsets();
                while ((str = br.readLine()) != null) {
                    String[] strSplit=str.split(";");
                    // пробуем создать объект товар и добавить его в коллекцию
                    StringBuilder stringBuilder = new StringBuilder();
                    File fileFish = new File("D:\\Java\\NK2FISH\\" + strSplit[3].replaceAll(" ","") + ".fis");
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
                    Fish fish = new Fish(strSplit[2], stringBuilder.toString());
                    if (strSplit[0].replaceAll(" ", "").equals("13,3"))
                        fishes.add(fish);
            }
            return fishes;
        }
        //return fishes;
    }
    
}
