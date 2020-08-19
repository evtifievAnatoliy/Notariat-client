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
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
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
public class KeyMacrosEditModalDialog  extends AbstractModalDialogWithTwoButtons{
    
    private double mainWindowWidth;
    private MainController mainController;
    
    private SplitPane splitPaneKeyMacrosAndKeyBody;
    private ListView<KeyMacro> keyMakrosListView;
    
    private TextArea keyMacroBodyTextArea;
    
    public KeyMacrosEditModalDialog(Stage primaryStage, String title, double width, double height, MainController mainController) {
        super(primaryStage, title, width/2, height/2);
        this.mainWindowWidth = width/2;
        this.mainController = mainController;
        
        
        mainController = MainController.getInstance();
    
        // отрисовываем слой  "список макросов"
        ObservableList<KeyMacro> keyMacrosArray = FXCollections.observableArrayList();
        keyMakrosListView = new ListView<KeyMacro>();
        keyMakrosListView.setItems(keyMacrosArray);
        // ------------------------------------
        
        keyMacroBodyTextArea = new TextArea();
        
            
        splitPaneKeyMacrosAndKeyBody = new SplitPane();
        splitPaneKeyMacrosAndKeyBody.getItems().addAll(keyMakrosListView, new BorderPane(keyMacroBodyTextArea));
        
        mainPane.setCenter(splitPaneKeyMacrosAndKeyBody);
        
        setKeyMakrosListView();
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
        keyMakrosListView.requestFocus();
    }

    public ListView<KeyMacro> getKeyMakrosListView() {
        return keyMakrosListView;
    }

    public SplitPane getSplitPaneKeyMacrosAndKeyBody() {
        return splitPaneKeyMacrosAndKeyBody;
    }

    public void setKeyMakrosListView(){
        keyMakrosListView.getItems().clear();
        Collections.sort(mainController.getKeyMacros().getKeyMacros(), KeyMacro.NameComparator);
        keyMakrosListView.getItems().addAll(mainController.getKeyMacros().getKeyMacros());
    }
    
    public void setSizeOfComponents(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        double listViewWight = windowWight/5;
        keyMakrosListView.setMinWidth(listViewWight);
        keyMakrosListView.setMaxWidth(listViewWight);
        double textAreaWight = listViewWight*4;
        keyMacroBodyTextArea.setMinWidth(textAreaWight);
        keyMacroBodyTextArea.setMaxWidth(textAreaWight);
        splitPaneKeyMacrosAndKeyBody.setDividerPositions(listViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){

         // событие при выборе элемента в keyMakrosListView
        MultipleSelectionModel<KeyMacro> keyMakrosListViewSelectionModel = keyMakrosListView.getSelectionModel();
        keyMakrosListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
                    keyMacroBodyTextArea.setText(keyMakrosListViewSelectionModel.getSelectedItem().getMacro_body());
                    keyMacroBodyTextArea.requestFocus();
                }
            }
        });
        
        keyMakrosListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    keyMacroBodyTextArea.setText(keyMakrosListViewSelectionModel.getSelectedItem().getMacro_body());
                    keyMacroBodyTextArea.requestFocus();
                }
            }
        });
        
        //------------------------------------------------------------------
    
       
        keyMacroBodyTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    keyMakrosListView.requestFocus();
                }
            }
        });
    
     }
    
    
}
