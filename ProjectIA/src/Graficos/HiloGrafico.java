/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.Bloque;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author mao
 */
public class HiloGrafico extends SwingWorker< Integer, Integer> {

    ArrayList<Bloque> entrada = new ArrayList<>();
    UsuarioVisual grafico;

    public HiloGrafico(ArrayList<Bloque> entrada, UsuarioVisual gui) {
        this.entrada = entrada;
        this.grafico = gui;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        Bloque[][] mapa = new Bloque[10][10];
        mapa = grafico.matrix;
        for (int i = 0; i < entrada.size(); i++) {
            try {
                mapa[entrada.get(i).getX()][entrada.get(i).getY()].setContenido(2);
                grafico.creacionBotones(mapa);
                Thread.sleep(1000);
                grafico.printmatrix(mapa);
                grafico.creacionBotones(mapa);
                mapa[entrada.get(i).getX()][entrada.get(i).getY()].setContenido(-1);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error en Tiempo de espera del Hilo de Ejecucion");
            }
        }
        return 0;
    }
}
