/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;

/**
 *
 * @author Mauro
 */
public class Bloque {

    public int contenido;
    public ArrayList<ArrayList> estado;   //Representa la matriz del juego para ese nodo
    public Coordenadas ubicacion;               //Indica en que punto de la matriz estado se encuentra el caballo
    public String tipo;                   //Puede ser MIN o MAX
    public String movimiento;             //Representa el movimiento que se realizó para llegar a ese estado
    public int profundidad;               //La profundidad del nodo
    public int utilidad;                  //La utilidad total en el algoritmo minimax
    public int puntosMovimiento;          //Representa los puntos que ganó al realizar ese movimiento
    public int padre;                     //Referencia al nodo padre
    public int puntosCaballoBlanco;       //Indica la cantidad de puntos que tiene la maquina en ese estado
    public int puntosCaballoNegro;       //Indica la cantidad de puntos que tiene el usuario en ese estado

    public Bloque() {
        estado = new ArrayList<>();
        movimiento = "";
        ubicacion = new Coordenadas(-1, -1);
        profundidad = 0;
        puntosCaballoNegro = 0;
        puntosCaballoBlanco = 0;
    }

    public Bloque(ArrayList<ArrayList> estado, String movimiento, int puntosMovimiento) {
        this.estado = estado;
        this.movimiento = movimiento;
        this.puntosMovimiento = puntosMovimiento;

        this.puntosCaballoNegro = 0;
        this.puntosCaballoBlanco = 0;

        tipo = "";
        padre = 0;
        profundidad = 0;
        utilidad = 0;
        ubicacion = new Coordenadas(-1, -1);
    }

    public void setEstado(ArrayList<ArrayList> estado) {
        this.estado = estado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public void setUbicacion(Coordenadas ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPuntosCaballoBlanco(int puntosCaballoBlanco) {
        this.puntosCaballoBlanco = puntosCaballoBlanco;
    }

    public void setPuntosCaballoDorado(int puntosCaballoNegro) {
        this.puntosCaballoNegro = puntosCaballoNegro;
    }

    public int getPuntosCaballoBlanco() {
        return puntosCaballoBlanco;
    }

    public int getPuntosCaballoNegro() {
        return puntosCaballoNegro;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public Coordenadas getUbicacion() {
        return ubicacion;
    }

}
