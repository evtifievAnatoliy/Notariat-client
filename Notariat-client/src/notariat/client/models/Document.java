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
        private String docRegnum;
        private String actCode;
        private String actCodeAttribute;
        private String docName;
        private String docBody;
        private String duty;
        private String services;
        private String docMash;
        private int docState;
        private String docPath;
        private String docNotarius;
        private String docActionNotarius;
        
        private String person;

    public Document(LocalDate docDate, LocalTime docTime, String DocName, String Person , String docMash) {
        this.DOC_DATE = docDate;
        this.DOC_TIME = docTime;
        this.docName = DocName;
        this.person = Person;
        this.docMash = docMash;
    }

    public LocalDate getDOC_DATE() {
        return DOC_DATE;
    }

    public LocalTime getDOC_TIME() {
        return DOC_TIME;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocMash() {
        return docMash;
    }

    public String getPerson() {
        return person;
    }
        
        
        
}
