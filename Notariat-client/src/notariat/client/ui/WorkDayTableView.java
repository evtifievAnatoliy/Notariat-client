/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import notariat.client.controllers.MainController;
import notariat.client.models.Document;

/**
 *
 * @author eag
 */
public class WorkDayTableView {
    
    private double mainWindowWidth;
    private MainController mainController;
    
    private TableView<Document> workDayTableView;
    private TableColumn<Document, LocalDate> dateColumn;
    private TableColumn<Document, LocalDate> nameDocumentColumn;
    private TableColumn<Document, LocalDate> personColumn;
    private TableColumn<Document, LocalDate> mashColumn;
    private TableColumn<Document, LocalTime> timeColumn;
    private MainForm mainForm;
    
    public WorkDayTableView(MainForm mainForm, double wight) {
        
        this.mainWindowWidth = wight;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
    
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
    
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }
    
    public void setSizeOfComponents(double windowWight){
        
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

    public TableView<Document> getWorkDayTableView() {
        return workDayTableView;
    }
    
    
    
    private void initializationOfAllActionListeners(){
        
        // событие при выборе элемента в workDayTableView
        TableView.TableViewSelectionModel<Document> tableViewSelectionModel = workDayTableView.getSelectionModel();  
        
        workDayTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){//по клику мышкой
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >1){
                    mainForm.getMainStackPane().getChildren().add(mainForm.getDocumentFromBaseTextArea().getDocumentTextArea());
                    // пока в documentFromBaseTextArea пишем название выбранного документа, в будущем нужно вставлять сам документ
                    mainForm.getDocumentFromBaseTextArea().getDocumentTextArea().setText(tableViewSelectionModel.getSelectedItem().getDocName() + "\n" 
                            + tableViewSelectionModel.getSelectedItem().getPerson() + "\n Содержание");
                    mainForm.getDocumentFromBaseTextArea().getDocumentTextArea().requestFocus();
                }
            }
        });
        workDayTableView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Enter
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){ 
                    mainForm.getMainStackPane().getChildren().add(mainForm.getDocumentFromBaseTextArea().getDocumentTextArea());
                    // пока в documentFromBaseTextArea пишем название выбранного документа, в будущем нужно вставлять сам документ
                    mainForm.getDocumentFromBaseTextArea().getDocumentTextArea().setText(tableViewSelectionModel.getSelectedItem().getDocName() + "\n" 
                            + tableViewSelectionModel.getSelectedItem().getPerson() + "\n Содержание");
                    mainForm.getDocumentFromBaseTextArea().getDocumentTextArea().requestFocus();
                }
            }
        });
        workDayTableView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
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
