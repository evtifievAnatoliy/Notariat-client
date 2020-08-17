/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.IOException;
import notariat.client.models.FishCategory;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author eag
 */

public class MainController {

    private static MainController instance;
    private Documents documents;
    private Fishes fishes;
    private FishCategories fishCategories;
    private FishSubCategories fishSubCategories;
    private FishesReaderWriter fishesReaderWriter;
    private KeyMacros keyMacros;
    private KeyMacrosReaderWriter keyMacrosReaderWriter;
    
    public MainController() throws IOException {
        
        documents = new Documents();
        
        try
        {
            fishesReaderWriter = new FishesReaderWriter();
            fishCategories = new FishCategories(fishesReaderWriter.readFishCategories());
        
            keyMacrosReaderWriter = new KeyMacrosReaderWriter();
            keyMacros = new KeyMacros(keyMacrosReaderWriter.readKeyMacros());
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Соединение с базой не установлено установлено!!!\n" + ex.getMessage());
        }
        
        
    }
    
    public static MainController getInstance() {
        if (instance == null){
            try {
                instance = new MainController();
            } catch (Exception ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return instance;
    }
    
    
    public FishSubCategories getFishSubCategories(FishCategory fishCategory) {
        try
        {
            fishSubCategories = new FishSubCategories(fishesReaderWriter.readFishSubCategories(fishCategory));
            return fishSubCategories;
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Ошибка чтения Подкаталогов рыб из базы!!!\n" + ex.getMessage());
        }
        
    }
    
    public Documents getDocuments() {
        return documents;
    }

    public Fishes getFishes(FishSubCategory fishSubCategory) {
        try
        {
            fishes = new Fishes(fishesReaderWriter.readFishes(fishSubCategory));
            //если загружаем из файла старой программы
            //fishes = new Fishes(fishesReaderWriter.readFishesFromFile(fishSubCategory, this));
            
            return fishes;
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Ошибка чтения рыбы базы!!!\n" + ex.getMessage());
        }
        
    }

    public FishCategories getFishCategories() {
        return fishCategories;
    }
    
    public FishesReaderWriter getFishesReaderWriter() {
        return fishesReaderWriter;
    }

    public KeyMacros getKeyMacros() {
        return keyMacros;
    }

    public KeyMacrosReaderWriter getKeyMacrosReaderWriter() {
        return keyMacrosReaderWriter;
    }
    
    
    
    
    
}
