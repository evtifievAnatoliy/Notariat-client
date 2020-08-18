/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class AbstractModalDialog{
    
    private boolean okPressed;
    
    public AbstractModalDialog(Stage primaryStage, String title){
        
        Stage stage = new Stage();
        
        stage.setTitle(title);
        stage.setWidth(400);
        stage.setHeight(200);
        
        
        Button buttonOk = new Button("OK");
        buttonOk.setMinWidth(100);
        buttonOk.setMaxWidth(100);
        buttonOk.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                okPressed = true;
                stage.close();
            }
            
        });
        Button buttonEsc = new Button("Cancel");
        buttonEsc.setMinWidth(100);
        buttonEsc.setMaxWidth(100);
        buttonEsc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                okPressed = false;
                stage.close();
            }
            
        });
        
        HBox buttonPane = new HBox(buttonOk, buttonEsc);
        buttonPane.setAlignment(Pos.CENTER);
        
        BorderPane mainPane = new BorderPane();
        mainPane.setBottom(buttonPane);
        
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
    
    
    public boolean isSuccess(){
        return okPressed;
    }
    
}
