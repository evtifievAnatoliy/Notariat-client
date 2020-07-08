/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notariat.client.controllers;

import java.util.ArrayList;
import notariat.client.models.Fish;

/**
 *
 * @author eag
 */
public class Fishes {
    private ArrayList<Fish> fishes;

    public Fishes() {
        
        fishes = new ArrayList<Fish>();
        fishes.add(new Fish("Доверенность на распоряжение счетом"));
        fishes.add(new Fish("Доверенность на распоряжение счетом (Общая)"));
        fishes.add(new Fish("Доверенность на распоряжение счетом (Сбербанк)"));
        fishes.add(new Fish("Доверенность на распоряжение вкладом"));
        fishes.add(new Fish("Доверенность на распоряжение вкладом (пенсия)"));
        fishes.add(new Fish("Доверенность на распоряжение картой"));
        fishes.add(new Fish("Доверенность на получение з/п"));
        fishes.add(new Fish("Доверенность на ведение дел в суде"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (Арбитраж)"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (общая)"));
        fishes.add(new Fish("Доверенность на ведение дел в суде (Уголовные дела)"));
        fishes.add(new Fish("Доверенность на получении пособия"));
        fishes.add(new Fish("Доверенность (образцы)"));
        
    }

    public ArrayList<Fish> getFishes() {
        return fishes;
    }
}
