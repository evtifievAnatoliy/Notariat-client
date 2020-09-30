/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.uiSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.plaf.synth.Region;
import notariat.client.configuration.Configuration;
import notariat.client.controllers.MainController;

/**
 *
 * @author eag
 */
public class MainForm extends JFrame{
    
    private MainController mainController;
    private double mainWindowWidth;
    private double mainWindowHeight;
    
    private JMenu menuNewDocument;
    private JLabel labelNewDocument;
    private JLabel labelBaseWorkDay;
    private JMenu  menuSettings;
    private JMenuItem menuItemLoadFishes;
    private JMenuItem menuItemLoadKeyMacros;
    private JMenuItem menuItemSettingsKeyMacros;
    private JMenuItem menuItemSettingsFishes;
    private JMenuItem menuItemExit;
    
    public MainForm() throws IOException{
        
        super("Нотариат: " + Configuration.getInstance().getProperty("department"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
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
        
        // отрисовываем меню
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(Box.createHorizontalBox());
        menuNewDocument = new JMenu("Новый документ (F3)");
        //labelNewDocument = new JLabel("Новый документ (F3)");
        //menuNewDocument.setName("Новый документ (F3)");
        //setMenuNewDocument(mainController.getFishesReaderWriter().readFishCategories());
        //labelBaseWorkDay = new JLabel("База рабочего дня (Alt-F9)");
        JMenu menuBaseWorkDay = new JMenu("База рабочего дня (Alt-F9)");
        //menuBaseWorkDay.setGraphic(labelBaseWorkDay);
        menuBar.add(menuNewDocument);
        menuBar.add(menuBaseWorkDay);
        
        menuSettings = new JMenu("Настройки");
        menuItemLoadFishes = new JMenuItem("Загрузить шаблоны");
        menuSettings.add(menuItemLoadFishes);
        menuItemLoadKeyMacros = new JMenuItem("Загрузить макросы");
        menuSettings.add(menuItemLoadKeyMacros);
        menuItemSettingsKeyMacros = new JMenuItem("Настроить макросы");
        menuSettings.add(menuItemSettingsKeyMacros);
        menuItemSettingsFishes = new JMenuItem("Настроить шаблоны");
        menuSettings.add(menuItemSettingsFishes);
        
        menuBar.add(Box.createHorizontalGlue());
        JMenu menuExit = new JMenu("Выход");
        menuItemExit = new JMenuItem("Выход");
        menuExit.add(menuItemExit);
        menuBar.add(menuSettings);
        menuBar.add(menuExit);
        
        
        
        
        setJMenuBar(menuBar);

        // Открытие окна
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        
    }
}
