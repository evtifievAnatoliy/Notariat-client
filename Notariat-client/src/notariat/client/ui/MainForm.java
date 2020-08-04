/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import notariat.client.configuration.Configuration;
import notariat.client.controllers.MainController;
import notariat.client.models.FishCategory;

/**
 *
 * @author eag
 */
public class MainForm {
    
    private MainController mainController;
    private double mainWindowWidth;
    
    private Stage primaryStage;
    private Menu menuNewDocument;
    private Label labelNewDocument;
    private Label labelBaseWorkDay;
    private Menu  menuSettings;
    private MenuItem menuItemExit;
    private StackPane mainStackPane;
    
    private SplitPaneListFishAndNewDocument splitPaneListFishAndNewDocument;
    private WorkDayTableView workDayTableView;
    private DocumentTextArea documentFromBaseTextArea;
    
    public MainForm(Stage primaryStage)throws Exception {
    
        mainController = MainController.getInstance();
        
        this.primaryStage = primaryStage;
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setTitle("Нотариат: " + Configuration.getInstance().getProperty("department"));
        primaryStage.setWidth(monitorSize.width - monitorSize.width/8);
        primaryStage.setHeight(monitorSize.height - monitorSize.height/8);
        primaryStage.initStyle(StageStyle.DECORATED);
        mainWindowWidth = monitorSize.getWidth(); //от ширины разрешения экрана
        //mainWindowWidth = primaryStage.getWidth(); //от ширины окна приложения
        
        
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
//                    // импорт Подкатегорий от Вдовкина
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setContentText(menuItem.getText());
//                    alert.showAndWait();
//                    try {
//                        mainController.getFishesReaderWriter().readFishSubCategoryAndWriteToMySQL(mainController.getFishCategories().findFishCategoryByName(menuItem.getText()));
//                    } catch (Exception ex) {
//                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//                    } 
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

}
