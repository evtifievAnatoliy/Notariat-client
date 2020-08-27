/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.models;

import java.util.Comparator;
import javafx.scene.input.KeyCode;

/**
 *
 * @author eag
 */
public class KeyMacro implements Comparable<KeyMacro>{
    private int id;
    private String key;
    private String macro_body;

    public KeyMacro(int id, String key, String macro_body) {
        this.id = id;
        this.key = key;
        this.macro_body = macro_body;
    }

    public KeyMacro(String key, String macro_body) {
        this.key = key;
        this.macro_body = macro_body;
    }

    public void setMacro_body(String macro_body) {
        this.macro_body = macro_body;
    }

    public int getId() {
        return id;
    }
    
    public String getKey() {
        return key;
    }

    public String getMacro_body() {
        return macro_body;
    }
    
    public KeyCode getKeyCode() {
                        if(key.equals("Ф")) return KeyCode.A;
                        if(key.equals("Ы")) return KeyCode.S;
                        if(key.equals("В")) return KeyCode.D;
                        if(key.equals("А")) return KeyCode.F;
                        if(key.equals("П")) return KeyCode.G;
                        if(key.equals("Р")) return KeyCode.H;
                        if(key.equals("О")) return KeyCode.J;
                        if(key.equals("Л")) return KeyCode.K;
                        if(key.equals("Д")) return KeyCode.L;
                        if(key.equals("Ж")) return KeyCode.SEMICOLON;
                        if(key.equals("Э"))return KeyCode.QUOTE;
                        
                        
                        if(key.equals("Й")) return KeyCode.Q;
                        if(key.equals("Ц")) return KeyCode.W;
                        if(key.equals("У")) return KeyCode.E;
                        if(key.equals("К")) return KeyCode.R;
                        if(key.equals("Е")) return KeyCode.T;
                        if(key.equals("Н")) return KeyCode.Y;
                        if(key.equals("Г")) return KeyCode.U;
                        if(key.equals("Ш")) return KeyCode.I;
                        if(key.equals("Щ")) return KeyCode.O;
                        if(key.equals("З")) return KeyCode.P;
                        if(key.equals("Х")) return KeyCode.OPEN_BRACKET;
                        if(key.equals("Ъ")) return KeyCode.CLOSE_BRACKET;
                        
                        if(key.equals("Я")) return KeyCode.Z;
                        if(key.equals("Ч")) return KeyCode.X;
                        if(key.equals("С")) return KeyCode.C;
                        if(key.equals("М")) return KeyCode.V;
                        if(key.equals("И")) return KeyCode.B;
                        if(key.equals("Т")) return KeyCode.N;
                        if(key.equals("Ь")) return KeyCode.M;
                        if(key.equals("Б")) return KeyCode.COMMA;
                        if(key.equals("Ю")) return KeyCode.COLON;
                        
                        if(key.equals("1")) return KeyCode.DIGIT1;
                        if(key.equals("2")) return KeyCode.DIGIT2;
                        if(key.equals("3")) return KeyCode.DIGIT3;
                        if(key.equals("4")) return KeyCode.DIGIT4;
                        if(key.equals("5")) return KeyCode.DIGIT5;
                        if(key.equals("6")) return KeyCode.DIGIT6;
                        if(key.equals("7")) return KeyCode.DIGIT7;
                        if(key.equals("8")) return KeyCode.DIGIT8;
                        if(key.equals("9")) return KeyCode.DIGIT9;
                        if(key.equals("0")) return KeyCode.DIGIT0;
                        
                        else return null;
		
    }

    @Override
    public String toString() {
        return "Alt - " + key;
    }

    @Override
    public int compareTo(KeyMacro key) {
        return this.key.compareTo(key.getKey());
    }
    //Comparator для сортировки списка или массива объектов по имени
    public static Comparator<KeyMacro> NameComparator = new Comparator<KeyMacro>() {
 
        @Override
        public int compare(KeyMacro key1, KeyMacro key2) {
            return key1.getKey().compareTo(key2.getKey());
        }
    };
    
}
