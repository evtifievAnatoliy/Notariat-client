/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import notariat.client.models.Fish;
import notariat.client.models.FishSubCategory;
import notariat.client.models.KeyMacro;

/**
 *
 * @author eag
 */
public class Fishes {
    private ArrayList<Fish> fishes;

    public Fishes(ArrayList<Fish> fishes) {
        this.fishes = fishes;
        
    }

    public ArrayList<Fish> getFishes() {
        return fishes;
    }
    
    public void updateFish(Fish updateFish, MainController mainController) throws SQLException, IOException{
        for (Fish fish: fishes){
            if (fish.getKey()== updateFish.getKey()){
                fish.setFish_body(updateFish.getFish_body());
                mainController.getFishesReaderWriter().updateFishInMySQL(fish);
            }
        }
            
    }
    
    public void addFish(Fish addFish, MainController mainController, FishSubCategory fishSubCategory) throws SQLException, IOException{
        int id = mainController.getFishesReaderWriter().addFishToMySQL(addFish, fishSubCategory.getId());
        getFishes().add(new Fish(id, addFish.getFish_name(), addFish.getFish_body()));
            
    }
}
