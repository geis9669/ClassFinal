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
	
	// have it be able to sort info by modifiers then name
	// how to make code to do this
}
