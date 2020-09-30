/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.ui;

import javafx.event.EventHandler;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import notariat.client.controllers.MainController;
import notariat.client.models.KeyMacro;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.RichTextChange;

/**
 *
 * @author eag
 */
public class DocumentHTMLArea {
    
    private double mainWindowWidth;
    private MainController mainController;

    private HTMLEditor documentHTMLArea; 
    private WebView webview;
    private MainForm mainForm;

    public DocumentHTMLArea(double mainWindowWidth) {
        this.mainWindowWidth = mainWindowWidth;
        mainController = MainController.getInstance();
        
        documentHTMLArea = new HTMLEditor();
        
        webview = (WebView) documentHTMLArea.lookup("WebView");
        GridPane.setHgrow(webview, Priority.ALWAYS);
        GridPane.setVgrow(webview, Priority.ALWAYS);
        
        setSizeOfComponents(mainWindowWidth);
        initializationOfAllActionListeners();
    }
    
    public DocumentHTMLArea(MainForm mainForm, double wight) {
        this(wight);
        this.mainForm = mainForm;
        
    }

    public HTMLEditor getDocumentHTMLArea() {
        return documentHTMLArea;
    }
    
    
    
    public void setSizeOfComponents(double windowWight){
        
//        documentHTMLArea.setMinWidth(windowWight/10*7);
//        documentHTMLArea.setMaxWidth(windowWight/10*7);
        documentHTMLArea.setMinWidth(15*68);
        documentHTMLArea.setMaxWidth(15*68);
    
    }
    
    
    public void setTextTODocumentHTMLArea(String text){
        
        documentHTMLArea.setHtmlText("<font face='courier' size=5 >" 
                                        + text 
                                        + "</font>");
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
                documentHTMLArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent event) {
                        if (codeCombination.match(event) && event.getSource() == documentHTMLArea){
                            //int position = webview.getEngine().
//                            documentTextArea.insertText(position, keyMacro.getMacro_body());
//                            documentTextArea.positionCaret(position);*/
                            ;
                        }}
                });
            }
        }
        // событие отправки задания на печать Ctrl-P
        KeyCodeCombination pAltCodeCombination = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        documentHTMLArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (pAltCodeCombination.match(event)){ 
                    
                    PrinterJob job = PrinterJob.createPrinterJob();
                    if (job != null && job.showPrintDialog(mainForm.getPrimaryStage())) {
                        WebView webView = webview;
                        PageLayout pageLayout = job.getPrinter().getDefaultPageLayout();
                        double scaleX = pageLayout.getPrintableWidth() / documentHTMLArea.getWidth();
                        double scaleY = pageLayout.getPrintableHeight() / documentHTMLArea.getHeight();
//                        double scaleX = pageLayout.getPrintableWidth() / webView.getBoundsInParent().getWidth();
//                        double scaleY = pageLayout.getPrintableHeight() / webView.getBoundsInParent().getHeight();
//                                                
                        double minimumScale = Math.min(scaleX, scaleY);
                        
                        Scale scale = new Scale(minimumScale, minimumScale);
                        try {
                          documentHTMLArea.getTransforms().add(scale);
                          boolean success = job.printPage(webView);
                          if (success)
                            job.endJob();
                        } finally {
                          documentHTMLArea.getTransforms().remove(scale);
                        }
                    }
                }
            }
//                    PrinterJob job = PrinterJob.createPrinterJob();
//                    job.showPageSetupDialog(mainForm.getPrimaryStage());
//                    if (job != null) {
//                        boolean success = job.printPage(webview);
//                        if (success) {
//                            job.endJob();
//                        }
//                    }
//                }
//            }
        });
        
        
        
    }
    
    public void keyEventEscape(){
        documentHTMLArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }
        });
    };
    
    
}
