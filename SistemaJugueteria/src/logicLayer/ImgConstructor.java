/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author aleja
 */
public class ImgConstructor extends JPanel {
    private Image image;
    private String path;
    
    public ImgConstructor(String path)
    {
        this.path = path;
    }
    
    @Override
    public void paint(Graphics g) {
        // Cargar la imagen desde la ruta
        image = new ImageIcon(getClass().getResource(path)).getImage();
        
        // Dibujar la imagen para que ocupe todo el tamanio del panel
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        // Hacer que el panel sea transparente para que se vean los componentes encima
        setOpaque(false);
        super.paint(g);
    }
}
