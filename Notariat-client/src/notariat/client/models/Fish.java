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
    private String fish_name;
    private String fish_body;
    private int act_code;
    private int atribute;

    public Fish(String fish_name, String fish_body) {
        this.fish_name = fish_name;
        this.fish_body = fish_body;
    }
    
    public Fish(int id, String fish_name, String fish_body) {
        this(fish_name, fish_body);
        this.key = id;
    }

    public String getFish_body() {
        return fish_body;
    }

    public String getFish_name() {
        return fish_name;
    }

    public int getKey() {
        return key;
    }

    
    
    @Override
    public String toString() {
        return fish_name;
    }

    public void setFish_body(String fish_body) {
        this.fish_body = fish_body;
    }
    
    
}
