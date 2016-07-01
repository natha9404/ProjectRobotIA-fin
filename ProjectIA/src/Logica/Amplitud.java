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
public class Amplitud {

    Bloque[][] matrix;
    private int premios;
    private int escudo;
    private Cola cola;
    private ArrayList<Bloque> solucion;
    private Bloque stdinicial;
    int costo_general;
    long time_start, time_end;
    int profundida = 0;
    int profundida_arbol;
    Bloque MejorOpcion = new Bloque();

    private ArrayList<Bloque> EntregaFinal;

    public Amplitud(Bloque[][] matrix, Bloque Inicial, int profundidad_arbol) {
        time_start = System.currentTimeMillis();
        this.profundida_arbol = profundidad_arbol;
        this.EntregaFinal = new ArrayList<>();
        this.matrix = matrix.clone();
        this.solucion = new ArrayList<>();
        this.cola = new Cola();
        this.stdinicial = new Bloque();
        this.stdinicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.cola.push(Inicial);
    }

    public ArrayList<Bloque> BusquedaAmplitud() {
       while (true) {
            if (cola.vacio()) {
                System.out.println("Error");
            }
            Bloque n = new Bloque();
            n = cola.pop();
            System.out.println("Expandir: " + profundida);
            expandir(n);
            //Para evitar ciclos pendejos

            profundida = camino(n).size();
            if(camino(n).size() == 5){
                System.out.println("Paro con profundidad");
                Imprimir(camino(n));
                break;
            }

        }

        return EntregaFinal;
    }

    void expandir(Bloque entrada) {
        mover(entrada);
        //Comprueba la izquierda
        //izquierda(entrada);
        // derecha(entrada);
        // arriba(entrada);
        // bajo(entrada);
    }

    public void mover(Bloque padre) {
        int columna = padre.x;
        int fila = padre.y;
        //La idea de los nombres que pongo debajo va deacuerdo a esto:
        //XXXXXXXXXX
        //XXXX-X-XXX Aqui estan las posiciones superior izquierda arriba y superior derecha arriba
        //XXX-XXX-XX Aqui estan las posiciones superior izquierda abajo y superior derecha abajo
        //XXXXX+XXXX Aqui esta el caballo
        //XXX-XXX-XX Aqui estan las posiciones inferior izquierda arriba e inferior derecha arriba
        //XXXX-X-XXX Aqui estan las posiciones inferior izquierda abajo e inferior derecha abajo
        //XXXXXXXXXX
        //XXXXXXXXXX
        //XXXXXXXXXX
        //XXXXXXXXXX
        //superior izquierda abajo
        if (fila - 1 >= 0 && columna - 2 >= 0) {
            Bloque nuevo = new Bloque();
            nuevo = matrix[fila - 1][columna - 2];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("superior izquierda abajo");
            }
        }
        //superior izquierda arriba
        if (fila - 2 >= 0 && columna - 1 >= 0) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila - 2][columna - 1];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("superior izquierda arriba");
            }
        }

        //superior derecha abajo
        if (fila - 1 >= 0 && fila - 1 <= 7 && columna + 2 >= 0 && columna + 2 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila - 1][columna + 2];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("superior derecha abajo");
            }
        }
        //superior derecha arriba
        if (fila - 2 >= 0 && fila - 2 <= 7 && columna + 1 >= 0 && columna + 1 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila - 2][columna + 1];
            nuevo.setPadre(padre);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("superior derecha arriba");
            }
        }

        //inferior izquierda abajo
        if (fila + 2 >= 0 && fila + 2 <= 7 && columna - 1 >= 0 && columna - 1 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila + 2][columna - 1];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("inferior izquierda abajo");
            }
        }
        //inferior izquierda arriba
        if (fila + 1 >= 0 && fila + 1 <= 7 && columna - 2 >= 0 && columna - 2 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila + 1][columna - 2];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("  //inferior izquierda arriba\n");
            }
        }

        //inferior derecha abajo
        if (fila + 2 <= 7 && columna + 1 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila + 2][columna + 1];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("inferior derecha abajo");
            }
        }
        //inferior derecha arriba
        if (fila + 1 <= 7 && columna + 2 <= 7) {

            Bloque nuevo = new Bloque();
            nuevo = matrix[fila + 1][columna + 2];
            nuevo.setPadre(padre);
            int costo = Costo(nuevo, padre);
            nuevo.setCosto(costo);
            if (!solucion.contains(nuevo)) {
                cola.push(nuevo);
                ObtencionObjetos(nuevo);
                solucion.add(nuevo);
                System.out.println("inferior derecha arriba");
            }
        }
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

        if (intro.getContenido() == 3) {
            intro.setContenido(0);
        }
        if (intro.getContenido() == 1) {
            intro.setContenido(0);
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
            System.out.println("Holaaa2");
            JOptionPane.showMessageDialog(null, "Numero de Nodos expandidos: " + solucion.size() + "\n" + "Profundidad del arbol: " + n.size() + "\n" + "Costo: " + costo_general + "\n" + "Tiempo: " + time_end + " Milisegundos", "Informe", 1, IcoRecurso.ICON_INFORME);
            ImprimirCola();
            System.out.println("Holaaa");
        }
    }

    public ArrayList<Bloque> camino(Bloque input) {
        costo_general = input.getCosto();
        ArrayList<Bloque> salida = new ArrayList<>();
        Bloque nuevo = new Bloque();
        nuevo = input;
        salida.add(nuevo);
        int costosumatoria = 0;
        while (true) {
            nuevo = nuevo.getPadre();
            salida.add(nuevo);
            costosumatoria += nuevo.getCosto();
            if (nuevo.raiz) {
                break;
            }
        }
        System.out.println(" Costo de Utilidad " + costosumatoria + " \n Profundidad " + salida.size());

        return salida;
    }

    public void ImprimirCola() {

        for (int i = 0; i < cola.getCola().size(); i++) {

            System.out.println("Nodos sin expandir" + cola.getCola().get(i).getX() + "," + cola.getCola().get(i).getY());

        }

    }

    /*Esta funcion retorna el costo*/
    private int Costo(Bloque hijo, Bloque padre) {
        int salida = 1;

        /*Campo de tipo flor*/
        if (hijo.getContenido() == 3) {
            salida = 3;
        }
        /*Campo de pasto*/
        if (hijo.getContenido() == 1) {
            salida = 1;
        }
        salida += padre.getCosto();
        return salida;
    }

}
