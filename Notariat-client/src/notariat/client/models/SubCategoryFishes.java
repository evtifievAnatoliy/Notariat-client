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
public class SubCategoryFishes {
    private int id;
    private int codVdovkin;
    private String name;

    public SubCategoryFishes(int id, int codVdovkin, String name) {
    
        this.id = id;
        this.codVdovkin = codVdovkin;
        this.name = name;
    }

    public int getId() {
        return id;
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
