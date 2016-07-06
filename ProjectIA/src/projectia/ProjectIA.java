/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectia;

import Graficos.UsuarioVisual;
import Logica.Amplitud;
import Logica.Bloque;
import Logica.Coordenadas;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Mauro
 */
public class ProjectIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] TableroOriginal =  crearTablero();
        ArrayList<ArrayList> crearTablero = conversor(TableroOriginal);
        Amplitud amplitud = new Amplitud("Principiante");
        ArrayList<Bloque> juego = new ArrayList<>();
        Bloque raiz = new Bloque();
      //
        raiz.setEstado(crearTablero);
        raiz.setTipo("MAX");
        raiz.setUtilidad(0);
        Coordenadas donde_estoy = amplitud.encontrarUbicacionCaballo(crearTablero, "Blanco");
        System.out.println("++++++             Ubicación: X = " + donde_estoy.x + " Y = " + donde_estoy.y);
        raiz.setPadre(-1);
        //
        Bloque unNodo = new Bloque();
                unNodo= amplitud.calcularDecisionMinimax(raiz);
        
        
        
        
        
        
        
        System.out.println("Movimiento: " + unNodo.movimiento);
        System.out.println("Ganancia Movimiento: " + unNodo.puntosMovimiento);
        System.out.println("puntos maquina: " + unNodo.getPuntosCaballoBlanco());
        System.out.println("puntos jugador: " + unNodo.getPuntosCaballoNegro());
        System.out.println("Utilidad: " + unNodo.utilidad);
        System.out.println("Ubicación: X = " + unNodo.ubicacion.x + " Y = " + unNodo.ubicacion.y);
        System.out.println("********************");
        Coordenadas casillaActual = amplitud.encontrarUbicacionCaballo(crearTablero, "Blanco"); //El caballa blanco es el jugador de la maquina
        crearTablero = amplitud.actualizarMatriz(crearTablero, casillaActual, 0); //Eliminio la el contenido de la casilla donde estaba el caballo
        crearTablero = amplitud.actualizarMatriz(crearTablero, unNodo.ubicacion, 1); //Pongo el caballo Blancon en la nueva casilla
        unNodo.estado = crearTablero;
        unNodo.setPadre(-1);
        unNodo = amplitud.calcularDecisionMinimax(unNodo);
        
        imprimir(crearTablero);

    }

    public static int[][] crearTablero() {
        int[][] tableroEnteros = new int[8][8];
        int hierba = 0;
        int flor = 0;
        int blanco = 0;
        int negro = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tableroEnteros[i][j] = 0;
            }
        }

        while (hierba < 20 || flor < 5 || blanco < 1 || negro < 1) {

            if (hierba < 20) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna] == 0) {
                   
                    tableroEnteros[fila][columna] = 3;
                    hierba++;
                }
            }

            if (flor < 5) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna] == 0) {
                    tableroEnteros[fila][columna] = 4;
                    flor++;
                }
            }

            if (blanco < 1) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna] == 0) {
                    tableroEnteros[fila][columna] = 2;
                    blanco++;
                }
            }

            if (negro < 1) {
                int fila = (int) (Math.random() * 8);
                int columna = (int) (Math.random() * 8);
                if (tableroEnteros[fila][columna] == 0) {
                    tableroEnteros[fila][columna] = 1;
                    negro++;
                }
            }
        }
        /*Convierte la matrix de enteros en matriz de arraylist*/
        return tableroEnteros;
    }

    static ArrayList<ArrayList> conversor(int[][] tableroEnteros) {
        ArrayList<ArrayList> salida = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ArrayList<Integer> columna = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                columna.add(tableroEnteros[i][j]);
            }
            salida.add(columna);
        }
        imprimir(salida);
        return salida;

    }

    static void imprimir(ArrayList<ArrayList> salida) {
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
