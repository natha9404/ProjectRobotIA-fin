/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectia;

import Graficos.UsuarioVisual;


/**
 *
 * @author Mauro
 */
public class ProjectIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UsuarioVisual graficos = new UsuarioVisual();
        graficos.setVisible(true);
        /*
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
        
        imprimir(crearTablero);*/

    }


}
