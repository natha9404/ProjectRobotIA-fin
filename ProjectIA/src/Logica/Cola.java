/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;

/**
 *
 * @author mao
 */
public class Cola {

    private ArrayList<Bloque> cola = new ArrayList<>();

    void push(Bloque a) {
        Bloque nodo;
        nodo = new Bloque();
        nodo = a;
        cola.add(nodo);
    }

    Bloque pop() {
        Bloque nodo;
        nodo = new Bloque();
        if (cola.size() > 0) {
            nodo = cola.get(0);
            cola.remove(0);
        } else if (cola.size() == 0) {
            nodo = null;
        }

        return nodo;
    }

    void clear() {
        cola.clear();
    }

    boolean vacio() {
        boolean salida = cola.isEmpty();
        return salida;
    }

    public ArrayList<Bloque> getCola() {
        return cola;
    }

    public void setCola(ArrayList<Bloque> cola) {
        this.cola = cola;
    }

}
