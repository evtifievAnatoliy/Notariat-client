/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import notariat.client.models.KeyMacro;

/**
 *
 * @author eag
 */
public class KeyMacros {
    
    private ArrayList<KeyMacro> keyMacros;

    public KeyMacros(ArrayList<KeyMacro> keyMacros) {
        this.keyMacros = keyMacros;
    }

    public ArrayList<KeyMacro> getKeyMacros() {
        return keyMacros;
    }
    
    

}
