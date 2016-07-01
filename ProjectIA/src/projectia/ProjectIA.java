/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectia;

import Archivo.Leer;
import Graficos.UsuarioVisual;
import Logica.Bloque;
import java.awt.Color;

/**
 *
 * @author Mauro
 */
public class ProjectIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Instancion la interface grafica
        UsuarioVisual visualizacion = new UsuarioVisual();
        //Hago visible al Usuario
        visualizacion.setVisible(true);
        /*Prueba de lectura del archivo plano por consola*/
/*
        Leer lectura = new Leer();
        Bloque[][] matrix = lectura.ReadFile();
        //Columnas}
        System.out.println("Pintando Matriz");
        for (int i = 0; i < matrix.length; i++) {
            //Filas
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(" "+ matrix[j][i].getIdentificador()+" |");

            }
            System.out.println("-");
        }*/
    }

}
