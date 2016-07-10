/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.Bloque;
import Logica.Amplitud;
import Logica.Coordenadas;
import Logica.Funcionalidades;
import java.awt.GridLayout;
import Recursos.IcoRecurso;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauro
 */
public final class UsuarioVisual extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form UsuarioVisual
     */
    Coordenadas caballoSegundo = new Coordenadas();
    ArrayList<Bloque> objetivos;
    public Bloque[][] matrix;
    public Bloque[][] matrixGrafico;
    Bloque init = new Bloque();
    Funcionalidades funciones;
    int puntosBlanco;
    int puntosNegro;
    boolean turnoHumano = false;
    boolean termino = false;
    ArrayList<ArrayList> matrizDeJuego = new ArrayList<>();
    Amplitud minimax;
    ArrayList<Coordenadas> temporalUbicaciones = new ArrayList<>(); //Almacena las ubicaciones tempores que posterior mente se eliminaran para que la maquina no las tome como posibilidades
    int contadornoRepita = 0;

    public UsuarioVisual() {
        initComponents();
        //Funcines para el Inicio del Mapa
        funciones = new Funcionalidades();
        matrix = funciones.crearTablero();
        matrizDeJuego = (ArrayList<ArrayList>) funciones.conversorToArraylist(matrix).clone();
        this.objetivos = new ArrayList<>();
        creacionBotones(matrix);
        this.repaint();
        this.setSize(580, 620);
        this.setResizable(false);

    }
    //Método que verifica si el juego termino

    private void terminoJuego() {
        if (puntosBlanco >= 17 || puntosNegro >= 18) {
            termino = true;
        }
    }
