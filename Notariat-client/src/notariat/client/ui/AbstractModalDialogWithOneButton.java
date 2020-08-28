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
public class AbstractModalDialogWithOneButton{
    
    private boolean okPressed;
    private BorderPane mainPane;
    private Stage stage;
    private Stage primaryStage;
    
    public AbstractModalDialogWithOneButton(Stage primaryStage, String title, double WindowWidth, double WindowHeight){
        
        this.primaryStage = primaryStage;
        stage = new Stage();
        
        stage.setTitle(title);
        stage.setWidth(WindowWidth);
        stage.setHeight(WindowHeight);
        
        
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
               
        HBox buttonPane = new HBox(buttonOk);
        buttonPane.setAlignment(Pos.CENTER);
        
        mainPane = new BorderPane();
        mainPane.setBottom(buttonPane);
        
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        
        

    }
    
    public void showAndWait(){
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    public boolean isSuccess(){
        return okPressed;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
}
