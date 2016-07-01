/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import Logica.Bloque;
import Logica.Coordenada;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauro
 */
public class Leer {

    public Coordenada inicio;
    public Bloque init;
    int contadorErrores = 0;
    int contador_id = 0;
    private ArrayList<Bloque> Objetivos;

    public Bloque[][] ReadFile() {
        this.Objetivos = new ArrayList<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br;
        Bloque newBloque;
        init = new Bloque();
        /*Creacion de matriz de bloques*/
        Bloque[][] matriz = new Bloque[10][10];

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(null);
            archivo = file.getSelectedFile();
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            int columna = 0;
            while ((linea = br.readLine()) != null) {
                //Esta funcion separa los caracteres presentes en las lineas
                StringTokenizer tokens = new StringTokenizer(linea);
                /*Este ciclo se encarga de llenar las filas*/
                for (int i = 0; i < 10; i++) {
                    /*Obtengo un valor*/
                    int primero = Integer.parseInt(tokens.nextToken());
                    //Almaceno el Valor en la Matriz
                    if (primero == 2) {
                        inicio = new Coordenada(i, columna);
                        Bloque entra = new Bloque(primero);
                        entra.x = i;
                        entra.y = columna;
                        entra.raiz = true;
                        entra.setIdentificador(contador_id);
                        init = entra;
                    }
                    if (primero == 6) {
                        inicio = new Coordenada(i, columna);
                        Bloque entra = new Bloque(primero);
                        entra.x = i;
                        entra.y = columna;
                        entra.setIdentificador(contador_id);
                        Objetivos.add(entra);
                    }
                    Bloque entra = new Bloque(primero);
                    entra.x = i;
                    entra.y = columna;
                    entra.setIdentificador(contador_id);
                    matriz[i][columna] = entra;
                    contador_id++;
                }
                columna++;
            }
            br.close();

        } catch (HeadlessException | IOException | NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error al leer archivo intentelo de nuevo", "Error de lectura " + contadorErrores, 0);
            if (contadorErrores < 3) {
                contadorErrores++;
                ReadFile();

            } else {
                JOptionPane.showMessageDialog(null, "El archivo de lectura no es el indicado \n Intentelo luego con la cabeza fria", "Error de lectura", 0);
            }
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                JOptionPane.showConfirmDialog(null, e2 + "Error de lectura de archivo");
            }
        }
        return matriz;
    }

    public ArrayList<Bloque> getObjetivos() {
        return Objetivos;
    }

    public void setObjetivos(ArrayList<Bloque> Objetivos) {
        this.Objetivos = Objetivos;
    }


}
