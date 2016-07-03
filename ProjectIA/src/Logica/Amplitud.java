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
public class Amplitud {
    
 
    String nivel;                          //Puede ser Principiante, Amateur o Experto
    int profundidadLimite;                 //Indica la máxima profundidad del árbol Minimax dependiendo del nivel
    boolean termino;                       //Indica si el juego ha finalizado
    ArrayList<String> listadoMovimientos;  //Representa todos los movimientos que se pueden realizar con los caballos
    Coordenadas siguienteUbicacionMaquina;       //Es la posicion en donde debera jugar la maquina dependiendo de la utilidad
    ArrayList<Bloque> arbolMinimax;            //Es el arbol MinMax que se contuyo 
    
public Amplitud(String nivel)
    {

        termino = false;
        this.nivel = nivel;
        
        switch (nivel) 
        {
            case "Principiante":
                profundidadLimite = 2;
                break;
            case "Amateur":
                profundidadLimite = 4;
                break;
            case "Experto":
                profundidadLimite = 6;
                break;
        }
        
        listadoMovimientos = new ArrayList<>();
        
        listadoMovimientos.add("Arriba Izquierda");
        listadoMovimientos.add("Arriba Derecha");
        listadoMovimientos.add("Izquierda Arriba");
        listadoMovimientos.add("Izquierda Abajo");
        listadoMovimientos.add("Abajo Izquierda");
        listadoMovimientos.add("Abajo Derecha");
        listadoMovimientos.add("Derecha Arriba");
        listadoMovimientos.add("Derecha Abajo");
    }
    
    
    // ° ° ° ° ° ° ° ° ° ° ° ° ° ALGORITMO MINIMAX ° ° ° ° ° ° ° ° ° ° ° ° °
    
    
    //Método que se encarga de crear el árbol minimax
    public ArrayList<Bloque> minimax(Bloque nodoRaiz) 
    {  
        ArrayList<Bloque> listaArbolMinimax = new ArrayList<>();
        listaArbolMinimax.add(nodoRaiz);
        Bloque nodo;
        
        int profundidad = 0;
        int indiceInicio = 0;
        int indiceFin = 0;
        int padre = listaArbolMinimax.get(0).padre;
        
        while (profundidad <= profundidadLimite) 
        {
            ArrayList<Bloque> nodosHijos;
            indiceInicio = indiceFin;
            indiceFin = listaArbolMinimax.size();

            for (int i = indiceInicio; i < indiceFin; i++) 
            {
                if ((profundidad % 2) == 0) 
                {
                    nodosHijos = calcularPosiblesMovimientos(listaArbolMinimax.get(i).estado, "Blanco");
                    padre +=1;
                } 
                else 
                {
                    nodosHijos = calcularPosiblesMovimientos(listaArbolMinimax.get(i).estado, "Negro");
                    padre +=1;
                }

                for (int j = 0; j < nodosHijos.size(); j++) 
                {
                    nodo = nodosHijos.get(j);
                    nodo.setProfundidad(profundidad + 1); //La profundidad del nodo padre + 1
                    nodo.setPadre(padre);
                    nodo.setPuntosCaballoBlanco(listaArbolMinimax.get(nodo.padre).getPuntosCaballoBlanco() + nodo.getPuntosCaballoBlanco());
                    nodo.setPuntosCaballoDorado(listaArbolMinimax.get(nodo.padre).getPuntosCaballoNegro() + nodo.getPuntosCaballoNegro());

                    //Si profundidad es par, significa que el padre es un nodo MAX, por lo que su hijo es MIN
                    if ((profundidad % 2) == 0) 
                    {
                        nodo.setTipo("MIN");
                    } 
                    else 
                    {
                        nodo.setTipo("MAX");
                    }

                    if (nodo.profundidad <= profundidadLimite) 
                    {
                        listaArbolMinimax.add(nodo);
                    }
                }
            }
            
            profundidad++;
        }
        

        return listaArbolMinimax;
    }
    
    
    
