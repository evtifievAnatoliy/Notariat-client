/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import notariat.client.controllers.MainController;
import notariat.client.models.KeyMacro;

/**
 *
 * @author user
 */
public class LabelAndTextFieldModalDialog  extends AbstractModalDialogWithTwoButtons{
    
    private TextField textField;
    
    KeyMacro choosenKeyMacro = null;
    
    public LabelAndTextFieldModalDialog(Stage primaryStage, String title, double width, double height, String labelStr) {
        super(primaryStage, title, width, height);
        
    
        textField = new TextField();
        Label lbl = new Label("\n" + labelStr);
       
        getMainPane().setCenter(new BorderPane(textField));
        getMainPane().setTop(new BorderPane(lbl));
        
    }

    public TextField getTextField() {
        return textField;
    }

    
    
}
