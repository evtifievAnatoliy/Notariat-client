/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import notariat.client.models.FishCategory;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author user
 */
public class FishSubCategories {
    private ArrayList<FishSubCategory> fishSubCategories;

    public FishSubCategories(ArrayList<FishSubCategory> fishSubCategories) {
        this.fishSubCategories = fishSubCategories;
    }

    public ArrayList<FishSubCategory> getFishSubCategories() {
        return fishSubCategories;
    }
    
    public void addFishSubCategory(FishSubCategory addFishSubCategory, MainController mainController, FishCategory fishCategory) throws SQLException, IOException{
        int id = mainController.getFishesReaderWriter().addFishSubCategoryToMySQL(addFishSubCategory, fishCategory.getId());
        getFishSubCategories().add(new FishSubCategory(id, addFishSubCategory.getCategoryId(), addFishSubCategory.getCodVdovkin(), addFishSubCategory.getName()));
            
    }
}
