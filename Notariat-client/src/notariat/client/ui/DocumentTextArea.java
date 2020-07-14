/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import notariat.client.controllers.MainController;

/**
 *
 * @author eag
 */
public class DocumentTextArea {
    
    private double mainWindowWidth;
    private MainController mainController;

    private TextArea documentTextArea; 
    
    private MainForm mainForm;
    
    public DocumentTextArea(MainForm mainForm, double wight) {
        this.mainWindowWidth = wight;
        this.mainForm = mainForm;
        
        mainController = MainController.getInstance();
        
        documentTextArea = new TextArea();
        
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }

    public TextArea getDocumentTextArea() {
        return documentTextArea;
    }
    
    
    
    public void setSizeOfComponents(double windowWight){
        
        documentTextArea.setMinWidth(windowWight/10*7);
        documentTextArea.setMaxWidth(windowWight/10*7);
    
    }
    
    private void initializationOfAllActionListeners(){
        
        //событие при нажатии Esc в documentTextArea
        documentTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }
        });
        //----------------------------------------------------------------
        
    }
    
}
