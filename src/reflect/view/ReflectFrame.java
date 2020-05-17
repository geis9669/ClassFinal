package reflect.view;

import reflect.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * the frame for the gui
 * @author Greg
 *
 */
public class ReflectFrame extends JFrame
{

	private UmlController app;
    private JPanel panel;
    
    /**
     * sets up the frame
     * @param controller the controller of the program
     */
    public ReflectFrame(UmlController controller)
    {
        super();
        this.app = controller;
        this.panel = new ReflectPanel(controller);
        
        this.setContentPane(panel);
        this.setTitle("Reflection");
        this.setResizable(true);
        this.setMinimumSize(new Dimension(100,100));
        this.setSize(600,600);
        
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent event)
        	{
        		app.closeApplication();
        	}
        });

        this.setVisible(true);
    }
}
