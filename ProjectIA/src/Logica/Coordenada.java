/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author Mauro
 */
public class Coordenada {

    private int iniciox = 0;
    private int inicioy = 0;

    public Coordenada(int x, int y) {
        this.iniciox = x;
        this.inicioy = y;
    }

    public Coordenada() {
    }

    public int getIniciox() {
        return iniciox;
    }

    public void setIniciox(int iniciox) {
        this.iniciox = iniciox;
    }

    public int getInicioy() {
        return inicioy;
    }

    public void setInicioy(int inicioy) {
        this.inicioy = inicioy;
    }

}