    //Método que Sube las utilidades a los nodos padre para encontrar la solución del algoritmo Minimax
    public Bloque calcularDecisionMinimax(Bloque nodoRaiz)
    {
        arbolMinimax = minimax(nodoRaiz);
        ArrayList<Bloque> nodosParaAnalizar = new ArrayList<>();
        int profundidad = profundidadLimite;
        Bloque jugada  = new Bloque();
        int tamMaximo = arbolMinimax.size()-1;
        System.out.println("Tamaño Max: " + tamMaximo);
        
        for (int i = tamMaximo; i > 0; i--)  //Si entra al for pero no a los if
        {
            if (arbolMinimax.get(i).profundidad == profundidad && arbolMinimax.get(i).tipo.equals("MAX"))
            {
                nodosParaAnalizar.add(arbolMinimax.get(i));
                
                tamMaximo--;
                if(!(arbolMinimax.get(tamMaximo).profundidad == profundidad))
                {
                    calcularMAX(nodosParaAnalizar);
                    profundidad--;
                    nodosParaAnalizar.clear();
                }
            }
            
            else if (arbolMinimax.get(i).profundidad == profundidad && arbolMinimax.get(i).tipo.equals("MIN"))
            {
                tamMaximo--;
                nodosParaAnalizar.add(arbolMinimax.get(i));
                if(arbolMinimax.get(i-1).profundidad != profundidad)
                {
                    calcularMIN(nodosParaAnalizar);
                    profundidad--;
                    nodosParaAnalizar.clear();
                }
            }
            
            if(profundidad == 0)
            {
                jugada = movimientoMaquina(arbolMinimax);
            }
        }
        
        return jugada;
    }
    
    
    /**
     * Se busca obtener dos cosas:
     * 1. Calcular la utilidad de los nodos MAX cuando estos son hojas
     * 2. Obtener la utilidad menor que se le ha de pasar al nodo MIN
     * @param nodosMAX 
     */
    private void calcularMAX(ArrayList<Bloque> nodosMAX) 
    {
        Bloque nodo;
        Collections.reverse(nodosMAX);
        int limite = arbolMinimax.size() - nodosMAX.size();
        
        for (int i = arbolMinimax.size()-1; i >= limite; i--)
        {
            if(arbolMinimax.get(i).utilidad == 0)
                arbolMinimax.get(i).setUtilidad(arbolMinimax.get(i).getPuntosCaballoBlanco() - arbolMinimax.get(i).getPuntosCaballoNegro());
            
        }
        
        nodo = nodosMAX.get(0);
        
        for (int i = 1; i < nodosMAX.size(); i++) 
        {
            if(nodo.utilidad > nodosMAX.get(i).utilidad)
            {
                if(nodo.padre == nodosMAX.get(i).padre)
                {
                    nodo = nodosMAX.get(i);
                }
            }
            
            if(nodo.padre != nodosMAX.get(i).padre) 
            {
                nodo = nodosMAX.get(i);
            }
            
            arbolMinimax.get(nodo.padre).utilidad = nodo.utilidad;
        }
    }
    
    
    
    /**
     * Se busca calcular la utilidad mas grande que se le va a asignar a nodo MAX 
     * @param nodosMIN 
     */
    private void calcularMIN(ArrayList<Bloque> nodosMIN) 
    {
        Bloque nodo = nodosMIN.get(0);
        
        for (int i = 1; i < nodosMIN.size(); i++) 
        {
            if(nodo.utilidad < nodosMIN.get(i).utilidad)
            {
                if(nodo.padre == nodosMIN.get(i).padre)
                {
                    nodo = nodosMIN.get(i);
                }
            }
            
            if(nodo.padre != nodosMIN.get(i).padre) 
            {
                nodo = nodosMIN.get(i);
            }
            
            arbolMinimax.get(nodo.padre).utilidad = nodo.utilidad;
        }
    }
    
    /**
     * Busca en la lista de nodos MAX-MIN el primer nodo MAX que tenga la misma
     * utilidad que el nodo raiz
     * @param lista
     * @return el nodo hacia donde se tiene que mover el jugador Maquina
     */
    private Bloque movimientoMaquina(ArrayList<Bloque> lista) 
    {
        Bloque jugada = new Bloque();
        
        for (int i = 1; i < 9; i++)
        {
            if (lista.get(i).utilidad == lista.get(0).utilidad) 
            {
                return jugada = lista.get(i);
            }
        }
        
        return jugada;
    }
    
    
    // ° ° ° ° ° ° ° ° ° ° ° ° ° MÉTODOS DE MOVIMIENTO ° ° ° ° ° ° ° ° ° ° ° ° °
    

