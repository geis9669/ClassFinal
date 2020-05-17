package reflect.view;

import reflect.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * displays information to the user and implements
 * user interaction
 * @author Greg
 *
 */
public class ReflectPanelChoices extends JPanel {

	private UmlController controller;
	
    private JScrollPane displayPane;
    private JTextArea displayArea;

    private JTextField enterField;
    private JComboBox<Class> pastClasses;

    private JCheckBox constructorsBox;
    private JCheckBox methodsBox;
    private JCheckBox dataMembersBox;
    
    private JButton submitButton;
    private JButton saveButton;

    private final int buffer = 20;

    /**
     * makes all the user interface components so a user can use the program
     * @param controller the controller of the application to get information from
     */
    public ReflectPanelChoices(UmlController controller)
    {
        super();
        this.controller = controller;
        
        this.setLayout(null);
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setSize(800, 600);
        
        String displayText = "";
        displayText += "This program will get information about classes that can be loaded\n";
        displayText += "An example of what to enter is java.util.Arraylist\n";
        
        this.displayArea = new JTextArea(displayText,20,50);
        displayArea.setWrapStyleWord(true);
        displayArea.setEnabled(false);
        displayArea.setDisabledTextColor(Color.BLACK);
        this.displayPane = new JScrollPane();
        displayPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        displayPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayPane.setViewportView(displayArea);
        displayPane.setLocation(buffer,buffer);
        displayPane.setSize(500, 250);
        displayArea.setLocation(0,0);
        displayArea.setSize(displayPane.getSize());
        this.add(displayPane);
        
        
        this.enterField = new JTextField("", 50);
        enterField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent enterPress)
            {
                updateScreen();
            }
        });
        enterField.setSize(displayPane.getWidth()/2,25);
        enterField.setLocation(displayPane.getX(),displayPane.getY()+displayPane.getHeight()+buffer);
        this.add(enterField);

        this.pastClasses = new JComboBox<Class>();
        DefaultComboBoxModel<Class> pastCModel = new DefaultComboBoxModel<Class>(controller.getClasses());
        pastClasses.setModel(pastCModel);
        pastClasses.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent click)
        	{
        		boolean[] boxes = {constructorsBox.isSelected(), methodsBox.isSelected(), dataMembersBox.isSelected()};
        		Class cl =(Class) pastClasses.getSelectedItem();
        		String info = controller.getClassInfoList(cl,boxes);
        		displayArea.setText(info);
                displayArea.setCaretPosition(displayArea.getSelectionEnd());
        	}
        });
        pastClasses.setSize(enterField.getSize());
        pastClasses.setLocation(enterField.getX()+enterField.getWidth(),enterField.getY());
        this.add(pastClasses);
        
        int boxsYPosition = enterField.getY()+enterField.getHeight()+buffer;
        int boxXPosition = displayPane.getX();
        int boxWidth = 150;
        int boxHeight = 20;

        this.constructorsBox = new JCheckBox("get constructors", true);
        constructorsBox.setLocation(boxXPosition,boxsYPosition);
        constructorsBox.setSize(boxWidth,boxHeight);
        this.add(constructorsBox);
        
        this.methodsBox = new JCheckBox("get methods", true);
        methodsBox.setLocation(boxXPosition, constructorsBox.getY()+constructorsBox.getHeight());
        methodsBox.setSize(boxWidth, boxHeight);
        this.add(methodsBox);
        
        this.dataMembersBox = new JCheckBox("get data members", true);
        dataMembersBox.setLocation(boxXPosition, methodsBox.getY()+methodsBox.getHeight());
        dataMembersBox.setSize(boxWidth, boxHeight);
        this.add(dataMembersBox);
        
        
        this.submitButton = new JButton("Click to submit");
        submitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent click)
            {
                updateScreen();
            }
        });
        submitButton.setLocation(displayPane.getX(), boxsYPosition+boxHeight*4);
        submitButton.setSize(170,25);
        this.add(submitButton);
        
        this.saveButton = new JButton ("Click to save the text in view");
        saveButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent click)
        	{
        		String path = getPathToSave(null);
        		if(path != null)
        		{
        			String text = displayArea.getText();
        			FileIO.saveText(controller, path, text);
        		}
        	}
        });
        saveButton.setLocation(submitButton.getX()+submitButton.getWidth(), submitButton.getY());
        saveButton.setSize(submitButton.getWidth() + 40,submitButton.getHeight());
        this.add(saveButton);
    }
    
    /**
     * updates the screen information if you are using the enterfield
     * for the class you want to load
     */
    private void updateScreen()
    {
        String text = enterField.getText();
        boolean[] boxes = {constructorsBox.isSelected(), methodsBox.isSelected(), dataMembersBox.isSelected()};

        String info = controller.getClassInfo(text, boxes);
        displayArea.setText(info);
        displayArea.setCaretPosition(displayArea.getSelectionEnd());
        DefaultComboBoxModel<Class> pastCModel = new DefaultComboBoxModel<Class>(controller.getClasses());
        pastClasses.setModel(pastCModel);
    }
    
    /**
     * gets the folder path from the user
     * @param startPath can be null, where you want the user to start in the folder system
     * @return the folder path or null if they canceled
     */
    private String getPathToSave(String startPath)
    {
    	String path = ".";
    	int result = -993;
    	JFileChooser fileChooser = new JFileChooser();
    	if(startPath != null)
    	{
    		fileChooser.setCurrentDirectory(new File(startPath));
    	}
    	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	result = fileChooser.showSaveDialog(this);
    	if(result == JFileChooser.APPROVE_OPTION)
    	{
    		path = fileChooser.getSelectedFile().getAbsolutePath();
    	}
    	else
    	{
    		path = null;

    	}
    	return path;
    }
}