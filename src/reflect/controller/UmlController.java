package reflect.controller;

import reflect.model.*;
import reflect.view.*;

public class UmlController {
	private ReflectFrameChoices gui;
	
	public UmlController()
	{
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
	
	public String getClassInfo(String className, boolean[] options) {
		return UmlInfoMethods.getClassInfo(className, options);
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
	
	public void handleErrors(Exception error)
	{
		error.printStackTrace();
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
