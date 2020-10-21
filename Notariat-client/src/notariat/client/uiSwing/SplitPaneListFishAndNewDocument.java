/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.uiSwing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import notariat.client.controllers.FishSubCategories;
import notariat.client.controllers.MainController;
import notariat.client.models.Fish;
import notariat.client.models.FishSubCategory;

/**
 *
 * @author user
 */
public class SplitPaneListFishAndNewDocument{
    
    private Dimension mainWindow;
    private MainController mainController;
    
    private JSplitPane splitPaneListFishesAndNewDocument;
    private JList<FishSubCategory> fishSubCategoriesListView;
    private DefaultListModel fishSubCategoriesListViewModel;
    private JList<Fish> fishListView;
    
    private DefaultListModel fishListViewModel;
    private MainForm mainForm;
    
    private JPanel listStackPane;
    private DocumentTextArea newDocumentTextArea;
    
    public SplitPaneListFishAndNewDocument(MainForm mainForm, Dimension mainWindow) {
        this.mainWindow = mainWindow;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
    
        // отрисовываем слой  "список Подкаталогов шаблонов"
        //ArrayList<FishSubCategory> fishSubCategoriesArray = new ArrayList<FishSubCategory>();
        fishSubCategoriesListView = new JList<FishSubCategory>();
        fishSubCategoriesListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fishSubCategoriesListViewModel = new DefaultListModel();
        fishSubCategoriesListView.setModel(fishSubCategoriesListViewModel);
        //fishSubCategoriesListViewModel.add(1, "Yello");
        //fishSubCategoriesListView.setListData(fishSubCategoriesArray);
        // ------------------------------------
        
        // отрисовываем слой  "список шаблонов"
        //ObservableList<Fish> fishesArray = FXCollections.observableArrayList();
        fishListView = new JList<Fish>();
        fishListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fishListViewModel = new DefaultListModel();
        fishListView.setModel(fishListViewModel);
        // ------------------------------------
        newDocumentTextArea = new DocumentTextArea(mainForm, mainWindow)
        {
            @Override
            public void keyEventEscape() {
                getDocumentTextArea().addKeyListener(new KeyListener(){  //по нажатию Escape
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }
                    @Override
                    public void keyPressed(KeyEvent ke) {
                        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE){ 
                            getDocumentTextArea().setText("");
                            fishListView.requestFocus();
                        }
                    }
                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }
        };
//        };
        listStackPane = new JPanel();
        listStackPane.setLayout(new BorderLayout());
        listStackPane.add(new JScrollPane(fishListView), BorderLayout.CENTER);
        //listStackPane.add(new JLabel("Hello"));
//        splitPaneListFishesAndNewDocument = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, 
//                new JScrollPane(fishSubCategoriesListView), 
//                newDocumentTextArea.getDocumentPanel());
        //splitPaneListFishesAndNewDocument.add(listStackPane, newDocumentTextArea.getDocumentTextArea());
        //splitPaneListFishesAndNewDocument.add(newDocumentTextArea.getDocumentTextArea());
        
        
        setStackPane(fishSubCategoriesListView);
        setSizeOfComponents(mainWindow);
        initializationOfAllActionListeners();
    }

    public JList<FishSubCategory> getFishSubCategoriesListView() {
        return fishSubCategoriesListView;
    }
    

    public JSplitPane getSplitPaneListFishesAndNewDocument() {
        return splitPaneListFishesAndNewDocument;
    }
    
