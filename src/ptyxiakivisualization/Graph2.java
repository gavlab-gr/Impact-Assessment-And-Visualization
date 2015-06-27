package ptyxiakivisualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

public class Graph2 extends JPanel{
    private Color colorArray[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED};
    private double lengths[][];
    private double length;
    
    
    public Graph2(double lengths1[], double lengths2[], double lengths3[], double lengths4[], double lengths5[]){
        lengths = new double[5][];
        
        this.lengths[0] = lengths1;  
        this.lengths[1] = lengths2;
        this.lengths[2] = lengths3;
        this.lengths[3] = lengths4;
        this.lengths[4] = lengths5;
        
        length = 1;
        for (double l1[] : lengths)
            for (double l : l1)
                if (l > length)
                    length = l;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);        
        
        int degrees1 = 72;
        int start_degrees;
        int degrees2;
        int numOfValues;
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

        degrees2 = 3;
        for (int i = 0 ; i < 5 ; i++){
            g.setColor(colorArray[i]);
            numOfValues = lengths[i].length;
            start_degrees = degrees2;
            step_degrees = 66 / (numOfValues + 1);
            degrees2 += step_degrees;
            for (double len : lengths[i]){
                g.drawLine(startX, startY, 
                        startX + (int)(Math.cos(Math.toRadians(degrees2)) * len), 
                        startY - (int)(Math.sin(Math.toRadians(degrees2)) * len)
                );
                degrees2+=step_degrees;
            }
            degrees2=start_degrees + 72;
        }
    }
    
}
