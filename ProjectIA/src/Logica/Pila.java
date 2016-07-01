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
public class Pila {
    private ArrayList<Bloque> pila = new ArrayList<>();

    void push(Bloque a) {
        Bloque nodo;
        nodo = new Bloque();
        nodo = a;
        pila.add(nodo);
    }

    Bloque pop() {
        Bloque nodo;
        nodo = new Bloque();
        if (pila.size() > 0) {
            int indice = pila.size() -1;
            nodo = pila.get(indice);
            pila.remove(indice);
        } else if (pila.size() == 0) {
            nodo = null;
        }

        return nodo;
    }

    void clear() {
        pila.clear();
    }

    boolean vacio() {
        boolean salida = pila.isEmpty();
        return salida;
    }

    public ArrayList<Bloque> getCola() {
        return pila;
    }

    public void setCola(ArrayList<Bloque> cola) {
        this.pila = cola;
    }
    
}