    public void setStackPane(JComponent jComponent) {
        splitPaneListFishesAndNewDocument = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, 
                new JScrollPane(fishSubCategoriesListView), 
                newDocumentTextArea.getDocumentPanel());
//            if (listStackPane.getComponentCount() >0){
//                Component[] components = listStackPane.getComponents();
//                for (int i=0; i<components.length; i++)
//                    listStackPane.remove(components[i]);
//            }
//            listStackPane.add(jComponent);
    }
    
    public void removeLastStackPane() {
            if (listStackPane.getComponentCount() >0){
                Component[] components = listStackPane.getComponents();
                    listStackPane.remove(components[components.length-1]);
            }
            
    }
    public void removeElementsFromFishSubCategoriesListView() {
            if (fishSubCategoriesListViewModel.getSize() >0)
                fishSubCategoriesListViewModel.removeAllElements();
              
    }
    
    public void addElementsToFishSubCategoriesListView(ArrayList <FishSubCategory> fishSubCategories) {
            for(FishSubCategory fishSubCategory : fishSubCategories){
                fishSubCategoriesListViewModel.addElement(fishSubCategory);
            }
                
    }
    
    public void setSizeOfComponents(Dimension mainWindow){
        
        // устанавливаем размеры компонентов слоя Новый документ
        splitPaneListFishesAndNewDocument.setPreferredSize(mainWindow);
        double fishListViewWight = mainWindow.getWidth()/5;
        listStackPane.setPreferredSize(new Dimension((int) fishListViewWight, 100));
        
        fishListView.setPreferredSize(new Dimension((int) fishListViewWight, 100));
        
        double textAreaWight = fishListViewWight/2*7;
        newDocumentTextArea.setSizeOfComponents(new Dimension((int) textAreaWight, (int) mainWindow.getHeight()));
        splitPaneListFishesAndNewDocument.setDividerLocation((int)fishListViewWight);
        //-------------------------------------------------------
    }
    
     private void initializationOfAllActionListeners(){
        
        // событие при выборе элемента в fishSubCategoriesListView
//        MultipleSelectionModel<FishSubCategory> fishSubCategoriesListViewSelectionModel = fishSubCategoriesListView.getSelectionModel();
//        fishSubCategoriesListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
//                    fishListView.getItems().clear();
//                    try{
//                        fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
//                    }
//                    catch(Exception e){
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось прочитать шаблоны из базы данных: \n" + e.getMessage(), ButtonType.OK);
//                        alert.showAndWait();
//                    
//                    }
//                    listStackPane.getChildren().add(fishListView);
//                    fishListView.requestFocus();
//                }
//            }
//        });
//        fishSubCategoriesListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER){ 
//                    fishListView.getItems().clear();
//                    try{
//                        fishListView.getItems().addAll(mainController.getFishes(fishSubCategoriesListViewSelectionModel.getSelectedItem()).getFishes());
//                    }
//                    catch(Exception e){
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось прочитать шаблоны из базы данных: \n" + e.getMessage(), ButtonType.OK);
//                        alert.showAndWait();
//                    
//                    }  
//                    listStackPane.getChildren().add(fishListView);
//                    fishListView.requestFocus();
//                }
//            }
//        });
//        fishSubCategoriesListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ESCAPE){ 
//                    mainForm.removeLastStackPane();
//                }
//            }
//        });
        //-------------------------------------------------------- 
         
        // событие при выборе элемента в fishListView
//        MultipleSelectionModel<Fish> fishListViewSelectionModel = fishListView.getSelectionModel();
//        fishListView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.getClickCount() >1){ //>1 для двойного нажатия //>0 для одинарного
//                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
//                    newDocumentTextArea.getDocumentTextArea().setText(fishListViewSelectionModel.getSelectedItem().getFish_body());
//                    newDocumentTextArea.getDocumentTextArea().requestFocus();
//                }
//            }
//        });
//        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER){ 
//                    // пока в newDocumentTextArea пишем название выбранной рыбы, в будущем нужно вставлять саму рыбу(шаблон документа)
//                    newDocumentTextArea.getDocumentTextArea().setText(fishListViewSelectionModel.getSelectedItem().getFish_body());
//                    newDocumentTextArea.getDocumentTextArea().requestFocus();
//                }
//            }
//        });
//        fishListView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ESCAPE){ 
//                    removeLastStackPane();
//                }
//            }
//        });
        //------------------------------------------------------------------
        
     }
    
    
}
