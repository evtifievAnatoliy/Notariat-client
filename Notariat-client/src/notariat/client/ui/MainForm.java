/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import notariat.client.models.Document;
import notariat.client.models.Fish;

/**
 *
 * @author eag
 */
public class MainForm {
    
    MainController mainController;
    double mainWindowWidth;
    
    private Stage primaryStage;
    private Label labelNewDocument;
    private Label labelBaseWorkDay;
    private Menu  menuSettings;
    private MenuItem menuItemExit;
    private StackPane mainStackPane;
    private SplitPane splitPaneListFishesAndNewDocument;
    private ListView<Fish> fishListView;
    private TextArea newDocumentTextArea;
    private TableView<Document> workDayTableView;
    private TableColumn<Document, LocalDate> dateColumn;
    private TableColumn<Document, LocalDate> nameDocumentColumn;
    private TableColumn<Document, LocalDate> personColumn;
    private TableColumn<Document, LocalDate> mashColumn;
    private TableColumn<Document, LocalTime> timeColumn;
    private TextArea documentFromBaseTextArea;
    
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
        ObservableList<Fish> fishesArray = FXCollections.observableArrayList(mainController.getFishes().getFishes());
        fishListView = new ListView<Fish>();
        fishListView.setItems(fishesArray);
        newDocumentTextArea = new TextArea();
        
        splitPaneListFishesAndNewDocument = new SplitPane();
        splitPaneListFishesAndNewDocument.getItems().addAll(fishListView, new BorderPane(newDocumentTextArea));
        splitPaneListFishesAndNewDocument.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight());
        
        //----------------------------------------------
        
        // отрисовываем слой База рабочего дня 
        ObservableList<Document> documents = FXCollections.observableArrayList(mainController.getDocuments().getDocuments());
        
        workDayTableView = new TableView<Document>(documents);
        workDayTableView.setEditable(false);
        
        
        // столбец для вывода
        dateColumn = new TableColumn<Document, LocalDate>("Дата");
        // определяем фабрику для столбца с привязкой к свойству
        dateColumn.setCellValueFactory(new PropertyValueFactory<Document, LocalDate>("DOC_DATE"));
        workDayTableView.getColumns().add(dateColumn);
        
        nameDocumentColumn = new TableColumn<Document, LocalDate>("Документ");
        nameDocumentColumn.setCellValueFactory(new PropertyValueFactory<Document, LocalDate>("docName"));
        workDayTableView.getColumns().add(nameDocumentColumn);
        
        personColumn = new TableColumn<Document, LocalDate>("ФИО клиентов");
        personColumn.setCellValueFactory(new PropertyValueFactory<Document, LocalDate>("person"));
        workDayTableView.getColumns().add(personColumn);
        
        mashColumn = new TableColumn<Document, LocalDate>("M");
        mashColumn.setCellValueFactory(new PropertyValueFactory<Document, LocalDate>("docMash"));
        workDayTableView.getColumns().add(mashColumn);
        
        timeColumn = new TableColumn<Document, LocalTime>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Document, LocalTime>("DOC_TIME"));
        workDayTableView.getColumns().add(timeColumn);
        //----------------------------------------------
        
        // отрисовываем слой documentFromBaseTextArea
        
        documentFromBaseTextArea = new TextArea();
        
        
        // ---------------------------------------------
        
        
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
    private void setSizeOfComponentsInMainStage(double windowWight){
        
        // устанавливаем размеры компонентов слоя Новый документ
        double fishListViewWight = windowWight/5;
        fishListView.setMinWidth(fishListViewWight);
        fishListView.setMaxWidth(fishListViewWight);
        double textAreaWight = fishListViewWight/2*7;
        newDocumentTextArea.setMinWidth(textAreaWight);
        newDocumentTextArea.setMaxWidth(textAreaWight);
        splitPaneListFishesAndNewDocument.setDividerPositions(fishListViewWight);
        //-------------------------------------------------------
        
        // устанавливаем размеры компонентов слоя documentFromBaseTextArea
        documentFromBaseTextArea.setMinWidth(textAreaWight);
        documentFromBaseTextArea.setMaxWidth(textAreaWight);
        // ------------------------------------------------------

        // устанавливаем размеры компонентов слоя  workDayTableView
        double workDayTableViewWight = windowWight;
        workDayTableView.setMinWidth(workDayTableViewWight);
        workDayTableView.setMaxWidth(workDayTableViewWight);
        dateColumn.setMinWidth(workDayTableViewWight/10);
        dateColumn.setMaxWidth(workDayTableViewWight/10);
        nameDocumentColumn.setMinWidth(workDayTableViewWight/10*2);
        nameDocumentColumn.setMaxWidth(workDayTableViewWight/10*2);
        personColumn.setMinWidth(workDayTableViewWight/10*5);
        personColumn.setMaxWidth(workDayTableViewWight/10*5);
        mashColumn.setMinWidth(workDayTableViewWight/10);
        mashColumn.setMaxWidth(workDayTableViewWight/10);
        timeColumn.setMinWidth(workDayTableViewWight/10);
        timeColumn.setMaxWidth(workDayTableViewWight/10);
        //-------------------------------------------------------
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
                setStackPane(splitPaneListFishesAndNewDocument);
            }
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F3 && event.getSource() == primaryStage){
                    setStackPane(splitPaneListFishesAndNewDocument);
                }
            }
        });
        //----------------------------------------------------------------
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
        //событие при нажатии Esc в documentTextArea
        documentFromBaseTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    removeLastStackPane();
                }
            }
        });
        //----------------------------------------------------------------
        
        
        // события при нажатии меню "База рабочего дня"
        labelBaseWorkDay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStackPane(workDayTableView);
            }
        });
        KeyCodeCombination f9AltCodeCombination = new KeyCodeCombination(KeyCode.F9, KeyCombination.ALT_DOWN);
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (f9AltCodeCombination.match(event) && event.getSource() == primaryStage){
                    setStackPane(workDayTableView);
            }}
        });
        //------------------------------------------------------------------
        
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
                    removeLastStackPane();
                }
            }
        });
        //------------------------------------------------------------------
        
        // событие при выборе элемента в workDayTableView
        TableView.TableViewSelectionModel<Document> tableViewSelectionModel = workDayTableView.getSelectionModel();  
        
        workDayTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){//по клику мышкой
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){
                    mainStackPane.getChildren().add(documentFromBaseTextArea);
                    // пока в documentFromBaseTextArea пишем название выбранного документа, в будущем нужно вставлять сам документ
                    documentFromBaseTextArea.setText(tableViewSelectionModel.getSelectedItem().getDocName() + "\n" 
                            + tableViewSelectionModel.getSelectedItem().getPerson() + "\n Содержание");
                    documentFromBaseTextArea.requestFocus();
                }
            }
        });
        workDayTableView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Enter
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    mainStackPane.getChildren().add(documentFromBaseTextArea);
                    // пока в documentFromBaseTextArea пишем название выбранного документа, в будущем нужно вставлять сам документ
                    documentFromBaseTextArea.setText(tableViewSelectionModel.getSelectedItem().getDocName() + "\n" 
                            + tableViewSelectionModel.getSelectedItem().getPerson() + "\n Содержание");
                    documentFromBaseTextArea.requestFocus();
                }
            }
        });
        workDayTableView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    removeLastStackPane();
                }
            }
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
