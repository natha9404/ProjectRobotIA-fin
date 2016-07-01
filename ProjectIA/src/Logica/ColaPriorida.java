/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mao
 */
public class ColaPriorida {

    private ArrayList<Bloque> cola = new ArrayList<>();

    void push(Bloque a) {
        Bloque nodo;
        nodo = new Bloque();
        nodo = a;
        cola.add(nodo);
        cola = InsertionSort(cola);
        Collections.reverse(cola);
        
       // Imprimir(cola);
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

    /*Algoritmo de ordenamiento reciclado de FADA*/
    public ArrayList<Bloque> InsertionSort(ArrayList<Bloque> entrada) {
        ArrayList<Bloque> temporal = entrada;
        Bloque key = new Bloque();
        temporal.add(0, key);

        int i = 0;
        for (int j = 0; j < temporal.size(); j++) {
            key = temporal.get(j);
            i = j - 1;

            while (i > 0 && (temporal.get(i).comparo(key) > 0)) {
                temporal.set(i + 1, temporal.get(i));
                i -= 1;
            }
            temporal.set(i + 1, key);
        }
        temporal.remove(0);
        return temporal;
    }

    void Imprimir(ArrayList<Bloque> n) {
         System.out.println("Sort");
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Orden :" + i + " x = " + n.get(i).x + " y = " + n.get(i).y + " padre " + n.get(i).getUltimoMovimiento() + " Costo " + n.get(i).getCosto());
        }
        System.out.println("FIN Sort");

    }

}