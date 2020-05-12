package reflect.view;

import reflect.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReflectFrameChoices extends JFrame
{
	private UmlController app;
    private JPanel panel;
    
    public ReflectFrameChoices(UmlController controller)
    {
        super();
        this.app = controller;
        this.panel = new ReflectPanelChoices(controller);
        
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
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
