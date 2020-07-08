/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.models;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author eag
 */
public class Document {
        
        private int id;
        private final LocalDate DOC_DATE;
        private final LocalTime DOC_TIME;
        private String DocRegnum;
        private String ActCode;
        private String ActCodeAttribute;
        private String DocName;
        private String DocBody;
        private String Duty;
        private String Services;
        private String DocMash;
        private int DocState;
        private String DocPath;
        private String DocNotarius;
        private String DocActionNotarius;
        
        private String Person;

    public Document(LocalDate docDate, LocalTime docTime, String DocName, String Person) {
        this.DOC_DATE = docDate;
        this.DOC_TIME = docTime;
        this.DocName = DocName;
        this.Person = Person;
    }
        
        
        
}
