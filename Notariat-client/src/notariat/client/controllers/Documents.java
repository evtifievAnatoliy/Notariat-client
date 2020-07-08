/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import notariat.client.models.Document;

/**
 *
 * @author eag
 */
public class Documents {
    
    private ArrayList<Document> documents;

    public Documents() {
        
        documents = new ArrayList<Document>();
        documents.add(new Document(LocalDate.now(), LocalTime.now(), "Дов.- На распоряжение", "Ляднов Т.И, Зубрина О.С."));
        documents.add(new Document(LocalDate.now(), LocalTime.now(), "Дов.- Прочие", "Иванов И.В."));
        documents.add(new Document(LocalDate.now(), LocalTime.now(), "Дов.- Представитель", "Палуга И.А., Сосногорск К.А."));
        
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }
    
    
    
    
}
