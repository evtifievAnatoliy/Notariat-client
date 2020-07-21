/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import notariat.client.models.SubCategoryFishes;

/**
 *
 * @author user
 */
public class SubCategoriesFishes {
    private ArrayList<SubCategoryFishes> subCategoriesFisheses;

    public SubCategoriesFishes(ArrayList<SubCategoryFishes> subCategoriesFisheses) {
        this.subCategoriesFisheses = subCategoriesFisheses;
    }

    public ArrayList<SubCategoryFishes> getSubCategoriesFisheses() {
        return subCategoriesFisheses;
    }
}
