/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client;
import javafx.application.Application;
import javafx.stage.Stage;
import notariat.client.uiSwing.MainForm;
/**
 *
 * @author eag
 */
public class NotariatClientSwing{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            MainForm mainForm = new MainForm();
        }
        catch(Exception e){
            // запись в лог
            
        }
    }

    
    
}
