package reflect.view;

import reflect.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReflectPanelChoices extends JPanel {

	private UmlController controller;
	
    private JScrollPane displayPane;
    private JTextArea displayArea;

    private JTextField enterField;
    private JButton submitButton;

    private JCheckBox constructorsBox;
    private JCheckBox methodsBox;
    private JCheckBox dataMembersBox;

    private JComboBox<Class> pastClasses;
    
    private final int buffer = 20;

    public ReflectPanelChoices(UmlController controller)
    {
        super();
        this.controller = controller;
        
        this.setLayout(null);
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setSize(800, 600);
        
        
        this.displayArea = new JTextArea("This is the display",20,50);
        displayArea.setWrapStyleWord(true);
        displayArea.setEnabled(false);
        displayArea.setDisabledTextColor(Color.BLACK);
        this.displayPane = new JScrollPane();
        displayPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        displayPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //displayPane.setViewportView(displayArea);
        displayPane.getViewport().add(displayArea);
        displayPane.setLocation(buffer,buffer);
        displayPane.setSize(500, 200);
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
        		//.getSelectedItem() 
        		//.getSelectedIndex()
        	}
        });
        pastClasses.setSize(enterField.getSize());
        pastClasses.setLocation(enterField.getX()+enterField.getWidth(),enterField.getY());
        this.add(pastClasses);
        
        int boxsYPosition = enterField.getY()+enterField.getHeight()+buffer;
        int boxXPosition = displayPane.getX();// submitButton.getX()+submitButton.getWidth() + buffer;
        
        int boxWidth = 150;
        int boxHeight = 20;

        this.constructorsBox = new JCheckBox("get constructors", true);
        constructorsBox.setLocation(boxXPosition,boxsYPosition);
        constructorsBox.setSize(boxWidth,boxHeight);
        this.add(constructorsBox);
        
        this.methodsBox = new JCheckBox("get methods", true);
        methodsBox.setLocation(boxXPosition, constructorsBox.getY()+constructorsBox.getHeight());
        methodsBox.setSize(boxWidth, boxHeight);
        //methodsBox.setLocation(constructorsBox.getX()+constructorsBox.getWidth(), boxsYPosition);
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
//        submitButton.setLocation(displayPane.getX(), enterField.getY()+enterField.getHeight()+buffer);
        submitButton.setLocation(displayPane.getX(), boxsYPosition+boxHeight*5);
        submitButton.setSize(170,25);
        this.add(submitButton);
        
    }
    
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
}