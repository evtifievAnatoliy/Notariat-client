/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import notariat.client.models.Fish;

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
}
