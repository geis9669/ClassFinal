package reflect.controller;

import java.util.ArrayList;
import reflect.model.*;
import reflect.view.*;

/**
 * sets up and destroy the application
 * @author Greg
 *
 */
public class UmlController {
	private String datafile;
	private ArrayList<Class> pastClasses;
	
	private ReflectFrameChoices gui;
	
	/**
	 * sets up the entire application
	 */
	public UmlController()
	{
		datafile = "pastClasses.reflect";
		try {
			pastClasses = (ArrayList<Class>) FileIO.loadData(this, datafile);
		}
		catch(Exception error)
		{
			pastClasses = new ArrayList<Class>();
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
		error.printStackTrace();
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
			return "Could not find the entered class, make sure its all spelled correctly\\n";
		}
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
	// have it be able to sort info by modifiers then name
	// how to make code to do this
}
