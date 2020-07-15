/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.models;

/**
 *
 * @author eag
 */
public class Fish {
    private int key;
    private String catalog_name;
    private String fish_name;
    private String fish_body;
    private String act_code;
    private String atribute;

    public Fish(String fish_name, String fish_body) {
        this.fish_name = fish_name;
        this.fish_body = fish_body;
    }

    public String getFish_body() {
        return fish_body;
    }

    public String getFish_name() {
        return fish_name;
    }

    @Override
    public String toString() {
        return fish_name;
    }
    
    
}
