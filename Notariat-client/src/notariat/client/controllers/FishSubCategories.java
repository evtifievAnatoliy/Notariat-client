/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
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
}
