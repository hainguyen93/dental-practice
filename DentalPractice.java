import java.awt.*;
import javax.swing.UIManager.*;  //for use NIMBUS look-and-feel
import javax.swing.*;

import java.awt.event.*;

 
public class DentalPractice extends JFrame implements ActionListener{
			
	JComboBox cb; //used in footer
	
	//constructor
	public DentalPractice(){
		super("Welcome");
		
		//using NIMBUS look-and-feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		setSize(500,450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		Container con = getContentPane(); //content pane
		con.setBackground(Color.ORANGE);
		
		JPanel greeting = makeGreetingLabel(); //greeting label
		con.add(greeting, BorderLayout.CENTER); //add to content pane
		
		JPanel footer = makeFooter(); //make a footer panel
		con.add(footer, BorderLayout.PAGE_END); //add footer to content pane		
		
		setVisible(true); //make it display 
	}		
		
	
	//make a greeting label
	public JPanel makeGreetingLabel(){	
		JPanel content = new JPanel(new BorderLayout());
		content.setBackground(Color.ORANGE); 
		
		JLabel greeting = new JLabel("Welcome to Information System");
		greeting.setHorizontalAlignment(SwingConstants.CENTER);		
		greeting.setFont(new Font("Serif", Font.BOLD, 22));  //set font	
		
		JLabel groupName = new JLabel("UnderGrad Team 07");
		groupName.setHorizontalAlignment(SwingConstants.CENTER);		
		groupName.setFont(new Font("Serif", Font.BOLD, 18));  //set font	
		
		content.add(greeting, BorderLayout.CENTER);
		content.add(groupName, BorderLayout.PAGE_END);
		
		return content;
    }
			
	//make a footer
	public JPanel makeFooter(){
		JPanel footer = new JPanel(new FlowLayout()); 		
		JLabel loggingAs = new JLabel("Logging as :");
		
		String[] users = {"Secretary","Dentist","Hygienist"}; //list of users
		cb = new JComboBox(users);  //combo box
		cb.setSelectedIndex(0);
		
		JButton enterButton = new JButton("Enter");  //enter button
		enterButton.addActionListener(this);  //add listener to "enter" button
		
		footer.add(loggingAs);    //add "logging as" to footer
		footer.add(cb);           //add combo box to footer
		footer.add(enterButton);  //add "Enter" button to footer
		return footer;
	}

	public void actionPerformed(ActionEvent e) {
		String selectedUser = (String) cb.getSelectedItem();  //selected user
		dispose();    //open new frame, close this one
		switch (selectedUser){
		    case "Secretary": new Secretary(); //user is secretary
		                      break;	
		    case "Dentist"  : new Partner("dentist"); //user is dentist
		                      break;  
		    case "Hygienist": new Partner("hygienist"); //user is hygienist
		                      break;  
		}
	}	
	
	//main method
	public static void main(String[] args){
		new DentalPractice();  
	}
}