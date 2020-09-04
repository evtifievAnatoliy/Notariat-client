/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.models;

/**
 *
 * @author user
 */
public class FishSubCategory {
    private int id;
    private int categoryId;
    private int codVdovkin;
    private String name;

    public FishSubCategory(int categoryId, int codVdovkin, String name) {
        this.categoryId = categoryId;
        this.codVdovkin = codVdovkin;
        this.name = name;
    }
    
    public FishSubCategory(int id, int categoryId, int codVdovkin, String name) {
        this(categoryId, codVdovkin, name);
        this.id = id;
        
    }
    
    
    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getCodVdovkin() {
        return codVdovkin;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    
}