//Pinta los campos disponibles para mover el usuario

    private void pintarcuadro(Coordenadas ubicacion) {
        matrix[ubicacion.x][ubicacion.y].setColor(true);
    }

    private void resaltarPosiblesMovimientos() {
        ArrayList<Bloque> posiblesMovimientos = new ArrayList<>();
        posiblesMovimientos = minimax.calcularPosiblesMovimientos(matrizDeJuego, "Negro");
        for (int i = 0; i < posiblesMovimientos.size(); i++) {
            Coordenadas coordenada = new Coordenadas();
            coordenada = posiblesMovimientos.get(i).ubicacion;
            pintarcuadro(coordenada);
        }
        creacionBotones(matrix);
    }

    private void DeleteNoRepita() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Bloque nodo = new Bloque();
                nodo = matrix[i][j];
                if (nodo.getContenido() == -9) {
                    matrix[i][j].setContenido(0);
                }

            }

        }
        matrizDeJuego = funciones.conversorToArraylist(matrix);

    }

    private void noRepita(Coordenadas ubicacion) {
        //Esta funcion inavilita por dos turnos las casillas corridas por el caballo blanco la maquina
        if (!turnoHumano) {

            temporalUbicaciones.add(ubicacion);
            cargarNorepite();

            if (temporalUbicaciones.size() == 4) {
                System.out.println("Reinicio Las repeticines ++++++++++++++ +++++++++++++++++++");
                contadornoRepita = 0;
                temporalUbicaciones.clear();
            }
            System.out.println("Graficos.UsuarioVisual.noRepita() Contador =" + contadornoRepita);
            contadornoRepita++;

        } else {
            DeleteNoRepita();
        }
    }

    void cargarNorepite() {
        for (int i = 0; i < temporalUbicaciones.size(); i++) {
            int prueba = 0;
            try {
                prueba = (int) matrizDeJuego.get(temporalUbicaciones.get(i).x).get(temporalUbicaciones.get(i).y);
            } catch (Exception e) {
                prueba = 0;
            }

            if (prueba != 1 && prueba != 2) {
                matrizDeJuego = minimax.actualizarMatriz(matrizDeJuego, temporalUbicaciones.get(i), -9);
                funciones.imprimir(matrizDeJuego);
            }
        }
    }

    //Método que mueve un caballo en la dirección dada
    public void moverEnL(String colorCaballo, Bloque nuevaJugada) {
        Coordenadas casillaActual = minimax.encontrarUbicacionCaballo(matrizDeJuego, colorCaballo);

        //colorearCasilla(casillaActual, 0);
        //colorearCasilla(nuevaJugada.ubicacion, codigoCaballo);
        puntosBlanco += nuevaJugada.getPuntosCaballoBlanco();
        puntosNegro += nuevaJugada.getPuntosCaballoNegro();
        matrizDeJuego = nuevaJugada.estado;

        if (colorCaballo.equals("Blanco")) {

            System.out.println("Matriz antes de la decicion Minimas");
            cargarNorepite();
            funciones.imprimir(matrizDeJuego);
            matrizDeJuego = minimax.actualizarMatriz(matrizDeJuego, casillaActual, 0);
            noRepita(casillaActual);
            // DeleteNoRepita();

        }
        jLabelPuntosMaquina.setText(Integer.toString(puntosBlanco));
        jLabelPuntosHumano.setText(Integer.toString(puntosNegro));
        matrix = funciones.conversorToBloque((ArrayList<ArrayList>) matrizDeJuego.clone());
        creacionBotones(matrix);
    }

    private void bugDobleCaballo() {
        /*Corriege el error de la aparicion de un segundo caballo negro*/
        if (caballoSegundo.x != -1 || caballoSegundo.y != -1) {
            /*Mauro Castillo Si aun no se a dectectado una escucha del usuario no haga nada*/
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (matrix[i][j].contenido == 2 && i != caballoSegundo.x && j != caballoSegundo.y) {
                        System.out.println("Graficos.UsuarioVisual.bugDobleCaballo() Encontro y corrigio el bug I =" + i + " J = " + j + " Cordenada (" + caballoSegundo.x + "," + caballoSegundo.y + ")");
                        matrix[i][j].contenido = 0;
                    }
                }

            }
            matrizDeJuego = funciones.conversorToArraylist(matrix);
        }
    }

    private void jugada() {

        int contadorDeJugadas = 0;

        while (contadorDeJugadas < 2) {
            bugDobleCaballo();
            if ((contadorDeJugadas % 2) == 0) //Si el contador de jugadas es par, es el turno del caballo blanco
            {
                if (termino) {
                    if (puntosBlanco < puntosNegro) {
                        JOptionPane.showMessageDialog(this, "Ganador", "Termino El juego", 1, IcoRecurso.ICON_WiNNER);
                    } else {
                        JOptionPane.showMessageDialog(this, "Perdiste", "Termino el Juego", 0, IcoRecurso.ICON_LIKE);
                    }
                    break;
                }

                turnoHumano = false;
                Bloque nodoRaiz = new Bloque();
                nodoRaiz.setEstado(matrizDeJuego);
                nodoRaiz.setTipo("MAX");
                nodoRaiz.setUtilidad(0);
                nodoRaiz.setPadre(-1);

                Bloque unNodo = minimax.calcularDecisionMinimax(nodoRaiz);

                moverEnL("Blanco", unNodo);
            } else //Si el contador de jugadas es impar, es el turno del caballo dorado
            {
                if (termino) {
                    System.out.println("Termine desde JugarPartida - Else");

                    if (puntosBlanco < puntosNegro) {
                        JOptionPane.showMessageDialog(this, "Ganador", "Termino El juego", 1, IcoRecurso.ICON_WiNNER);
                    } else {
                        JOptionPane.showMessageDialog(this, "Perdiste", "Termino el Juego", 0, IcoRecurso.ICON_LIKE);
                    }

                    break;
                }

                turnoHumano = true;
                resaltarPosiblesMovimientos();
            }

            contadorDeJugadas++;
            terminoJuego();
        }
    }

    //Diseñada para generar los iconos
    private ImageIcon IconoMapa(Bloque bloque) {
        ImageIcon imagen = new ImageIcon();
        //Poner condicionales para elementos graficos
        if (bloque.getContenido() == 0) {
            //VACIO
            imagen = IcoRecurso.ICON_VACIO;
        }
        if (bloque.getContenido() == 2) {
            //CABALLO NEGRO
            imagen = IcoRecurso.ICON_CABALLO_NEGRO;
        }
        if (bloque.getContenido() == 1) {
            //CABALLO BLANCO
            imagen = IcoRecurso.ICON_CABALLO_BLANCO;
        }
        if (bloque.getContenido() == 3) {
            //CESPED
            imagen = IcoRecurso.ICON_CESPED;
        }
        if (bloque.getContenido() == 4) {
            //FLOR
            imagen = IcoRecurso.ICON_FLOR;
        }
        return imagen;
    }

    /*Funcion diseñada para llenar el JPmapa de Botones*/
    //Como se usan los arreglos de bloques si el programa suelta array de array con enteros?
    public void creacionBotones(Bloque[][] matrix) {

        jPmapa.removeAll();
        jPmapa.setLayout(new GridLayout(8, 8));

        //*AQUI CREO LOS BOTONES CON TAMAÑO 8 -- A que se refieren con tamaño 8?
        Boton[][] MatFichas = new Boton[8][8];
        for (int PosY = 0; PosY < 8; PosY++) {
            for (int PosX = 0; PosX < 8; PosX++) {
                MatFichas[PosX][PosY] = new Boton();
                /*Agrego Coordenadas*/
                Coordenadas ubicacion = new Coordenadas(PosX, PosY);
                MatFichas[PosX][PosY].setCordenada(ubicacion);
                MatFichas[PosX][PosY].setBackground(Color.white);
                /*Fin de insertcion de coordenadas*/
                //Escucha del Boton
                MatFichas[PosX][PosY].addActionListener(this);
                //Icono del Boton
                MatFichas[PosX][PosY].setIcon(IconoMapa(matrix[PosX][PosY]));
                if (matrix[PosX][PosY].isColor()) {
                    MatFichas[PosX][PosY].setBackground(Color.ORANGE);
                }
                jPmapa.add(MatFichas[PosX][PosY]);

            }
        }

        jPmapa.updateUI();
        jPmapa.revalidate();
        jPmapa.repaint();

    }

    void printmatrix(Bloque[][] matrix) {
        System.out.println("Pintando Matriz");
        for (int i = 0; i < matrix.length; i++) {
            //Filas
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(" " + matrix[j][i].getContenido() + " |");
            }
            System.out.println("-");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jBbuscar = new javax.swing.JButton();
        jPBanner = new javax.swing.JPanel();
        jPmapa = new javax.swing.JPanel();
        jLTitle = new javax.swing.JLabel();
        jComboBoxDificultad = new javax.swing.JComboBox();
        jLabelDificultad = new javax.swing.JLabel();
        jLabelMaquina = new javax.swing.JLabel();
        jLabelHumano = new javax.swing.JLabel();
        jLabelPuntosMaquina = new javax.swing.JLabel();
        jLabelPuntosHumano = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBbuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBbuscar.setFont(new java.awt.Font("Candara", 1, 18)); // NOI18N
        jBbuscar.setText("Empezar el juego");
        jBbuscar.setContentAreaFilled(false);
        jBbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBbuscarActionPerformed(evt);
            }
        });

        jPBanner.setBackground(new java.awt.Color(204, 255, 204));

        jPmapa.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPmapaLayout = new javax.swing.GroupLayout(jPmapa);
        jPmapa.setLayout(jPmapaLayout);
        jPmapaLayout.setHorizontalGroup(
            jPmapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );
        jPmapaLayout.setVerticalGroup(
            jPmapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );

        jLTitle.setFont(new java.awt.Font("Chiller", 1, 48)); // NOI18N
        jLTitle.setForeground(new java.awt.Color(255, 102, 153));
        jLTitle.setText("Hungry horses 1.0");

        javax.swing.GroupLayout jPBannerLayout = new javax.swing.GroupLayout(jPBanner);
        jPBanner.setLayout(jPBannerLayout);
        jPBannerLayout.setHorizontalGroup(
            jPBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBannerLayout.createSequentialGroup()
                .addGroup(jPBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPBannerLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLTitle))
                    .addGroup(jPBannerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPmapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPBannerLayout.setVerticalGroup(
            jPBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBannerLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPmapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        jComboBoxDificultad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jComboBoxDificultad.setForeground(new java.awt.Color(255, 51, 255));
        jComboBoxDificultad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Principiante", "Amateur", "Experto" }));

        jLabelDificultad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDificultad.setText("Seleccione un nivel de dificultad:");

        jLabelMaquina.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelMaquina.setForeground(new java.awt.Color(102, 102, 255));
        jLabelMaquina.setText("Máquina:");

        jLabelHumano.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelHumano.setForeground(new java.awt.Color(255, 51, 51));
        jLabelHumano.setText("Humano:");

        jLabelPuntosMaquina.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelPuntosMaquina.setForeground(new java.awt.Color(51, 51, 255));
        jLabelPuntosMaquina.setText("0");

        jLabelPuntosHumano.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelPuntosHumano.setForeground(new java.awt.Color(255, 51, 51));
        jLabelPuntosHumano.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelMaquina)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelPuntosMaquina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBbuscar)
                        .addGap(95, 95, 95))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelHumano)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelPuntosHumano))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelDificultad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDificultad)
                    .addComponent(jComboBoxDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMaquina)
                    .addComponent(jLabelPuntosMaquina)
                    .addComponent(jBbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHumano)
                    .addComponent(jLabelPuntosHumano))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBbuscarActionPerformed
        jBbuscar.setEnabled(false);
        JOptionPane.showMessageDialog(this, "No tiene sentido que lo intentes igual perderas", "Que empiece el Juego", 0);
        minimax = new Amplitud((String) jComboBoxDificultad.getSelectedItem());
        jugada();
    }//GEN-LAST:event_jBbuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsuarioVisual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioVisual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioVisual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioVisual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsuarioVisual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBbuscar;
    private javax.swing.JComboBox jComboBoxDificultad;
    private javax.swing.JLabel jLTitle;
    private javax.swing.JLabel jLabelDificultad;
    private javax.swing.JLabel jLabelHumano;
    private javax.swing.JLabel jLabelMaquina;
    private javax.swing.JLabel jLabelPuntosHumano;
    private javax.swing.JLabel jLabelPuntosMaquina;
    private javax.swing.JPanel jPBanner;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPmapa;
    // End of variables declaration//GEN-END:variables

    private Coordenadas convertidorACoordenadas(String entrada) {

        int principio = entrada.indexOf("Boton[,");
        principio += 7;
        int fin = entrada.indexOf(",64x41");
        String procesado = entrada.substring(principio, fin);
        //Convierto el string en tokens
        StringTokenizer st = new StringTokenizer(procesado, ",");
        String parametroX = st.nextToken();
        String parametroY = st.nextToken();
        int X = Integer.parseInt(parametroX);
        int Y = Integer.parseInt(parametroY);
        X = X / 64;
        Y = Y / 41;
        // System.out.println(" -+-+- Coordenadas  inicio: " + procesado + " X = " + X + " Y = " + Y);
        Coordenadas salida = new Coordenadas(X, Y);
        caballoSegundo = salida;
        return salida;
    }

    private boolean BloqueAdmitido(Coordenadas ubicacion) {
        /*Esta funcion verifica la disponibilidad de movimiento*/
        boolean salida = false;
        salida = matrix[ubicacion.x][ubicacion.y].isColor();

        return salida;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Coordenadas ubicacion = convertidorACoordenadas(ae.getSource().toString());

        if (turnoHumano) {
            if (BloqueAdmitido(ubicacion)) {
                //CREAMOS EL NODO que representa la jugada que escogio el usuario
                Bloque nuevaJugada = new Bloque();
                nuevaJugada.setUbicacion(ubicacion);

                //Definimos los nuevos puntos de cada caballo
                int valorCasilla = minimax.encontrarValorCasilla(matrizDeJuego, ubicacion);
                switch (valorCasilla) {
                    case 3:   //Si la casilla es un cesped
                        nuevaJugada.setPuntosCaballoDorado(1);
                        break;
                    case 4:   //Si la casilla es una flor
                        nuevaJugada.setPuntosCaballoDorado(3);
                        break;
                    case 0:   //Si la casilla está vacía
                        nuevaJugada.setPuntosCaballoDorado(0);
                        break;
                }

                //Definimos el nuevo estado para ese nodo
                Coordenadas casillaAnterior = minimax.encontrarUbicacionCaballo(matrizDeJuego, "Negro");
                ArrayList<ArrayList> nuevoEstado = matrizDeJuego;
                nuevoEstado = minimax.actualizarMatriz(nuevoEstado, casillaAnterior, 0);
                nuevoEstado = minimax.actualizarMatriz(nuevoEstado, ubicacion, 2);
                nuevaJugada.setEstado(nuevoEstado);

                //Realizamos el movimiento que el usuario selecciono
                moverEnL("Negro", nuevaJugada);
                // quitarResaltado();

                //Si el juego no se ha terminado, vuelve a jugar el caballo blanco
                if (termino == false) {
                    //System.out.println("No debe terminar");
                    jugada();
                }

            } else //Si el usuario selecciona una casilla a la cual no se pueda mover
            {
                JOptionPane.showMessageDialog(null, "Lo siento, pero no puede moverse a esta casilla", "Error", 0, IcoRecurso.ICON_ERROR);
            }
        } else // Si no es el turno del jugador
        {
            if (termino) {
                JOptionPane.showMessageDialog(null, "El juego ya terminó", "Error", 0, IcoRecurso.ICON_ERROR);
            } else {
                JOptionPane.showMessageDialog(null, "El juego aún no ha empezado", "Error", 0, IcoRecurso.ICON_ERROR);
            }
        }

    }

}
