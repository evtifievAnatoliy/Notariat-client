/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.io.IOException;

/**
 *
 * @author eag
 */

public class MainController {

    private static MainController instance;
    private Documents documents;
    private Fishes fishes;
    
    private FishesReaderWriter fishesReaderWriter;
    
    public MainController() throws IOException {
        
        documents = new Documents();
        
        try
        {
            fishesReaderWriter = new FishesReaderWriter();
            fishes = new Fishes(fishesReaderWriter.readFishes());
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error. Соединение с базой не установлено установлено!!!\n" + ex.getMessage());
        }
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

    public FishesReaderWriter getFishesReaderWriter() {
        return fishesReaderWriter;
    }
    
    
    
}
