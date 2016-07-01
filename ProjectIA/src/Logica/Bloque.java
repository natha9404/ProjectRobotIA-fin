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
public class Bloque {

    private int contenido;
    private String ultimoMovimiento;
    private Bloque padre;
    private int identificador;
    public int x = 0;
    public int y = 0;
    public boolean raiz;
    private int costo;

    public Bloque() {
        this.contenido = 0;
        this.ultimoMovimiento = "";
        this.raiz = false;
        this.costo = 0;
    }

    public String getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(String ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    public Bloque(int contenido) {
        this.contenido = contenido;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bloque getPadre() {
        return padre;
    }

    public void setPadre(Bloque padre) {
        this.padre = padre;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int comparo(Bloque other) {
        int salida = 0;

        if (costo > other.getCosto()) {
            salida = 1;
        }
        if (costo < other.getCosto()) {
            salida = -1;
        }

        return salida;
    }
}
