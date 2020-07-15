/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.SortedMap;
import notariat.client.configuration.Configuration;
import notariat.client.models.Fish;

/**
 *
 * @author eag
 */
public class FishesReaderWriter {
    /*
    // метод записи в файл. На входе путь записи и  коллекция Товаров
    public void writeItems(ArrayList<Item> items, Item newItem, ArrayList<Item> updateItems) throws IOException{
        	
        if (items !=null) // проверяем на наличие элементов в коллекции
        { 
            // пробуем записать в файл
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Configuration.getInstance().getProperty("items.Path")))){
                for (Item item : items)
                {
                    bufferedWriter.write(item.toString() + "\n");
                }       
            }
        }
        else
            throw new IllegalArgumentException("Error. Товары в файл не записаны, т.к. список товаров пуст. ");
    }
    */
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
                    File fileFish = new File("I:\\Notariat\\two\\NFISHNEW\\" + strSplit[3] + ".fis");
                    if(!fileFish.exists()){
                        stringBuilder.append("Шаблон пуст");
                        //break;
                    }
                    try(BufferedReader brFish = new BufferedReader(new FileReader(fileFish))){
                        String strFish;
                        while((strFish = brFish.readLine()) != null){
                            
                            byte[] bytes = strFish.getBytes(Charset.forName("CP1251"));
                            String strFishIncoding = new String(bytes, "UTF-8");
                            stringBuilder.append(strFishIncoding);
                        }
                    }
                    catch(Exception e){
                        
                    }
                    Fish fish = new Fish(strSplit[2], stringBuilder.toString());
                    if (strSplit[0].replaceAll(" ", "").equals("1,1"))
                        fishes.add(fish);
            }
            return fishes;
        }
        //return fishes;
    }
    
}
