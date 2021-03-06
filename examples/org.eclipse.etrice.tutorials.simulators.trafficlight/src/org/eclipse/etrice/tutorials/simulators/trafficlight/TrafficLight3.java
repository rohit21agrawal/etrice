package org.eclipse.etrice.tutorials.simulators.trafficlight;
import java.awt.*;
import java.awt.event.*;


public class TrafficLight3 extends Canvas {
	// Traffic light has 4 states
	// 0 = red
	// 1 = yellowRed
	// 2 = green
	// 3 = yellow
	// 4 = OFF
	
	private int state;
	
	public TrafficLight3(int state){
		this.state = state;
	}
	public TrafficLight3(){
		this.state = 0;
	}

	private int singleLightSize = 20;
	private int xPosition = 5;
	private int gap = 5;
	
	public void paint(Graphics g){
		Color lightRed = new Color(255,0,0);
		Color darkRed = new Color (127,0,0);
		Color lightGreen = new Color (0,255,0);
		Color darkGreen = new Color (0,127,0);
		Color lightYellow = new Color (255,255,0);
		Color darkYellow = new Color (127,127,0);
		
		Color colors [][] = {
				{lightRed,darkYellow,darkGreen},
				{lightRed,lightYellow,darkGreen},
				{darkRed,darkYellow,lightGreen},
				{darkRed,lightYellow,darkGreen},
				{darkRed,darkYellow,darkGreen}
		};
		
		
		// set Background
		g.setColor(Color.lightGray);
		g.fillRect(0,0,getSize().width,getSize().height);
		// draw boarder
		g.setColor(Color.BLACK);
		g.drawRect(0,0,getSize().width-1,getSize().height-1);
		
		for (int i = 0; i<3; i++) {
			g.setColor(colors[state][i]);
			g.fillOval(xPosition,i*singleLightSize+gap,singleLightSize,singleLightSize);
			g.setColor(Color.black);
			g.drawOval(xPosition,i*singleLightSize+gap,singleLightSize-1,singleLightSize-1);
		}
	}

	 public Dimension getPreferredSize(){
	     return new Dimension(singleLightSize + 2 * gap, singleLightSize * 3 + 2 * gap);
	   }
	 
	   public Dimension getMinimumSize()
	   {
	     return new Dimension(singleLightSize + 2 * gap, singleLightSize * 3 + 2 * gap);
	   }	

	   public void setState(int state)
	   {
		   if (state < 5){
			   this.state = state;
			   repaint();			   
		   }
	   }	
}
