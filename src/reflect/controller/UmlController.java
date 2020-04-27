package reflect.controller;

import reflect.model.*;
import reflect.view.*;

public class UmlController {
	private ReflectFrameChoices gui;
	
	public UmlController()
	{
		this.gui = new ReflectFrameChoices(this);
	}
	
	public String getClassInfo(String className, boolean[] options) {
		return UmlInfoMethods.getClassInfo(className, options);
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
