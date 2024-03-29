package reflect.controller;

import java.awt.Component;
import java.util.HashSet;
import javax.swing.JOptionPane;
import reflect.model.*;
import reflect.view.*;

/**
 * sets up and destroy the application
 * @author Greg
 *
 */
public class UmlController {
	private String datafile;
	private HashSet<Class> pastClasses;
	
	private ReflectFrame gui;
	
	/**
	 * sets up the entire application
	 */
	public UmlController()
	{
		datafile = "pastClasses.reflect";
		try 
		{
			pastClasses = (HashSet<Class>) FileIO.loadData(this, datafile);
		}
		catch(Exception error)
		{
			String message = "Could not load pastClasses.reflect due to \n" + error.getMessage();
			Class<?> stack = error.getClass();
			message += "\n"+stack.getName();
			displayMessage(null,message);
			pastClasses = new HashSet<Class>();
		}
		this.gui = new ReflectFrame(this);
	}
	
	/**
	 * This is the method to call when you want to close the project
	 */
	public void closeApplication()
	{
		FileIO.saveData(this, datafile, pastClasses);
		
		System.exit(0);
	}
	
	/**
	 * this handles generic errors to show to the user
	 */
	public void handleErrors(Exception error)
	{
		String message = "An error happend \n" + error.getMessage();
		Class<?> stack = error.getClass();
		message += "\n"+stack.getName();
		displayMessage(gui,message);
	}
	
	/**
	 * gets the classes that have been loaded before
	 * @return an array of classes that are loaded
	 */
	public Class[] getClasses()
	{
		Class[] temp = new Class[pastClasses.size()];
		pastClasses.toArray(temp);
		return temp;
	}
	
	/**
	 * this is used to load a string that might be a reference to a class
	 * @param className a string that is the class to be loaded
	 * example java.util.ArrayList
	 * @param options the first is for constructors, next methods, lastly datamembers
	 * @return a string with all the information or an error message
	 */
	public String getClassInfo(String className, boolean[] options) 
	{
		try
		{
			Class cl = Class.forName(className);
			pastClasses.add(cl);
			return UmlInfoMethods.getClassInfo(cl, options);
		}
		catch(ClassNotFoundException e)
		{
			String message = "Could not find the entered class, make sure its all spelled correctly\n";
			message +="and is in the build path";
			return message;
		}
	}
	
	/**
	 * this is used if the class has already been loaded to get its information
	 * @param classClass the class you want information about
	 * @param options the first is for constructors, next methods, lastly datamembers
	 * @return a string with all the information
	 */
	public String getClassInfoList(Class classClass, boolean[] options)
	{
		return UmlInfoMethods.getClassInfo(classClass, options);
	}
	
	/**
	 * used to have only one place for when the programmer needs to
	 * show information to the user so if I change from popup
	 * I don't have to look through all my code for each instance
	 * 
	 * show a message to the user.
	 * @param Component where you want the popup to appear in front of null is for the screen.
	 * @param message what you want to tell the user
	 */
	private void displayMessage(Component parentComponent,String message)
	{
		JOptionPane.showMessageDialog(parentComponent, message);
	}
	/**
	 * used to have only one place for when the programmer needs to
	 * ask a question to the user
	 * @param message the question to ask
	 * @param Component where you want the popup to appear in front of null is for the screen.
	 * @return what the user entered
	 */
	private String askQuestion(Component parentComponent, String message)
	{
		return ""+ JOptionPane.showInputDialog(parentComponent,message);
	}
}
