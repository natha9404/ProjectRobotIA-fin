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
public class Profundida {

    Bloque[][] matrix;
    private int premios;
    private int escudo;
    private Pila pila;
    private int Profundida = 0;
    private ArrayList<Bloque> EntregaFinal;
    private ArrayList<Bloque> solucion;
    //Donde se encuentra inicialmente robot
    private Bloque stdinicial;
    private int costo_general;
    long time_start, time_end;

    public Profundida(Bloque[][] matrix, Bloque Inicial) {

        time_start = System.currentTimeMillis();
        this.EntregaFinal = new ArrayList<>();
        this.matrix = matrix.clone();
        this.solucion = new ArrayList<>();
        this.pila = new Pila();
        this.stdinicial = new Bloque();
        this.stdinicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.pila.push(Inicial);
        //this.solucion.add(Inicial);

    }

    public ArrayList<Bloque> BusquedaProfindida() {
        while (premios != 2) {
            if (pila.vacio()) {
                System.out.println("Error");
            }
            Bloque n = new Bloque();
            n = pila.pop();
            if (n.getContenido() != 1) {
                //Para evitar ciclos pendejos
                solucion.add(n);
                if(camino(n).size()<5)
                {
                    expandir(n);
                   
                }
                else{
                    EntregaFinal = camino(n);
                    Collections.reverse(EntregaFinal);
                    break;
                }
                
                 
            }
        }
        
        return EntregaFinal;
    }

    void expandir(Bloque entrada) {
        //Comprueba la izquierda
        
        bajo(entrada);
        arriba(entrada);
        derecha(entrada);
        izquierda(entrada);
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

                if (!solucion.contains(salida)) {

                    if (salida.getContenido() != 1) {
                        int costo = Costo(salida, entrada);
                        salida.setCosto(costo);
                        System.out.println("Logica.Profundida.izquierda() tercera comparacion");
                        pila.push(salida);
                        solucion.add(salida);
                        ObtencionObjetos(salida);
                        solucion.add(salida);
                        System.out.println("Logica.Profundida.izquierda()" + solucion.get(solucion.size() - 1).getUltimoMovimiento());
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
                        
                        pila.push(salida);
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
                        pila.push(salida);
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
                        pila.push(salida);
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
            //  JOptionPane.showMessageDialog(null, " Encontro Premio X = " + intro.x + "  Y = " + intro.y, "Bateria", 1, IcoRecurso.ICON_BATERIA);
            ArrayList<Bloque> n;
            n = camino(intro);
            Collections.reverse(n);
            EntregaFinal.addAll(n);
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.pila.push(Inicial);
            Imprimir(camino(intro));
            //Codigo esperimental
            pila.clear();
            pila.push(intro);
            solucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.pila.push(Inicial);
            //Fin de Codigo Experimental
            matrix[intro.x][intro.y].setContenido(0);

            salida = true;

        }
        if (intro.getContenido() == 3) {
            escudo += 1;
            //JOptionPane.showMessageDialog(null, " Encontro escudo X = " + intro.x + "  Y = " + intro.y, "Bateria", 1, IcoRecurso.ICON_TRAJE);
            matrix[intro.x][intro.y].setContenido(0);
            ArrayList<Bloque> n;
            n = camino(intro);
            Collections.reverse(n);
            EntregaFinal.addAll(n);
            Imprimir(camino(intro));

            /*Codigo esperimental*/
            pila.clear();
            pila.push(intro);
            solucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.pila.push(Inicial);
            /*Fin de Codigo Experimental*/
            salida = true;
        }

        return salida;
    }

    void Imprimir(ArrayList<Bloque> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso:" + i + " x = " + n.get(i).x + " y = " + n.get(i).y + " padre " + n.get(i).getUltimoMovimiento() + " ID " + n.get(i).getIdentificador());
        }
        if (premios == 2) {
            time_end = System.currentTimeMillis();
            time_end = (time_end - time_start);
            JOptionPane.showMessageDialog(null, "Numero de Nodos expandidos: " + solucion.size() + "\n" + "Profundidad del arbol: " + Profundida + "\n" + "Costo: " + n.get(0).getCosto()+"\n" + "Tiempo: " + time_end + " Milisegundos", "Informe", 1, IcoRecurso.ICON_INFORME);
            ImprimirPila();
        }
    }

    public ArrayList<Bloque> camino(Bloque input) {
        costo_general += input.getCosto();
        ArrayList<Bloque> salida = new ArrayList<>();
        Bloque nuevo = new Bloque();
        nuevo = input;
        salida.add(nuevo);
        int i = 0;
        // while ((!nuevo.raiz) || nuevo.getUltimoMovimiento().equals("raiz")) {
        while (true) {
            nuevo = nuevo.getPadre();
            //nuevo = buscarPadre(salida.get(index));
            salida.add(nuevo);
            Profundida++;
            if (nuevo.raiz) {
                salida.add(nuevo);
                break;
            }
            i++;
        }
        return salida;
    }

    /*Esta funcion retorna el costo*/
    private int Costo(Bloque hijo, Bloque padre) {
        int salida = 1;
        costo_general += 1;
        if (escudo == 0) {
            /*Campo de tipo 1*/
            if (hijo.getContenido() == 4) {
                costo_general += 2; 
                salida = 3;
            }
            /*Campo de tipo 2*/
            if (hijo.getContenido() == 5) {
                costo_general +=5;
                salida = 6;
            }

        }
        salida += padre.getCosto();

        return salida;
    }

    private void ImprimirPila() {
        
        for (int i=0; i<pila.getCola().size(); i++)
        
        System.out.println("Pila" + pila.getCola().get(i).getX() + "," + pila.getCola().get(i).getY());
    
    }
}
