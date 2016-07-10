/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.Coordenadas;
import javax.swing.JButton;

/**
 *
 * @author mao
 */
public class Boton extends JButton{
    
    Coordenadas cordenada = new Coordenadas();

    public Coordenadas getCordenada() {
        return cordenada;
    }

    public void setCordenada(Coordenadas cordenada) {
        this.cordenada = cordenada;
    }

    void setBackground(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
