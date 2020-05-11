package reflect.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileIO {

	public static void saveText(UmlController app, String path, String textToSave)
	{
		String filename = path;
		LocalDateTime date = LocalDateTime.now();
		
		filename += "/" + date.getMonth()+ " "+ date.getDayOfMonth() + " ";
		filename += date.getHour() + "-" + date.getMinute();
		filename += " umlInfo.txt";
		File saveFile = new File(filename);
		
		try(Scanner textScanner = new Scanner(textToSave); PrintWriter saveText = new PrintWriter(saveFile))
		{
			while (textScanner.hasNext())
			{
				String currentLine = textScanner.nextLine();
				saveText.println(currentLine);
			}
		}
		catch(IOException error)
		{
			app.handleErrors(error);
		}
		catch(Exception genericError)
		{
			app.handleErrors(genericError);
		}
	}
}
