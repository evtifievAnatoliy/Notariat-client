/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client;
import javafx.application.Application;
import javafx.stage.Stage;
import notariat.client.ui.MainForm;

/**
 *
 * @author eag
 */
public class NotariatClient extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            MainForm mainForm = new MainForm(primaryStage);
        }
        catch(Exception ex){
            // запись в лог
        }
    }

    
}
