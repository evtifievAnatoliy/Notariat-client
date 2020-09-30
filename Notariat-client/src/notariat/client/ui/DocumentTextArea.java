/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import notariat.client.controllers.MainController;
import notariat.client.models.KeyMacro;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.RichTextChange;

/**
 *
 * @author eag
 */
public class DocumentTextArea {
    
    private double mainWindowWidth;
    private MainController mainController;

    private TextArea documentTextArea; 
    
    private MainForm mainForm;

    public DocumentTextArea(double mainWindowWidth) {
        this.mainWindowWidth = mainWindowWidth;
        mainController = MainController.getInstance();
        
        documentTextArea = new TextArea();
        
        setSizeOfComponents(mainWindowWidth);
        setStileOfComponents(22);
        initializationOfAllActionListeners();
    }
    
    public DocumentTextArea(MainForm mainForm, double wight) {
        this(wight);
        this.mainForm = mainForm;
        
    }

    public TextArea getDocumentTextArea() {
        return documentTextArea;
    }
    
    
    
    public void setSizeOfComponents(double windowWight){
        
//        documentTextArea.setMinWidth(windowWight/10*7);
//        documentTextArea.setMaxWidth(windowWight/10*7);
        documentTextArea.setMinWidth(15*68);
        documentTextArea.setMaxWidth(15*68);
    
    }
    
    public void setStileOfComponents(int size){
        
        documentTextArea.setStyle("-fx-font-family: 'Courier'; -fx-font-weight: 200; -fx-font-size: " + size);
        //Styles: 'serif' (e.g., Times)
                //'sans-serif' (e.g., Helvetica)
                //'cursive' (e.g., Zapf-Chancery)
                //'fantasy' (e.g., Western)
                //'monospace' (e.g., Courier)
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html#typefont
        
        
        documentTextArea.setWrapText(true); // перенос символов в стрроке по завершению строчки
        
    }
    
    private void initializationOfAllActionListeners(){
        
        //событие при нажатии Esc в documentTextArea
        keyEventEscape();
        //----------------------------------------------------------------
        
        //инициализация макросов
        if(mainController.getKeyMacros().getKeyMacros()!=null)
        for (KeyMacro keyMacro: mainController.getKeyMacros().getKeyMacros()){
            if(keyMacro.getKeyCode() != null){
                KeyCodeCombination codeCombination = new KeyCodeCombination(keyMacro.getKeyCode(), KeyCombination.ALT_DOWN);
                documentTextArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent event) {
                        if (codeCombination.match(event) && event.getSource() == documentTextArea){
                            int position = documentTextArea.getCaretPosition();
                            documentTextArea.insertText(position, keyMacro.getMacro_body());
                            documentTextArea.positionCaret(position);
                            ;
                        }}
                });
            }
        }
        
        //событие по изменению фокуса
//        documentTextArea.focusedProperty().addListener( (obs, oldValue, newValue) -> {
//        if (newValue) { /* при получении фокуса */ }
//        else { /* при потере */ 
//            documentTextArea.clear();
//        }
//        });
        
        documentTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F10){ 
                    
                    //mainForm.removeLastStackPane();
                    mainForm.getDocumentHTMLArea().setTextTODocumentHTMLArea(documentTextArea.getText().replaceAll("\n", "<br>"));
                    mainForm.setStackPane(mainForm.getDocumentHTMLArea().getDocumentHTMLArea());
                }
            }
        });
        
    }
    
    public void keyEventEscape(){
        documentTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }
        });
    };
    
    
}
