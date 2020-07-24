/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.IOException;
import notariat.client.models.CategoryFishes;
import notariat.client.models.SubCategoryFishes;

/**
 *
 * @author eag
 */

public class MainController {

    private static MainController instance;
    private Documents documents;
    private Fishes fishes;
    private CategoriesFishes categoriesFishes;
    private SubCategoriesFishes subCategoriesFishes;
    private FishesReaderWriter fishesReaderWriter;
    
    public MainController() throws IOException {
        
        documents = new Documents();
        
        
        try
        {
            fishesReaderWriter = new FishesReaderWriter();
            categoriesFishes = new CategoriesFishes(fishesReaderWriter.readCategoryFisheses());
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
    
    
    public SubCategoriesFishes getSubCategoriesFishes(CategoryFishes categoryFishes) {
        try
        {
            subCategoriesFishes = new SubCategoriesFishes(fishesReaderWriter.readSubCategoryFisheses(categoryFishes));
            return subCategoriesFishes;
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Ошибка чтения Подкаталогов рыб из базы!!!\n" + ex.getMessage());
        }
        
    }
    
    public Documents getDocuments() {
        return documents;
    }

    public Fishes getFishes(SubCategoryFishes subCategoryFishes) {
        try
        {
            fishes = new Fishes(fishesReaderWriter.readFishes(subCategoryFishes, this));
            return fishes;
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Ошибка чтения рыбы базы!!!\n" + ex.getMessage());
        }
        
    }

    public CategoriesFishes getCategoriesFishes() {
        return categoriesFishes;
    }
    
    public FishesReaderWriter getFishesReaderWriter() {
        return fishesReaderWriter;
    }
    
    
    
    
}
