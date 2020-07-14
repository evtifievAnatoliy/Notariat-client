/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import notariat.client.controllers.MainController;
import notariat.client.models.Fish;

/**
 *
 * @author user
 */
public class SplitPaneListFishAndNewDocument{
    
    private double mainWindowWidth;
    private MainController mainController;
    
    private SplitPane splitPaneListFishesAndNewDocument;
    private ListView<Fish> fishListView;
    private TextArea newDocumentTextArea;
    private MainForm mainForm;
    
    public SplitPaneListFishAndNewDocument(MainForm mainForm, double wight) {
        this.mainWindowWidth = wight;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
    
        ObservableList<Fish> fishesArray = FXCollections.observableArrayList(mainController.getFishes().getFishes());
        fishListView = new ListView<Fish>();
        fishListView.setItems(fishesArray);
        newDocumentTextArea = new TextArea();
        
        splitPaneListFishesAndNewDocument = new SplitPane();
        splitPaneListFishesAndNewDocument.getItems().addAll(fishListView, new BorderPane(newDocumentTextArea));
        
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }

    public SplitPane getSplitPaneListFishesAndNewDocument() {
        return splitPaneListFishesAndNewDocument;
    }
    
    
    public void setSizeOfComponents(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        double fishListViewWight = windowWight/5;
        fishListView.setMinWidth(fishListViewWight);
        fishListView.setMaxWidth(fishListViewWight);
        double textAreaWight = fishListViewWight/2*7;
        newDocumentTextArea.setMinWidth(textAreaWight);
        newDocumentTextArea.setMaxWidth(textAreaWight);
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){
         
         
        //событие при нажатии Esc в newDocumentTextArea
        newDocumentTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    fishListView.requestFocus();
                }
            }
        });
        //----------------------------------------------------------------
         
        // событие при выборе элемента в fishListView
        MultipleSelectionModel<Fish> fishListViewSelectionModel = fishListView.getSelectionModel();
        fishListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >0){ //>1 для двойного нажатия
                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
                    newDocumentTextArea.setText(fishListViewSelectionModel.getSelectedItem().toString());
                    newDocumentTextArea.requestFocus();
                }
            }
        });
        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
                    newDocumentTextArea.setText(fishListViewSelectionModel.getSelectedItem().toString());
                    newDocumentTextArea.requestFocus();
                }
            }
        });
        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }
        });
        //------------------------------------------------------------------
        
     }
    
    
}
