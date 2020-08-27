/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import notariat.client.configuration.Configuration;
import notariat.client.controllers.MainController;
import notariat.client.models.FishCategory;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author eag
 */
public class MainForm {
    
    private MainController mainController;
    private double mainWindowWidth;
    private double mainWindowHeight;
    
    private Stage primaryStage;
    private Menu menuNewDocument;
    private Label labelNewDocument;
    private Label labelBaseWorkDay;
    private Menu  menuSettings;
    private MenuItem menuItemLoadFishes;
    private MenuItem menuItemLoadKeyMacros;
    private MenuItem menuItemSettingsKeyMacros;
    private MenuItem menuItemExit;
    private StackPane mainStackPane;
    
    private SplitPaneListFishAndNewDocument splitPaneListFishAndNewDocument;
    private WorkDayTableView workDayTableView;
    private DocumentTextArea documentFromBaseTextArea;
    private TextArea informationTextArea = new TextArea();
    
    public MainForm(Stage primaryStage)throws Exception {
    
        mainController = MainController.getInstance();
        
        this.primaryStage = primaryStage;
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setTitle("Нотариат: " + Configuration.getInstance().getProperty("department"));
        primaryStage.setWidth(monitorSize.width - monitorSize.width/8);
        primaryStage.setHeight(monitorSize.height - monitorSize.height/8);
        primaryStage.initStyle(StageStyle.DECORATED);
        mainWindowWidth = monitorSize.getWidth(); //от ширины разрешения экрана
        mainWindowHeight = primaryStage.getHeight(); //от ширины окна приложения
        
        
        // отрисовываем меню
        MenuBar leftMenuBar = new MenuBar();
        menuNewDocument = new Menu();
        labelNewDocument = new Label("Новый документ (F3)");
        menuNewDocument.setGraphic(labelNewDocument);
        setMenuNewDocument(mainController.getFishesReaderWriter().readFishCategories());
        labelBaseWorkDay = new Label("База рабочего дня (Alt-F9)");
        Menu menuBaseWorkDay = new Menu();
        menuBaseWorkDay.setGraphic(labelBaseWorkDay);
        leftMenuBar.getMenus().add(menuNewDocument);
        leftMenuBar.getMenus().add(menuBaseWorkDay);
        
        MenuBar rightMenuBar = new MenuBar();
        menuSettings = new Menu("Настройки");
        menuItemLoadFishes = new MenuItem("Загрузить шаблоны");
        menuSettings.getItems().add(menuItemLoadFishes);
        menuItemLoadKeyMacros = new MenuItem("Загрузить макросы");
        menuSettings.getItems().add(menuItemLoadKeyMacros);
        menuItemSettingsKeyMacros = new MenuItem("Настроить макросы");
        menuSettings.getItems().add(menuItemSettingsKeyMacros);
        
        
        Menu menuExit = new Menu("Выход");
        menuItemExit = new MenuItem("Выход");
        menuExit.getItems().add(menuItemExit);
        rightMenuBar.getMenus().add(menuSettings);
        rightMenuBar.getMenus().add(menuExit);
        
        Region spacerBetweenLeftMenuBarAndRightMenuBar = new Region();
        spacerBetweenLeftMenuBarAndRightMenuBar.getStyleClass().add("menu-bar");
        HBox.setHgrow(spacerBetweenLeftMenuBarAndRightMenuBar, Priority.ALWAYS);
        
        HBox controlPane = new HBox(leftMenuBar, spacerBetweenLeftMenuBarAndRightMenuBar,  rightMenuBar);
        
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(controlPane);
        
        
        // -------------------------------------------
        
        // отрисовываем элементы компановки StackPane. Окна будут находится на разных слоях
                
        // отрисовываем слой Новый документ
        splitPaneListFishAndNewDocument = new SplitPaneListFishAndNewDocument(this, mainWindowWidth);
        
        // отрисовываем слой База рабочего дня 
        workDayTableView = new WorkDayTableView(this, mainWindowWidth);
        
        // отрисовываем слой documentFromBaseTextArea
        documentFromBaseTextArea = new DocumentTextArea(this, mainWindowWidth);
        
        mainStackPane = new StackPane();
        //----------------------------------------------
        
        mainPane.setCenter(mainStackPane);
        
        
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        
        setSizeOfComponentsInMainStage(mainWindowWidth);
        initializationOfAllActionListeners();
        primaryStage.setMaximized(true);  //полноэкранный размер
        
        primaryStage.show();
    }

    public StackPane getMainStackPane() {
        return mainStackPane;
    }

    public DocumentTextArea getDocumentFromBaseTextArea() {
        return documentFromBaseTextArea;
    }

    public void setMenuNewDocument(ArrayList<FishCategory> categoriesFisheses) {
        for (FishCategory categoryFishes : categoriesFisheses){
            MenuItem menuItem = new MenuItem(categoryFishes.toString());
            this.menuNewDocument.getItems().add(menuItem);
        }
    
    }
    
    
    
