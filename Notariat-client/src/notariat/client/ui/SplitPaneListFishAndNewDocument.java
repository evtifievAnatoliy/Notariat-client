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
    private MainForm mainForm;
    
    private DocumentTextArea newDocumentTextArea;
    
    public SplitPaneListFishAndNewDocument(MainForm mainForm, double wight) {
        this.mainWindowWidth = wight;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
    
        ObservableList<Fish> fishesArray = FXCollections.observableArrayList(mainController.getFishes().getFishes());
        fishListView = new ListView<Fish>();
        fishListView.setItems(fishesArray);
        newDocumentTextArea = new DocumentTextArea(mainWindowWidth) {
            @Override
            public void keyEventEscape() {
                getDocumentTextArea().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
                @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ESCAPE){ 
                            fishListView.requestFocus();
                        }
                    }       
                });
            }
            
        };
        
        splitPaneListFishesAndNewDocument = new SplitPane();
        splitPaneListFishesAndNewDocument.getItems().addAll(fishListView, new BorderPane(newDocumentTextArea.getDocumentTextArea()));
        
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
        newDocumentTextArea.getDocumentTextArea().setMinWidth(textAreaWight);
        newDocumentTextArea.getDocumentTextArea().setMaxWidth(textAreaWight);
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){
         
        // событие при выборе элемента в fishListView
        MultipleSelectionModel<Fish> fishListViewSelectionModel = fishListView.getSelectionModel();
        fishListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >0){ //>1 для двойного нажатия
                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
                    newDocumentTextArea.getDocumentTextArea().setText(fishListViewSelectionModel.getSelectedItem().getFish_body());
                    newDocumentTextArea.getDocumentTextArea().requestFocus();
                }
            }
        });
        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
                    newDocumentTextArea.getDocumentTextArea().setText(fishListViewSelectionModel.getSelectedItem().getFish_body());
                    newDocumentTextArea.getDocumentTextArea().requestFocus();
                }
            }
        });
        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F10){ 
                    try{
                        mainController.getFishesReaderWriter().writeFishes(mainController.getFishes().getFishes());
                        newDocumentTextArea.getDocumentTextArea().setText(mainController.getFishesReaderWriter().readCategoryFisheses().toString());
                    }
                    catch(Exception exception){
                        newDocumentTextArea.getDocumentTextArea().setText(exception.getMessage());
                    }
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
