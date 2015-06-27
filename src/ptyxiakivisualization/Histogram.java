package ptyxiakivisualization;

// THIS PROGRAM IS MODIFIED FROM ONE BY LIANG:
// http://www.cs.armstrong.edu/liang/intro6e/evennumberedexercise/Histogram.java
// M.S. Branicky, 11/23/07, 11/26/07

import javax.swing.*;
import java.awt.*;

public class Histogram extends JPanel {
  // Count of occurrences of each category
  private double[] count;
  // Name of each category
  private String[] countname;

  /** Set the count and display histogram
     * @param count
     * @param countname */
  public void showHistogram(double[] count, String[] countname) {
    this.count = count;
    this.countname = countname;
    repaint();
  }

  /** Paint the histogram */
  @Override
  protected void paintComponent(Graphics g) {
    if (count == null || count.length == 0) return; // No display if count is null
    super.paintComponent(g);
    
    //System.out.println("histogram!");
    
    // Find the panel size and bar width and interval dynamically
    int width = getWidth();
    //System.out.println("width="+width);
    int height = getHeight();
    int interval = (width - 40) / count.length;
    //int individualWidth = (int)(((width - 40) / count.length) * 0.30);//change 0,6=>0.4

    int individualWidth = 60;
    
    // Find the maximum count. The maximum count has the highest bar
    double maxCount = 0;
    for (int i = 0; i < count.length; i++) {
      if (maxCount < count[i])
        maxCount = count[i];
    }

    // x is the start position for the first bar in the histogram
    int x = 30;

    // Draw a horizontal base line
    g.drawLine(10, height - 45, width - 10, height - 45);
    for (int i = 0; i < count.length; i++) {
      // Find the bar height
      int barHeight =
        (int)(((double)count[i] / (double)maxCount) * (height - 95));

      // Display a bar (i.e. rectangle)
      if(countname[i].equals("natural science")){
      g.setColor(Color.blue);
       g.fillRect(40, height - 45 - barHeight, individualWidth,barHeight);
      }else if(countname[i].equals("applied science")){
          g.setColor(Color.cyan);
          g.fillRect(178, height - 45 - barHeight, individualWidth,barHeight);
      }else if(countname[i].equals("social science")){
          g.setColor(Color.green);
          g.fillRect(316, height - 45 - barHeight, individualWidth,barHeight);
      }else if(countname[i].equals("universe science")){
          g.setColor(Color.yellow);
          g.fillRect(454, height - 45 - barHeight, individualWidth,barHeight);
      }else if(countname[i].equals("human science")){
          g.setColor(Color.red);
          g.fillRect(592, height - 45 - barHeight, individualWidth,barHeight);
      }
      
      //g.fillRect(30, height - 45 - barHeight, individualWidth,barHeight);
      //g.fillRect(60, height - 45 - barHeight, individualWidth,barHeight);
     // g.fillRect(x, height - 45 - barHeight, individualWidth,barHeight);


//      g.setColor(Color.black);
//      g.setFont(new Font("Arial", Font.BOLD, 16)); 
//      // Display a letter under the base line
//      g.drawString(countname[i], x, height - 30);
//
//      // Display count (turned into a string) over each bar
//      g.drawString(""+count[i], x, height - barHeight - 55 );
//
//      // Move x for displaying the next character
//      x += interval;
    }
  }

  /** Override getPreferredSize */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 300);
  }
}