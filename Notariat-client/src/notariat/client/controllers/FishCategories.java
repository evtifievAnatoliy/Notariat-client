/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import notariat.client.models.FishCategory;


public class FishCategories {
    private ArrayList<FishCategory> fishCategories;

    public FishCategories(ArrayList<FishCategory> fishCategories) {
        this.fishCategories = fishCategories;
    }

    public ArrayList<FishCategory> getFishCategories() {
        return fishCategories;
    }

    public FishCategory findFishCategoryByName (String name){
        for (FishCategory fishCategory : fishCategories){
            if (fishCategory.getName().equals(name))
                return fishCategory;
        }
        return null;
    }
    public FishCategory findFishCategoryById (int id){
        for (FishCategory fishCategory : fishCategories){
            if (fishCategory.getId() == id)
                return fishCategory;
        }
        return null;
    }
}
