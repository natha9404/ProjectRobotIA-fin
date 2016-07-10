/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recursos;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Mauro
 */
public class IcoRecurso {

    // Icons constans
    public static final ImageIcon ICON_VACIO = getIcon("blanco.png");
    public static final ImageIcon ICON_CABALLO_BLANCO = getIcon("caballo_blanco.png");
    public static final ImageIcon ICON_CABALLO_NEGRO = getIcon("caballo_negro.png");
    public static final ImageIcon ICON_CESPED = getIcon("cesped.png");
    public static final ImageIcon ICON_FLOR = getIcon("flor.png");
    public static final ImageIcon ICON_CESPED_COLOR = getIcon("cesped_color.png");
    public static final ImageIcon ICON_FLOR_COLOR = getIcon("flor_color.png");
    public static final ImageIcon ICON_VACIO_COLOR = getIcon("blanco_color.png");
    public static final ImageIcon ICON_ERROR = getIcon("error.png");
    public static final ImageIcon ICON_LIKE= getIcon("like.png");
    public static final ImageIcon ICON_WiNNER = getIcon("winner.png");

    private static ImageIcon getIcon(String path) {
        URL resource = IcoRecurso.class.getResource("icons/" + path);
        if (resource == null) {
            // Log something...
            return null;
        }
        return new ImageIcon(resource);
    }

}
