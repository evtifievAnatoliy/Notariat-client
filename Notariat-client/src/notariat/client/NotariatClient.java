/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.GroupLayout;

/**
 *
 * @author eag
 */
public class NotariatClient extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch();
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Нотариат: " + "НК2");
        stage.setWidth(500);
        stage.setHeight(400);
        
        
        Button newBtn = new Button("New Document");
        Group group = new Group(newBtn);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        
        stage.show();
    }
    
}
