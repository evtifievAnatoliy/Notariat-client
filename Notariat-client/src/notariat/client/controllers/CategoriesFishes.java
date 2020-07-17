/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import notariat.client.models.CategoryFishes;


public class CategoriesFishes {
    private ArrayList<CategoryFishes> categoriesFisheses;

    public CategoriesFishes(ArrayList<CategoryFishes> categoriesFisheses) {
        this.categoriesFisheses = categoriesFisheses;
    }

    public ArrayList<CategoryFishes> getCategoriesFisheses() {
        return categoriesFisheses;
    }

    public CategoryFishes findCategoryFishes (String name){
        for (CategoryFishes categoryFishes : categoriesFisheses){
            if (categoryFishes.getName().equals(name))
                return categoryFishes;
        }
        return null;
    }
}
