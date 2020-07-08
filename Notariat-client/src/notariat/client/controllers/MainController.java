/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

/**
 *
 * @author eag
 */

public class MainController {

    private static MainController instance;
    private Documents documents;
    private Fishes fishes;

    public MainController() {
        
        documents = new Documents();
        fishes = new Fishes();
        
    }
    
    public static MainController getInstance() {
        if (instance == null){
            try {
                instance = new MainController();
            } catch (Exception ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return instance;
    }

    public Documents getDocuments() {
        return documents;
    }

    public Fishes getFishes() {
        return fishes;
    }
    
    
    
}
