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
public class Funcionalidades {

    public Funcionalidades() {
    }

    public Bloque[][] crearTablero() {
        Bloque[][] tableroEnteros = new Bloque[8][8];
        int hierba = 0;
        int flor = 0;
        int blanco = 0;
        int negro = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bloque bloque = new Bloque();
                bloque.setContenido(0);
                tableroEnteros[i][j] = bloque;
            }
        }

        while (hierba < 20 || flor < 5 || blanco < 1 || negro < 1) {

            if (hierba < 20) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna].getContenido() == 0) {
                    Bloque bloque = new Bloque();
                    bloque.setContenido(3);
                    tableroEnteros[fila][columna] = bloque;
                    hierba++;
                }
            }

            if (flor < 5) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna].getContenido() == 0) {
                    Bloque bloque = new Bloque();
                    bloque.setContenido(4);
                    tableroEnteros[fila][columna] = bloque;

                    flor++;
                }
            }

            if (blanco < 1) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna].getContenido() == 0) {
                    Bloque bloque = new Bloque();
                    bloque.setContenido(2);
                    tableroEnteros[fila][columna] = bloque;

                    blanco++;
                }
            }

            if (negro < 1) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna].getContenido() == 0) {
                    Bloque bloque = new Bloque();
                    bloque.setContenido(1);
                    tableroEnteros[fila][columna] = bloque;
                    negro++;
                }
            }
        }
        /*Convierte la matrix de enteros en matriz de arraylist*/
        return tableroEnteros;
    }

    public ArrayList<ArrayList> conversorToArraylist(Bloque[][] tableroEnteros) {
        ArrayList<ArrayList> salida = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ArrayList<Integer> columna = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                columna.add(tableroEnteros[i][j].getContenido());
            }
            salida.add(columna);
        }
        imprimir(salida);
        return salida;

    }

    public Bloque[][] conversorToBloque(ArrayList<ArrayList> tableroEnteros) {
        Bloque[][] salida = new Bloque[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bloque nodo = new Bloque();
                try {
                    nodo.setContenido((int) tableroEnteros.get(i).get(j));
                } catch (Exception ex) {
                    nodo.setContenido(0);
                }
                salida[i][j] = nodo;
            }
        }
        return salida;
    }

    public void imprimir(ArrayList<ArrayList> salida) {
        System.out.println("Pintando Matriz");
        for (int i = 0; i < salida.size(); i++) {
            //Filas
            for (int j = 0; j < salida.get(i).size(); j++) {
                System.out.print(" " + salida.get(j).get(i) + " |");

            }
            System.out.println("-");
        }
    }

}
