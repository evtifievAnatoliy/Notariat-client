/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import notariat.client.controllers.MainController;
import notariat.client.models.Fish;
import notariat.client.models.FishCategory;
import notariat.client.models.FishSubCategory;
import notariat.client.models.KeyMacro;

/**
 *
 * @author user
 */
public class FishesEditModalDialog  extends AbstractModalDialogWithOneButton{
    
    private double mainWindowWidth;
    private MainController mainController;
    
    private Menu menuNewDocument;
    private SplitPane splitPaneListFishesAndNewDocument;
    private ListView<FishSubCategory> fishSubCategoriesListView;
    private ListView<Fish> fishListView;
    private StackPane listStackPane;
    private DocumentTextArea newDocumentTextArea;
    private MultipleSelectionModel<FishSubCategory> fishSubCategoriesListViewSelectionModel;
    
    private ContextMenu contextMenuFishListView;
    private MenuItem contextMenuAddFishInListView;
    private ContextMenu contextMenuSubCategoriesListView;
    private MenuItem contextMenuAddSubCategoriesListView;
    private FishCategory selectedCategory = null;
    
    Fish choosenFish = null;
    
    public FishesEditModalDialog(Stage primaryStage, String title, double width, double height, MainController mainController) throws IOException {
        super(primaryStage, title, width/4*3, height/4*3);
        this.mainWindowWidth = width/4*3;
        this.mainController = mainController;
        
        mainController = MainController.getInstance();
        
        // отрисовываем меню
        MenuBar menuBar = new MenuBar();
        menuNewDocument = new Menu();
        Label labelNewDocument = new Label("Выберите категорию шаблонов");
               
        menuNewDocument.setGraphic(labelNewDocument);
        setMenuNewDocument(mainController.getFishesReaderWriter().readFishCategories());
        menuBar.getMenus().add(menuNewDocument);
        
        
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
        newDocumentTextArea = new DocumentTextArea(mainWindowWidth) {
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
        
               
        getMainPane().setCenter(splitPaneListFishesAndNewDocument);
        getMainPane().setTop(menuBar);
        
        // добавляем контекстное меню добавить и удалить категории и подкатегории шаблонов
        contextMenuFishListView = new ContextMenu();
        contextMenuAddFishInListView = new MenuItem("Добавить");
        contextMenuFishListView.getItems().addAll(contextMenuAddFishInListView);
        contextMenuSubCategoriesListView = new ContextMenu();
        contextMenuAddSubCategoriesListView = new MenuItem("Добавить");
        contextMenuSubCategoriesListView.getItems().addAll(contextMenuAddSubCategoriesListView);
        
        setStackPane(fishSubCategoriesListView);
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }
    
    public void setMenuNewDocument(ArrayList<FishCategory> categoriesFisheses) {
        for (FishCategory categoryFishes : categoriesFisheses){
            MenuItem menuItem = new MenuItem(categoryFishes.toString());
            this.menuNewDocument.getItems().add(menuItem);
        }
    
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
            listStackPane.requestFocus();
            
    }
    
    public void setFishListView(){
        fishListView.getItems().clear();
        fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
        
    }
    
    public void removeLastStackPane() {
            if (listStackPane.getChildren().size() > 0)
                listStackPane.getChildren().remove(listStackPane.getChildren().size()-1);
            listStackPane.requestFocus();
            
    }
    
    public void setSizeOfComponents(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        double fishListViewWight = windowWight/5;
        listStackPane.setMinWidth(fishListViewWight);
        listStackPane.setMaxWidth(fishListViewWight);
        fishListView.setMinWidth(fishListViewWight);
        fishListView.setMaxWidth(fishListViewWight);
        double textAreaWight = fishListViewWight/2*7;
        newDocumentTextArea.getDocumentTextArea().setMinWidth(textAreaWight);
        newDocumentTextArea.getDocumentTextArea().setMaxWidth(textAreaWight);
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){
        // загрузка меню выберите категорию шаблонов
         for(MenuItem menuItem : menuNewDocument.getItems()){
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectedCategory = mainController.getFishCategories().findFishCategoryByName(menuItem.getText());
                    getFishSubCategoriesListView().getItems().clear();
                    getFishSubCategoriesListView().getItems().addAll(
                            mainController.getFishSubCategories(
                                    selectedCategory).getFishSubCategories());
                    
                    setStackPane(getFishSubCategoriesListView());
                }
            });
        }
        
        
        // событие при выборе элемента в fishSubCategoriesListView
        fishSubCategoriesListViewSelectionModel = fishSubCategoriesListView.getSelectionModel();
        fishSubCategoriesListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
                    fishListView.getItems().clear();
                    fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
                            
                    
                    
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
                    fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
                      
                    listStackPane.getChildren().add(fishListView);
                    fishListView.requestFocus();
                }
            }
        });
        fishSubCategoriesListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    removeLastStackPane();
                }
            }
        });
        fishSubCategoriesListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){ //>1 для двойного нажатия //>0 для одинарного
                    contextMenuSubCategoriesListView.show(fishSubCategoriesListView, event.getScreenX(), event.getScreenY());
                }
            }
        });
        contextMenuAddSubCategoriesListView.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LabelAndTextFieldModalDialog labelAndTextFieldModalDialog = 
                            new LabelAndTextFieldModalDialog(getPrimaryStage(), "Добавить подкатегорию шаблонов", 400, 150, "Введите имя подкатегории:");
                    labelAndTextFieldModalDialog.showAndWait();
                    if(labelAndTextFieldModalDialog.isSuccess()){
                        try{
                            
                            mainController.getFishSubCategories(selectedCategory).addFishSubCategory(
                                    new FishSubCategory(selectedCategory.getId(), 0, labelAndTextFieldModalDialog.getTextField().getText()), 
                                    mainController, selectedCategory);
                            
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, labelAndTextFieldModalDialog.getTextField().getText(), ButtonType.OK);
                            alert.showAndWait();
                        }
                        catch(Exception e){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Добавить подкатегорию не получилось. \n" + e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                        }
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
                    choosenFish = fishListViewSelectionModel.getSelectedItem();
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
                    choosenFish = fishListViewSelectionModel.getSelectedItem();
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
        
        fishListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){ //>1 для двойного нажатия //>0 для одинарного
                    contextMenuFishListView.show(fishListView, event.getScreenX(), event.getScreenY());
                }
            }
        });
        
        contextMenuAddFishInListView.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LabelAndTextFieldModalDialog labelAndTextFieldModalDialog = 
                            new LabelAndTextFieldModalDialog(getPrimaryStage(), "Добавить шаблон", 400, 150, "Введите имя шаблона:");
                    labelAndTextFieldModalDialog.showAndWait();
                    if(labelAndTextFieldModalDialog.isSuccess()){
                        try{
                        mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).addFish(
                                new Fish(labelAndTextFieldModalDialog.getTextField().getText(), "Пустой шаблон"), 
                                mainController, fishSubCategoriesListViewSelectionModel.getSelectedItem());
                        setFishListView();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, labelAndTextFieldModalDialog.getTextField().getText(), ButtonType.OK);
                        alert.showAndWait();
                        }
                        catch(Exception e){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Добавить шаблон не получилось. \n" + e.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                    
                }
        });
        
        //------------------------------------------------------------------
        
        // события при сохранении по F10
        newDocumentTextArea.getDocumentTextArea().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию F10
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F10){ 
                    try{
                        choosenFish.setFish_body(newDocumentTextArea.getDocumentTextArea().getText());
                        mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).updateFish(choosenFish, mainController);
                        setFishListView();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Обновление шаблона - " + choosenFish.getFish_name()+ 
                                " выполнено успешно.", ButtonType.OK);
                        choosenFish = null;
                        alert.showAndWait();
                    }
                    catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Обновление шаблона - " + choosenFish.getFish_name() + 
                                " выполнено с ошибкой. \n" + e.getMessage(), ButtonType.OK);
                        choosenFish = null; 
                        alert.showAndWait();
                    }
                }
            }
        });
        
        
        
     }
     
    
    
    
}
