package reflect.controller;

import java.util.ArrayList;
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
	
	private ReflectFrameChoices gui;
	
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
			JOptionPane.showMessageDialog(null, message);
			pastClasses = new HashSet<Class>();
		}
		this.gui = new ReflectFrameChoices(this);
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
		JOptionPane.showMessageDialog(null, message);
	}
	
	public Class[] getClasses()
	{
		Class[] temp = new Class[pastClasses.size()];
		pastClasses.toArray(temp);
		return temp;
	}
	
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
	
	public String getClassInfoList(Class classClass, boolean[] options)
	{
		return UmlInfoMethods.getClassInfo(classClass, options);
	}
	
	public void something()
	{
		String className = "java.util.ArrayList";
		String[] includeWords;// has to have this some where in it
		String[] excludeWords;// can't have this some where in it
		SortBy[] sorts ; // how to sort the lists
		
		boolean[] modifiers; // public, static , final, none
		
		String[] returnType; 
		
		try
		{
			
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public void start()
	{
		displayMessage(getClassInfo("java.util.ArrayList", null));
	}
	
	private void displayMessage(String message)
	{
		System.out.println(message);
	}
	private String askQuestion(String message)
	{
		System.out.println(message);
		return "";
	}
}
