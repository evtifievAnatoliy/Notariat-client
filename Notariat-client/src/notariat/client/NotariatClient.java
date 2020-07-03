/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import notariat.client.configuration.Configuration;

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
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setTitle("Нотариат: " + Configuration.getInstance().getProperty("department"));
        stage.setWidth(monitorSize.width - 100);
        stage.setHeight(monitorSize.height - 100);
        
        
        Button newBtn = new Button("Новый документ");
        Button exitBtn = new Button("Выход");
        
        Group group = new Group(newBtn, exitBtn);
        
        Scene scene = new Scene(group);
        stage.setScene(scene);
        
        //stage.setMaximized(true);  //полноэкранный размер
        stage.show();
        

    }
    
}