    private void setSizeOfComponentsInMainStage(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        splitPaneListFishAndNewDocument.setSizeOfComponents(windowWight);
        
        // устанавливаем размеры компонентов слоя documentFromBaseTextArea
        documentFromBaseTextArea.setSizeOfComponents(windowWight);
        
        // устанавливаем размеры компонентов слоя  workDayTableView
        workDayTableView.setSizeOfComponents(windowWight);
        
    }
    
    
    private void initializationOfAllActionListeners(){
        
        // событие по нажатию пункта меню "Выход" 
        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event) {
              System.exit(0);
            }
        });
        
        // событие по нажатию пункта меню "Загрузить Шаблоны в настройках" 
        menuItemLoadFishes.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event) {
                informationTextArea.clear();
                informationTextArea.setText("Загрузка шаблонов началась: \n");
                setStackPane(informationTextArea);
                try{
                    loadFishSubCategoriesFromVdovkinToMySql(mainController.getFishCategories().getFishCategories());
                    Alert alert = new Alert(Alert.AlertType.NONE, "Загрузка шаблонов закончена.", ButtonType.OK);
                    alert.showAndWait();
                }
                catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Загрузка шаблонов выполнена с ошибкой." + ex.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        // событие по нажатию пункта меню "Загрузить макросы в настройках" 
        menuItemLoadKeyMacros.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event) {
                try{
                    mainController.getKeyMacrosReaderWriter().readKeyMacrosFromFileAndWriteToMySQL();
                    Alert alert = new Alert(Alert.AlertType.NONE, "Загрузка макросов закончена.", ButtonType.OK);
                    alert.showAndWait();
                }
                catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Загрузка макросов выполнена с ошибкой." + ex.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        // событие по нажатию пункта меню "Настроить макросы в настройках" 
        menuItemSettingsKeyMacros.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event) {
                try{
                                       
                    KeyMacrosEditModalDialog keyMacrosEditModalDialog = new  KeyMacrosEditModalDialog(primaryStage ,"Настройка макросов", 
                            mainWindowWidth, mainWindowHeight, mainController);
                    keyMacrosEditModalDialog.showAndWait();
                    /*if (abstractModalDialog.isSuccess()){
                        Alert alert = new Alert(Alert.AlertType.NONE, "OK", ButtonType.OK);
                        alert.showAndWait();
                    }
                    if (!abstractModalDialog.isSuccess()){
                        Alert alert = new Alert(Alert.AlertType.NONE, "ESC", ButtonType.OK);
                        alert.showAndWait();
                    }*/
                        
                }
                catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Загрузка макросов выполнена с ошибкой." + ex.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        
        // событие по нажатию Esc "Информационного текстового поля" 
        informationTextArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){
                    removeLastStackPane();
                }
            }
        });
        
        
        
        // события при нажатии меню "Новый документ"
        labelNewDocument.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                menuNewDocument.show();
            }
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F3 && event.getSource() == primaryStage){
                    menuNewDocument.show();
                }
            }
        });
        for(MenuItem menuItem : menuNewDocument.getItems()){
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    splitPaneListFishAndNewDocument.getFishSubCategoriesListView().getItems().clear();
                    splitPaneListFishAndNewDocument.getFishSubCategoriesListView().getItems().addAll(
                            mainController.getFishSubCategories(
                                    mainController.getFishCategories().findFishCategoryByName(menuItem.getText())).getFishSubCategories());
                    
                    setStackPane(splitPaneListFishAndNewDocument.getSplitPaneListFishesAndNewDocument());
                }
            });
        }
        //----------------------------------------------------------------
        
        // события при нажатии меню "База рабочего дня"
        labelBaseWorkDay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStackPane(workDayTableView.getWorkDayTableView());
            }
        });
        KeyCodeCombination f9AltCodeCombination = new KeyCodeCombination(KeyCode.F9, KeyCombination.ALT_DOWN);
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (f9AltCodeCombination.match(event) && event.getSource() == primaryStage){
                    setStackPane(workDayTableView.getWorkDayTableView());
            }}
        });
        //------------------------------------------------------------------
        
        // событие при изменении размеров главного окна
        primaryStage.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setSizeOfComponentsInMainStage(newValue.doubleValue());
                
            }
            
        });
        //------------------------------------------------------------------
    }
    
    public void setStackPane(Node node) {
            if (mainStackPane.getChildren() != null)
                mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(node);
    }
    
    public void removeLastStackPane() {
            if (mainStackPane.getChildren().size() > 0)
                mainStackPane.getChildren().remove(mainStackPane.getChildren().size()-1);
            
    }
    
    //метод загрузки шадлонов и рыб от Вдовкина в MySql
    private void loadFishSubCategoriesFromVdovkinToMySql(ArrayList<FishCategory> fishCategories) {
        for (FishCategory fishCategory : fishCategories){
            try {
                mainController.getFishesReaderWriter().readFishSubCategoryAndWriteToMySQL(fishCategory);
                informationTextArea.appendText(fishCategory.getName() + "-OK \n");
                for(FishSubCategory fishSubCategory: mainController.getFishSubCategories(fishCategory).getFishSubCategories()){
                    mainController.getFishesReaderWriter().writeFishesToMySQL(
                                mainController.getFishesReaderWriter().readFishesFromFile(fishSubCategory, mainController),
                            fishSubCategory.getId());
                informationTextArea.appendText(fishSubCategory.getName() + "-OK \n");
                }
            } catch (Exception ex) {
                informationTextArea.appendText(fishCategory.getName() + "-Error!!! \n" + ex.getMessage() + "\n");
            } 
        }
    
    }
}
