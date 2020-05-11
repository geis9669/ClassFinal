package reflect.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileIO {

	/**
	 * saves text to a text file
	 * @param app the controller of the program
	 * @param path where to save the file on your system
	 * @param textToSave the text that you are saving
	 */
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
	
	/**
	 * saves a object to a file to the current directory
	 * @param app the controller of this program
	 * @param datafile name of the save file
	 * @param object the object being saved
	 */
	public static void saveData(UmlController app, String datafile, Serializable object)
	{
		try(FileOutputStream saveStream = new FileOutputStream(datafile);
				ObjectOutputStream output = new ObjectOutputStream(saveStream))
		{
			output.writeObject(object);
		}
		catch(IOException error)
		{
			app.handleErrors(error);
		}
	}
	
	/**
	 * loads an object from the current directory
	 * @param app the controller of this program
	 * @param datafile name of the file to load
	 * @return the object that needs to be cast could be null
	 */
	public static Object loadData(UmlController app, String datafile)
	{
		try(FileInputStream loadStream = new FileInputStream(datafile);
				ObjectInputStream input = new ObjectInputStream(loadStream))
		{
			return input.readObject();
		}
		catch(IOException error)
		{
			app.handleErrors(error);
		} 
		catch (ClassNotFoundException error) 
		{
			app.handleErrors(error);
		}
		catch(Exception error)
		{
			app.handleErrors(error);
		}
		return null;
	}
}
