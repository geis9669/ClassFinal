package reflect.controller;

/*
 * helpful websites
 * http://www.egtry.com/java/awt/draw_save
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawanImageandsavetopng.htm
 * https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
 * https://docs.oracle.com/javase/tutorial/2d/images/index.html
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class SaveImage 
{
	
	public static void saveCanvas(Canvas canvas)
	{
		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		
		canvas.paint(g2);
		try 
		{
			ImageIO.write(image, "png", new File("canvas.png"));
		} 
		catch (IOException e)
		{
			
		}
	}

}
