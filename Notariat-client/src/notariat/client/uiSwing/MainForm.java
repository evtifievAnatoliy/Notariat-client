/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.uiSwing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.MenuItem;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.synth.Region;
import notariat.client.configuration.Configuration;
import notariat.client.controllers.MainController;
import notariat.client.models.FishCategory;

/**
 *
 * @author eag
 */
public class MainForm extends JFrame{
    
    private MainController mainController;
    private double mainWindowWidth;
    private double mainWindowHeight;
    
    private Container container;
    private Font mainFont;
    private JMenu menuNewDocument;
    private JMenu  menuSettings;
    private JMenuItem menuItemLoadFishes;
    private JMenuItem menuItemLoadKeyMacros;
    private JMenuItem menuItemSettingsKeyMacros;
    private JMenuItem menuItemSettingsFishes;
    private JMenuItem menuItemExit;
    private JMenuBar menuBar; 
    private JPanel mainPanel;
    private CardLayout mainPanelCardLayout;
    
    private SplitPaneListFishAndNewDocument splitPaneListFishAndNewDocument;
    
    public MainForm() throws IOException{
        
        super("Нотариат: " + Configuration.getInstance().getProperty("department"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        container = this.getContentPane();
        
        try{
            mainController = MainController.getInstance();
               
        }
        catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Не удалось прочитать категории шаблонов из базы данных: \n" + e.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        }
        
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(monitorSize.width - monitorSize.width/8, monitorSize.height - monitorSize.height/8);
        mainWindowWidth = monitorSize.getWidth(); //от ширины разрешения экрана
        mainWindowHeight = this.getHeight(); //от ширины окна приложения
        
        // устанавливаем стиль кнопок
        mainFont = new Font("TimesRoman",Font.HANGING_BASELINE,12);
        
        // отрисовываем меню
        menuBar = new JMenuBar();
        menuBar.add(Box.createHorizontalBox());
        menuNewDocument = new JMenu("Новый документ (F3)");
        menuNewDocument.setFont(mainFont);
        try{
            setMenuNewDocument(mainController.getFishesReaderWriter().readFishCategories());
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        }
        JMenu menuBaseWorkDay = new JMenu("База рабочего дня (Alt-F9)");
        menuBaseWorkDay.setFont(mainFont);
        menuBar.add(menuNewDocument);
        menuBar.add(menuBaseWorkDay);
        
        menuSettings = new JMenu("Настройки");
        menuSettings.setFont(mainFont);
        menuItemLoadFishes = new JMenuItem("Загрузить шаблоны");
        menuItemLoadFishes.setFont(mainFont);
        menuSettings.add(menuItemLoadFishes);
        menuItemLoadKeyMacros = new JMenuItem("Загрузить макросы");
        menuItemLoadKeyMacros.setFont(mainFont);
        menuSettings.add(menuItemLoadKeyMacros);
        menuItemSettingsKeyMacros = new JMenuItem("Настроить макросы");
        menuItemSettingsKeyMacros.setFont(mainFont);
        menuSettings.add(menuItemSettingsKeyMacros);
        menuItemSettingsFishes = new JMenuItem("Настроить шаблоны");
        menuItemSettingsFishes.setFont(mainFont);
        menuSettings.add(menuItemSettingsFishes);
        
        menuBar.add(Box.createHorizontalGlue());
        JMenu menuExit = new JMenu("Выход");
        menuExit.setFont(mainFont);
        menuItemExit = new JMenuItem("Выход");
        menuItemExit.setFont(mainFont);
        menuExit.add(menuItemExit);
        menuBar.add(menuSettings);
        menuBar.add(menuExit);
        
        mainPanel = new JPanel();
        mainPanel.setFocusable(true);
        DocumentTextArea documentTextArea = new DocumentTextArea(this,monitorSize);
        
        // отрисовываем элементы компановки StackPane. Окна будут находится на разных слоях
                
        // отрисовываем слой Новый документ
        splitPaneListFishAndNewDocument = new SplitPaneListFishAndNewDocument(this, monitorSize);
 //       setStackPane(splitPaneListFishAndNewDocument.getSplitPaneListFishesAndNewDocument());
        //setStackPane(new JScrollPane(documentTextArea.getDocumentTextArea()));

        
        setJMenuBar(menuBar);
        
        setStackPane(new JLabel(""));
        // Открытие окна
        
        initializationOfAllActionListeners();
        
        
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        
    }
    
    public void setMenuNewDocument(ArrayList<FishCategory> categoriesFisheses) {
        for (FishCategory categoryFishes : categoriesFisheses){
            JMenuItem menuItem = new JMenuItem(categoryFishes.toString());
            menuItem.setFont(mainFont);
            this.menuNewDocument.add(menuItem);
        }
        
    }
    
    public void setStackPane(JComponent jComponent){
        
        mainPanel.add(jComponent, BorderLayout.CENTER);
        container.add(mainPanel);
        container.revalidate();
        container.repaint();
    }
    public void removeLastStackPane() {
            if (mainPanel.getComponentCount() > 0){
                Component[] components = mainPanel.getComponents();
                for (int i=0; i<components.length; i++)
                    mainPanel.remove(components[i]);
                
                container.remove(mainPanel);
                setStackPane(new JLabel(""));
                container.revalidate();
                container.repaint();
            }
        
    }
    
    private void initializationOfAllActionListeners(){
        
        // событие по нажатию пункта меню "Выход" 
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        
        });
        /*
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
        // событие по нажатию пункта меню "Настроить шаблоны в настройках" 
        menuItemSettingsFishes.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event) {
                try{
                    FishesEditModalDialog fishesEditModalDialog = new  FishesEditModalDialog(primaryStage ,"Настройка шаблонов", 
                            mainWindowWidth, mainWindowHeight, mainController);
                    fishesEditModalDialog.showAndWait();
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
        
        */
        
        // события при нажатии меню "Новый документ"
        
        mainPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_F3){
                    menuNewDocument.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
            });
        
        //JMenuItem[] jMenuItems = menuNewDocument.getI
        
        for(int i=0 ; i<menuNewDocument.getItemCount(); i++){
            JMenuItem jMenuItem =  menuNewDocument.getItem(i);
            jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                        splitPaneListFishAndNewDocument.removeElementsFromFishSubCategoriesListView();
                        splitPaneListFishAndNewDocument.addElementsToFishSubCategoriesListView(
                            mainController.getFishSubCategories(
                                    mainController.getFishCategories().findFishCategoryByName(jMenuItem.getText())).getFishSubCategories());
                        JOptionPane.showMessageDialog(getContentPane(), "Шаблоны: \n" + 
                                jMenuItem.getText(), "Шаблон:", JOptionPane.INFORMATION_MESSAGE);
                        setStackPane(splitPaneListFishAndNewDocument.getSplitPaneListFishesAndNewDocument());
                        container.revalidate();
                        container.repaint();
                        
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(getContentPane(), "Не удалось прочитать категории шаблонов из базы данных: \n" 
                                + e.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
                    }
            }
        
        }); 
    }    
        //----------------------------------------------------------------
        /*
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
        */
    }
    
    
/*
    
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
    */
}
