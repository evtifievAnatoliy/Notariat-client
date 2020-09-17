/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import notariat.client.controllers.MainController;
import notariat.client.models.Fish;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author user
 */
public class SplitPaneListFishAndNewDocument{
    
    private double mainWindowWidth;
    private MainController mainController;
    
    private SplitPane splitPaneListFishesAndNewDocument;
    private ListView<FishSubCategory> fishSubCategoriesListView;
    private ListView<Fish> fishListView;
    private MainForm mainForm;
    
    private StackPane listStackPane;
    private DocumentTextArea newDocumentTextArea;
    
    public SplitPaneListFishAndNewDocument(MainForm mainForm, double wight) {
        this.mainWindowWidth = wight;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
    
        // отрисовываем слой  "список Подкаталогов шаблонов"
        ObservableList<FishSubCategory> fishSubCategoriesArray = FXCollections.observableArrayList();
        fishSubCategoriesListView = new ListView<FishSubCategory>();
        fishSubCategoriesListView.setItems(fishSubCategoriesArray);
        // ------------------------------------
        
        // отрисовываем слой  "список шаблонов"
        ObservableList<Fish> fishesArray = FXCollections.observableArrayList();
        fishListView = new ListView<Fish>();
        fishListView.setItems(fishesArray);
        // ------------------------------------
        newDocumentTextArea = new DocumentTextArea(mainForm, mainWindowWidth) {
            @Override
            public void keyEventEscape() {
                getDocumentTextArea().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
                @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ESCAPE){ 
                            getDocumentTextArea().clear();
                            fishListView.requestFocus();
                        }
                    }       
                });
            }
            
        };
        splitPaneListFishesAndNewDocument = new SplitPane();
        listStackPane = new StackPane();
        splitPaneListFishesAndNewDocument.getItems().addAll(listStackPane, new BorderPane(newDocumentTextArea.getDocumentTextArea()));
        
        setStackPane(fishSubCategoriesListView);
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }

    public ListView<FishSubCategory> getFishSubCategoriesListView() {
        return fishSubCategoriesListView;
    }
    

    public SplitPane getSplitPaneListFishesAndNewDocument() {
        return splitPaneListFishesAndNewDocument;
    }
    
    public void setStackPane(Node node) {
            if (listStackPane.getChildren() != null)
                listStackPane.getChildren().clear();
            listStackPane.getChildren().add(node);
    }
    
    public void removeLastStackPane() {
            if (listStackPane.getChildren().size() > 0)
                listStackPane.getChildren().remove(listStackPane.getChildren().size()-1);
            
    }
    
    public void setSizeOfComponents(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        double fishListViewWight = windowWight/5;
        listStackPane.setMinWidth(fishListViewWight);
        listStackPane.setMaxWidth(fishListViewWight);
        fishListView.setMinWidth(fishListViewWight);
        fishListView.setMaxWidth(fishListViewWight);
        double textAreaWight = fishListViewWight/2*7;
        newDocumentTextArea.setSizeOfComponents(textAreaWight);
        newDocumentTextArea.setSizeOfComponents(textAreaWight);
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){
        
        // событие при выборе элемента в fishSubCategoriesListView
        MultipleSelectionModel<FishSubCategory> fishSubCategoriesListViewSelectionModel = fishSubCategoriesListView.getSelectionModel();
        fishSubCategoriesListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
                    fishListView.getItems().clear();
                    try{
                        fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
                    }
                    catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось прочитать шаблоны из базы данных: \n" + e.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    
                    }
                    listStackPane.getChildren().add(fishListView);
                    fishListView.requestFocus();
                }
            }
        });
        fishSubCategoriesListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    fishListView.getItems().clear();
                    try{
                        fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
                    }
                    catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось прочитать шаблоны из базы данных: \n" + e.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    
                    }  
                    listStackPane.getChildren().add(fishListView);
                    fishListView.requestFocus();
                }
            }
        });
        fishSubCategoriesListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }
        });
        //-------------------------------------------------------- 
         
        // событие при выборе элемента в fishListView
        MultipleSelectionModel<Fish> fishListViewSelectionModel = fishListView.getSelectionModel();
        fishListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
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
                if (event.getCode() == KeyCode.ESCAPE){ 
                    removeLastStackPane();
                }
            }
        });
        //------------------------------------------------------------------
        
     }
    
    
}
