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


    public ReflectPanelChoices(UmlController controller)
    {
        super();
        // initialize every thing
        this.controller = controller;
        
        this.submitButton = new JButton("Click to submit");
        this.enterField = new JTextField("", 50);

        this.displayArea = new JTextArea("This is the display",20,50);
        this.displayPane = new JScrollPane();

        constructorsBox = new JCheckBox("get constructors", true);
        methodsBox = new JCheckBox("get methods", true);
        dataMembersBox = new JCheckBox("get data members", true);

        // set up text Area
        displayPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        displayPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayArea.setWrapStyleWord(true);
        displayArea.setEnabled(false);
        displayArea.setDisabledTextColor(Color.BLACK);
        //displayPane.setViewportView(displayArea);
        displayPane.getViewport().add(displayArea);


        // setup the Panel
        this.setLayout(null);
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setSize(800, 600);
        this.add(enterField);
        this.add(submitButton);
        this.add(displayPane);
        this.add(constructorsBox);
        this.add(methodsBox);
        this.add(dataMembersBox);

        // any listeners that I need;
        submitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent click)
            {
                updateScreen();
            }
        });
        
        enterField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent enterPress)
            {
                updateScreen();
            }
        });

        // the layout
        int buffer = 20;
        displayPane.setLocation(buffer,buffer);
        displayPane.setSize(500, 200);
        displayArea.setLocation(0,0);
        displayArea.setSize(displayPane.getSize());

        enterField.setSize(displayPane.getWidth(),25);
        enterField.setLocation(displayPane.getX(),displayPane.getY()+displayPane.getHeight()+buffer);

//        constructorsBox methodsBox dataMembersBox
        int boxsYPosition = enterField.getY()+enterField.getHeight()+buffer;
        int boxXPosition = displayPane.getX();// submitButton.getX()+submitButton.getWidth() + buffer;
        int boxWidth = 150;
        int boxHeight = 20;
        constructorsBox.setLocation(boxXPosition,boxsYPosition);
        constructorsBox.setSize(boxWidth,boxHeight);
        methodsBox.setLocation(boxXPosition, constructorsBox.getY()+constructorsBox.getHeight());
        methodsBox.setSize(boxWidth, boxHeight);
        //methodsBox.setLocation(constructorsBox.getX()+constructorsBox.getWidth(), boxsYPosition);
        dataMembersBox.setLocation(boxXPosition, methodsBox.getY()+methodsBox.getHeight());
        dataMembersBox.setSize(boxWidth, boxHeight);

        //submitButton.setLocation(displayPane.getX(), enterField.getY()+enterField.getHeight()+buffer);
        submitButton.setLocation(displayPane.getX(), boxsYPosition+boxHeight*5);
        submitButton.setSize(170,25);

    }
    
    private void updateScreen()
    {
        String text = enterField.getText();
//      enterField.setText("");

        boolean[] boxes = {constructorsBox.isSelected(), methodsBox.isSelected(), dataMembersBox.isSelected()};

        String info = controller.getClassInfo(text, boxes); // gets the info
        displayArea.append(info);
        displayArea.setCaretPosition(displayArea.getSelectionEnd());
    }
}