package reflect.view;

import javax.swing.JOptionPane;

/**
 * this makes popups for user interaction
 * @author geis9669
 *
 */
public class Popup
{
	/**
	 * this makes a popup on the screen to show to the user
	 * @param message the information you want to show to the user
	 */
    public void displayMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message);
    }
    
    /**
     * makes a popup to get the user to enter a item in
     * @param message the question to ask
     * @return the value the user entered
     */
    public String askQuestion(String message)
    {
        return ""+ JOptionPane.showInputDialog(null,message);
    }

}
