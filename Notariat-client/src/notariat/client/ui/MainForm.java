/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import notariat.client.configuration.Configuration;
import notariat.client.controllers.MainController;

/**
 *
 * @author eag
 */
public class MainForm {
    
    MainController mainController;
    
    private Stage primaryStage;
    private Label labelNewDocument;
    private Label labelBaseWorkDay;
    private Menu  menuSettings;
    private MenuItem menuItemExit;
    private StackPane mainStackPane;
    private SplitPane splitPaneListFishesAndNewDocument;
    private ListView<String> fishListView;
    private TextArea newDocumentTextArea;
    
    public MainForm(Stage primaryStage)throws Exception {
        
        mainController = MainController.getInstance();
        
        this.primaryStage = primaryStage;
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setTitle("Нотариат: " + Configuration.getInstance().getProperty("department"));
        primaryStage.setWidth(monitorSize.width - monitorSize.width/2);
        primaryStage.setHeight(monitorSize.height - monitorSize.height/2);
        primaryStage.initStyle(StageStyle.DECORATED);
        
        // отрисовываем меню
        MenuBar leftMenuBar = new MenuBar();
        Menu menuNewDocument = new Menu();
        labelNewDocument = new Label("Новый документ (F3)");
        menuNewDocument.setGraphic(labelNewDocument);
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
        ObservableList<String> fishesArray = FXCollections.observableArrayList("Доверенность на распоряжение счетом", "Доверенность на распоряжение счетом (Общая)", 
                                                        "Доверенность на распоряжение счетом (Сбербанк)", "Доверенность на распоряжение вкладом", "Доверенность на распоряжение вкладом (пенсия)",
                                                        "Доверенность на распоряжение картой", "Доверенность на получение з/п", 
                                                        "Доверенность на ведение дел в суде", "Доверенность на ведение дел в суде (Арбитраж)", "Доверенность на ведение дел в суде (общая)", 
                                                        "Доверенность на ведение дел в суде (Уголовные дела)", "Доверенность на получении пособия", "Доверенность (образцы)");
        fishListView = new ListView<String>();
        fishListView.setItems(fishesArray);
        double fishListViewWight = monitorSize.getWidth()/5;
        fishListView.setMinWidth(fishListViewWight);
        fishListView.setMaxWidth(fishListViewWight);
        
        newDocumentTextArea = new TextArea();
        newDocumentTextArea.setMinWidth(fishListViewWight/2*7);
        newDocumentTextArea.setMaxWidth(fishListViewWight/2*7);
        
        splitPaneListFishesAndNewDocument = new SplitPane();
        splitPaneListFishesAndNewDocument.getItems().addAll(fishListView, new BorderPane(newDocumentTextArea));
        splitPaneListFishesAndNewDocument.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight());
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //----------------------------------------------

        mainStackPane = new StackPane();
        //mainStackPane.getChildren().add(splitPaneListFishesAndNewDocument);
        
        
        //----------------------------------------------
        mainPane.setCenter(mainStackPane);
        
        
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        
        initializationOfAllActionListeners();
        
        primaryStage.setMaximized(true);  //полноэкранный размер
        primaryStage.show();
    }
    
    private void initializationOfAllActionListeners(){
        
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
                setStackPane(splitPaneListFishesAndNewDocument);
            }
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F3 && event.getSource() == primaryStage){
                    setStackPane(splitPaneListFishesAndNewDocument);
            }}
        });
        //----------------------------------------------------------------
        
        // события при нажатии меню "База рабочего дня"
        labelBaseWorkDay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStackPane(newDocumentTextArea);
            }
        });
        KeyCodeCombination f9AltCodeCombination = new KeyCodeCombination(KeyCode.F9, KeyCombination.ALT_DOWN);
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (f9AltCodeCombination.match(event) && event.getSource() == primaryStage){
                    setStackPane(newDocumentTextArea);
            }}
        });
        //------------------------------------------------------------------
        
        // событие при выборе элемента в fishListView
        MultipleSelectionModel<String> langsSelectionModel = fishListView.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){
            
            @Override
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue){
                 
                // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
                newDocumentTextArea.setText(newValue);
            }
        });
        //------------------------------------------------------------------
        
    }
    
    public void setStackPane(Node pane) {
            if (mainStackPane.getChildren() != null)
                mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(pane);
    }

    
}
