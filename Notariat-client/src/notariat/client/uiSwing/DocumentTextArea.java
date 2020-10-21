/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.uiSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import notariat.client.controllers.MainController;
import notariat.client.models.KeyMacro;

/**
 *
 * @author eag
 */
public class DocumentTextArea {
    
    private Dimension mainWindow;
    private MainController mainController;

    private JTextArea documentTextArea; 
    private JPanel documentPanel;
    
    private MainForm mainForm;


    public DocumentTextArea(Dimension mainWindow) {
        this.mainWindow = mainWindow;
        mainController = MainController.getInstance();
        
        documentTextArea = new JTextArea();
        
        
        setSizeOfComponents(mainWindow);
        setStileOfComponents(22);
        initializationOfAllActionListeners();
    }
    
    public DocumentTextArea(MainForm mainForm, Dimension mainWindow) {
        this(mainWindow);
        this.mainForm = mainForm;
        
    }

    public JTextArea getDocumentTextArea() {
        return documentTextArea;
    }
    
    public JPanel getDocumentPanel() {
        documentPanel = new JPanel();
        documentPanel.setLayout(new BorderLayout());
        documentPanel.add(documentTextArea, BorderLayout.CENTER);
        JPanel westPanel = new JPanel();// боковые серые панели
        westPanel.setPreferredSize(new Dimension(mainWindow.width/14, mainWindow.height));
        documentPanel.add(westPanel, BorderLayout.WEST);
        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(mainWindow.width/14, mainWindow.height));
        documentPanel.add(eastPanel, BorderLayout.EAST);
        return documentPanel;
    }

    
    public void setSizeOfComponents(Dimension mainWindow){
        
          documentTextArea.setPreferredSize(new Dimension((int) (mainWindow.getWidth()/10*7), (int) this.mainWindow.getHeight()/10*9));
          //documentTextArea.setPreferredSize(new Dimension(15*68, 700));
    
    }
    
    public void setStileOfComponents(int size){
        
//        documentTextArea.setStyle("-fx-font-family: 'Courier'; -fx-font-weight: 200; -fx-font-size: " + size);
        //Styles: 'serif' (e.g., Times)
                //'sans-serif' (e.g., Helvetica)
                //'cursive' (e.g., Zapf-Chancery)
                //'fantasy' (e.g., Western)
                //'monospace' (e.g., Courier)
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html#typefont
        
        
//        documentTextArea.setWrapText(true); // перенос символов в стрроке по завершению строчки
        
    }
    
    private void initializationOfAllActionListeners(){
        
        //событие при нажатии Esc в documentTextArea
        keyEventEscape();
        //----------------------------------------------------------------
        
        //инициализация макросов
        if(mainController.getKeyMacros().getKeyMacros()!=null)
        for (KeyMacro keyMacro: mainController.getKeyMacros().getKeyMacros()){
            if(keyMacro.getKeyCode() != null){
                documentTextArea.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(java.awt.event.KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(java.awt.event.KeyEvent ke) {
                        if (ke.isAltDown() && keyMacro.getKeyCode().equals(ke.getKeyChar())){
                            int position = documentTextArea.getCaretPosition();
                            try {
                                documentTextArea.setText(documentTextArea.getText(0, position) + 
                                        keyMacro.getMacro_body() + documentTextArea.getText(position + 1, documentTextArea.getText().length() - position));
                                String str = documentTextArea.getText(0, position);
                            } catch (BadLocationException ex) {
                                JOptionPane.showMessageDialog(mainForm, "Не удалось вставить макрос", "Error:", JOptionPane.ERROR_MESSAGE);
                            }
                            documentTextArea.setCaretPosition(position);
                            
                        }
                    }
                    @Override
                    public void keyReleased(java.awt.event.KeyEvent ke) {
                    }
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
        
//        documentTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){  //по нажатию Escape
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.F10){ 
//                    
//                    //mainForm.removeLastStackPane();
//                    mainForm.getDocumentHTMLArea().setTextTODocumentHTMLArea(documentTextArea.getText().replaceAll("\n", "<br>"));
//                    mainForm.setStackPane(mainForm.getDocumentHTMLArea().getDocumentHTMLArea());
//                }
//            }
//        });
        
    }
    
    public void keyEventEscape(){
        documentTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE){ 
                    mainForm.removeLastStackPane();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent ke) {
            }
        });
    };
    
    
}
