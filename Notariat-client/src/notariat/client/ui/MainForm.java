/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

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
public class MainForm {

    public MainForm(Stage primaryStage) throws Exception {
        
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setTitle("Нотариат: " + Configuration.getInstance().getProperty("department"));
        primaryStage.setWidth(monitorSize.width - 100);
        primaryStage.setHeight(monitorSize.height - 100);
        
        
        Button newBtn = new Button("Новый документ");
        Button exitBtn = new Button("Выход");
        
        Group group = new Group(newBtn, exitBtn);
        
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        
        //stage.setMaximized(true);  //полноэкранный размер
        primaryStage.show();
    }

   
    
}
