/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Recursos.IcoRecurso;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author mao
 */
public class Costo_uniforme {

    Bloque[][] matrix;
    private int premios;
    private int escudo;
    private ColaPriorida cola;
    private ArrayList<Bloque> solucion;
    private Bloque stdinicial;
    int costo_general;
    private ArrayList<Bloque> EntregaFinal;
    long time_start, time_end;

    public Costo_uniforme(Bloque[][] matrix, Bloque Inicial) {
        time_start = System.currentTimeMillis();
        this.EntregaFinal = new ArrayList<>();
        this.matrix = matrix.clone();
        this.solucion = new ArrayList<>();
        this.cola = new ColaPriorida();
        this.stdinicial = new Bloque();
        this.stdinicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.cola.push(Inicial);
        //this.solucion.add(Inicial);

    }

    public ArrayList<Bloque> BusquedaCostouniforme() {
        while (premios != 2) {
            if (cola.vacio()) {
                System.out.println("Error");
            }
            Bloque n = new Bloque();
            n = cola.pop();
            if (n.getContenido() != 1) {
                //Para evitar ciclos pendejos
                solucion.add(n);
                expandir(n);

            }
        }
        return EntregaFinal;
    }

    void expandir(Bloque entrada) {
        //Comprueba la izquierda
        izquierda(entrada);
        derecha(entrada);
        arriba(entrada);
        bajo(entrada);
    }

    Bloque izquierda(Bloque entrada) {

        Bloque salida = new Bloque();
        salida = null;
        if (entrada.x != 0) {

            /* para No realizar la expacion de su padre
             */
            if (!entrada.getUltimoMovimiento().equals("derecha")) {
                salida = matrix[(entrada.x - 1)][entrada.y];
                salida.setUltimoMovimiento("izquierda");
                salida.setPadre(entrada);
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!solucion.contains(salida)) {
                    if (salida.getContenido() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        solucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Bloque derecha(Bloque entrada) {
        Bloque p = new Bloque();
        p = entrada;
        Bloque salida = new Bloque();
        salida = null;
        if (entrada.x != 9) {
            /* para No realizar la expacion de su padre
             */
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("izquierda")) {
                salida = matrix[(entrada.x + 1)][entrada.y];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("derecha");
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!solucion.contains(salida)) {
                    if (salida.getContenido() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        solucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Bloque arriba(Bloque entrada) {
        Bloque salida = new Bloque();
        salida = null;
        if (entrada.y != 0) {
            /* para No realizar la expacion de su padre
             */
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("abajo")) {
                salida = matrix[(entrada.x)][(entrada.y - 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("arriba");
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!solucion.contains(salida)) {
                    if (salida.getContenido() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        solucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Bloque bajo(Bloque entrada) {
        Bloque salida = new Bloque();
        salida = null;
        if (entrada.y != 9) {
            /* para No realizar la expacion de su padre
             */
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("arriba")) {
                salida = matrix[(entrada.x)][(entrada.y + 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("abajo");
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!solucion.contains(salida)) {
                    if (salida.getContenido() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        solucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }
//Encuentra el objeto

    boolean ObtencionObjetos(Bloque intro) {
        boolean salida = false;
        Bloque Inicial = new Bloque();
        Inicial = intro;

        if (intro.getContenido() == 6) {
            premios += 1;
            // JOptionPane.showMessageDialog(null, " Encontro Premio X = " + intro.x + "  Y = " + intro.y, "Bateria", 1, IcoRecurso.ICON_BATERIA);
            ArrayList<Bloque> n;
            n = camino(intro);
            Collections.reverse(n);
            EntregaFinal.addAll(n);
            Imprimir(camino(intro));
            /*Codigo esperimental*/
            cola.clear();
            cola.push(intro);
            solucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.cola.push(Inicial);
            /*Fin de Codigo Experimental*/
            matrix[intro.x][intro.y].setContenido(0);
            salida = true;

        }
        if (intro.getContenido() == 3) {
            escudo += 1;
            //  JOptionPane.showMessageDialog(null, " Encontro escudo X = " + intro.x + "  Y = " + intro.y, "Bateria", 1, IcoRecurso.ICON_TRAJE);
            matrix[intro.x][intro.y].setContenido(0);
            ArrayList<Bloque> n;
            n = camino(intro);
            Collections.reverse(n);
            EntregaFinal.addAll(n);
            Imprimir(camino(intro));
            salida = true;
            /*Codigo esperimental*/
            cola.clear();
            cola.push(intro);
            solucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.cola.push(Inicial);
            /*Fin de Codigo Experimental*/
        }

        return salida;
    }

    void Imprimir(ArrayList<Bloque> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso:" + i + " x = " + n.get(i).x + " y = " + n.get(i).y + " padre " + n.get(i).getUltimoMovimiento() + " Costo " + n.get(i).getCosto());
        }
        if (premios == 2) {
            time_end = System.currentTimeMillis();
            time_end = (time_end - time_start);
            JOptionPane.showMessageDialog(null, "Numero de Nodos expandidos: " + solucion.size() + "\n" + "Profundidad del arbol: " + n.size() + "\n" + "Costo: " + costo_general+ "\n" + "Tiempo: "+time_end+" Milisegundos", "Informe", 1, IcoRecurso.ICON_INFORME);
        }
    }

    public ArrayList<Bloque> camino(Bloque input) {
        ArrayList<Bloque> salida = new ArrayList<>();
        costo_general = input.getCosto();
        Bloque nuevo = new Bloque();
        nuevo = input;
        salida.add(nuevo);
        int i = 0;
        // while ((!nuevo.raiz) || nuevo.getUltimoMovimiento().equals("raiz")) {
        while (true) {
            nuevo = nuevo.getPadre();
            //nuevo = buscarPadre(salida.get(index));
            salida.add(nuevo);
            if (nuevo.raiz) {
                break;
            }
            i++;
        }
        return salida;
    }

    /*Esta funcion retorna el costo*/
    private int Costo(Bloque hijo, Bloque padre) {
        int salida = 1;
        if (escudo == 0) {
            /*Campo de tipo 1*/
            if (hijo.getContenido() == 4) {
                salida = 3;
            }
            /*Campo de tipo 2*/
            if (hijo.getContenido() == 5) {
                salida = 6;
            }

        }
        salida += padre.getCosto();

        return salida;
    }

}