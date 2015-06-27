
package ptyxiakivisualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;


public class Graph1 extends JPanel{
    private Color colorArray[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED};
    private double lengths[];
    private double length;
    
    public Graph1(double lengths[]){
        this.lengths = lengths;  
        
        //find max length
        length = 0;
        for (double l : lengths)
            if (l > length)
                length = l;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);        
        
        int degrees1 = 72;
        int degrees2 = 3;
        int step_degrees = 72;
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;

        Polygon p;
        
        // mavroi aksones
        for (int i = 0 ; i < 5 ; i++){
            p = new Polygon();
            p.addPoint(startX, startY);
            p.addPoint(startX + (int)(Math.cos(Math.toRadians(degrees1-2)) * length), 
                    startY - (int)(Math.sin(Math.toRadians(degrees1-2)) * length));
            p.addPoint(startX + (int)(Math.cos(Math.toRadians(degrees1+2)) * length), 
                    startY - (int)(Math.sin(Math.toRadians(degrees1+2)) * length));
            g.fillPolygon(p);
            degrees1 += step_degrees;
        }

        
        for (int i = 0 ; i < 5 ; i++){
            g.setColor(colorArray[i]);
            p = new Polygon();
            p.addPoint(startX, startY);
            p.addPoint(startX + (int)(Math.cos(Math.toRadians(degrees2)) * lengths[i]), 
                    startY - (int)(Math.sin(Math.toRadians(degrees2)) * lengths[i]));
            p.addPoint(startX + (int)(Math.cos(Math.toRadians(degrees2+66)) * lengths[i]), 
                    startY - (int)(Math.sin(Math.toRadians(degrees2+66)) * lengths[i]));
            g.fillPolygon(p);
            degrees2 += step_degrees;
        }       
    }
    
    
}