    //Retorna un nodo con el nuevo estado y la ganancia si se pudo realizar el movimiento dado como parámetro.
    //Si no se pudo realizar el movimiento, retorna un nodo sin estado ni movimiento
    public Bloque mover(ArrayList<ArrayList> estado, String movimiento, String colorCaballo)
    {
        Bloque nuevoBloque = new Bloque();
        Coordenadas ubicacion = encontrarUbicacionCaballo(estado, colorCaballo);
        int x = 0; int y = 0;
        int codigoCaballo = 1; //Si es Caballo Blanco
        if(colorCaballo.equals("Negro"))
            codigoCaballo = 2;
        
        
        switch(movimiento)
        {
            case "Arriba Izquierda":
            {
                x = -2;
                y = -1;
                break;
            }
            case "Arriba Derecha":
            {
                x = -2;
                y = 1;
                break;
            }
            case "Izquierda Arriba":
            {
                x = -1;
                y = -2;
                break;
            }
            case "Izquierda Abajo":
            {
                x = 1;
                y = -2;
                break;
            }
            case "Abajo Izquierda":
            {
                x = 2;
                y = -1;
                break;
            }
            case "Abajo Derecha":
            {
                x = 2;
                y = 1;
                break;
            }
            case "Derecha Arriba":
            {
                x = -1;
                y = 2;
                break;
            }
            case "Derecha Abajo":
            {
                x = 1;
                y = 2;
                break;
            } 
        }
        
        Coordenadas nuevaUbicacion = new Coordenadas(ubicacion.x+x, ubicacion.y+y);
    
        //Verificamos que el punto esté dentro del tablero
        if( (nuevaUbicacion.x >= 0 && nuevaUbicacion.x <= 7) && (nuevaUbicacion.y >= 0 && nuevaUbicacion.y <= 7) )
        {
           
            //Evaluamos si la nueva casilla es una flor, un césped, un caballo o está vacía
            int valorCasilla = encontrarValorCasilla(estado, nuevaUbicacion);
            
            
            //Si no hay ningún caballo en la nueva casilla
            if(valorCasilla != 1 && valorCasilla !=2)
            {
                ArrayList<ArrayList> nuevoEstado = actualizarMatriz(estado, nuevaUbicacion, codigoCaballo);
                
                int puntosMovimiento = 0;
                if(valorCasilla == 3)
                    puntosMovimiento = 1;
                else if(valorCasilla == 4)
                    puntosMovimiento = 3;
                
                nuevoBloque = new Bloque(nuevoEstado, movimiento, puntosMovimiento);
                
                nuevoBloque.setUbicacion(nuevaUbicacion);
                
                
                // esto es para darle los punto a cada jugador en su movimiento (lo puso Diego)
                if(colorCaballo.equals("Negro"))
                    nuevoBloque.setPuntosCaballoDorado(puntosMovimiento);
                else
                    nuevoBloque.setPuntosCaballoBlanco(puntosMovimiento);
                    
            }
        }
  
        return nuevoBloque;
    }
    
    
    
    //Método que calcula que movimientos puede realizar un caballo según un estado del tablero
    ArrayList<Bloque> calcularPosiblesMovimientos(ArrayList<ArrayList> estado, String colorCaballo)
    {
        ArrayList<Bloque> posiblesMovimientos = new ArrayList<>();
        
        //Intentamos mover el caballo en todas las direcciones posibles.
        for(int i = 0; i < 8; i++) 
        {
            Bloque nodo = mover(estado, listadoMovimientos.get(i), colorCaballo);
            
            if(nodo.movimiento.equals("") == false)
                posiblesMovimientos.add(nodo);   
        }

        
        return posiblesMovimientos;
    }
    
    
    // ° ° ° ° ° ° ° ° ° ° ° ° ° MÉTODOS AUXILIARES ° ° ° ° ° ° ° ° ° ° ° ° °
    
    
    //Actualiza el valor de un punto (x,y) en la matriz
    public ArrayList<ArrayList> actualizarMatriz(ArrayList<ArrayList> matriz, Coordenadas casilla, int nuevoValor)
    {
        ArrayList<ArrayList> nuevaMatriz = new ArrayList<>();
        
        for(int i = 0; i < matriz.size(); i++)
        {
            ArrayList<Integer> nuevaFila = new ArrayList<>();
            
            if(i == casilla.x)
            {
                for(int j = 0; j < matriz.get(i).size(); j++)
                {
                    int nuevaCasilla = (Integer) matriz.get(i).get(j); 
                    
                    if(j == casilla.y)
                    {
                        nuevaCasilla = nuevoValor;
                        nuevaFila.add(nuevaCasilla);
                    }
                    else 
                        nuevaFila.add(nuevaCasilla);
                }
                
                nuevaMatriz.add(nuevaFila);
            }
            else
            {
                nuevaFila = matriz.get(i);
                nuevaMatriz.add(nuevaFila); 
            }
       
        }
        return nuevaMatriz;
    }
    
    
    //Retorna la casilla de la matriz dada en donde se encuentra el caballo
    public Coordenadas encontrarUbicacionCaballo(ArrayList<ArrayList> matriz, String colorCaballo)
    {
        Coordenadas ubicacion = new Coordenadas (-1,-1);
        
        int codigoCaballo = 1; //Si es Caballo Blanco
        if(colorCaballo.equals("Negro"))
            codigoCaballo = 2;
        
        
        for (int i = 0; i < 8; i++) 
        {
            for(int j = 0; j < 8; j++)
            {
                int casilla = (Integer) matriz.get(i).get(j);
                
                if(casilla == codigoCaballo)
                    ubicacion = new Coordenadas(i, j);
            }
        }
        
        return ubicacion;
    }
    
    
    //Método que retorna el valor de la casilla respectiva en la matriz dada
    public int encontrarValorCasilla(ArrayList<ArrayList> matriz, Coordenadas casilla)
    {
        int valor = 0;
        
        for (int i = 0; i < 8; i++) 
        {
            if(i == casilla.x)
            {
                for(int j = 0; j < 8; j++)
                {
                    if(j == casilla.y)
                        valor = (Integer) matriz.get(i).get(j);
                }
            }
        }
        
        return valor;
        
    }
    
    
    
    
    // ° ° ° ° ° ° ° ° ° ° ° ° °SETTERS & GETTERS ° ° ° ° ° ° ° ° ° ° ° ° °
    
}
