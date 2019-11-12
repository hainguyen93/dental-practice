import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

//main interface for secretary
public class Secretary extends JFrame{
	
	JPanel registrationTab;  //tab for registration
	JPanel appointmentTab;  //tab for appointments
	JPanel paymentTab;  //tab for payment	
	
	
	//constructor, main frame
	public Secretary(){
		super("Secretary");	
				
		setSize(500,450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		JTabbedPane tabbedPane = new JTabbedPane();  //tab pane 
		makeRegistrationTab();  //make register tab
		makeAppointmentTab();  //make booking tab
		makePaymentTab();   //make payment tab		
 		
		tabbedPane.addTab("Registration", null, registrationTab, null);
		tabbedPane.addTab("Appointment", null, appointmentTab, null);
		tabbedPane.addTab("Payment", null, paymentTab, null);			
		
		setContentPane(tabbedPane);
		setVisible(true);
	}
	 
	
	//make a tab
	//return an array of all buttons on that tab
	public JButton[] makeTab(String[][] funs, JPanel tab){
			
		int length = funs.length;  //number of functions on that tab	
		JButton[] bLis = new JButton[length]; //buttons array
		
		for (int row = 0; row < length; row++){
			JPanel pane = new JPanel(new GridBagLayout());			 
			GridBagConstraints gbc = new GridBagConstraints(); //centred buttons
			pane.setBorder(BorderFactory.createTitledBorder(funs[row][0]));  //set border
			JButton button = new JButton(funs[row][1]); //button
			bLis[row] = button;  //add button to button array
			button.setPreferredSize(new Dimension(300, 30));
			pane.add(button, gbc);  //add button to panel and centre it
			tab.add(pane);	//add pane to tab
		}			
	    return bLis;
	}
	
	
	//make registration tab
	public void makeRegistrationTab(){			
		registrationTab = new JPanel(new GridLayout(0,1));
		
		//all functions for registration tab
		String[][] funs = {{"Registering", "Register New Patient"},   //register function
				           {"Subscribing or Unsubscribing", "Subscribe / Unsubscribe Patient"}}; 
		
		//list of all buttons in registration tab
		JButton[] bLis = makeTab(funs, registrationTab); 	
		
		//add listener for each button
		for (JButton b : bLis){
			b.addActionListener(new RegistrationTabListener(bLis));  
		}
	}
	 
	
	//make Appointment tab
	public void makeAppointmentTab(){			
		appointmentTab = new JPanel(new GridLayout(0,1)); 
				
		//all functions for registration tab
		String[][] funs = {{"Booking",  "Book An Appointment"},
				           {"Cancelling", "Cancel An Appointment"},
				           {"Empty Appointment",  "Book An Empty Appointment"},
				           {"Finding",  "Find An Appointment"},
				           {"Calendar",  "View Appointment Calendar"}};
		
		//list of all buttons in registration tab
		JButton[] bLis = makeTab(funs, appointmentTab); 
		
		//add listener for each button
		for (JButton b : bLis){
		    b.addActionListener(new AppointmentTabListener(bLis));
	    }
	}
	
	
	//make Payment tab
	public void makePaymentTab(){			
		paymentTab = new JPanel(new GridLayout(0,1)); 
				
		//all functions for registration tab
		String[][] funs = {{"Reviewing",  "Review And Modify Treatments"},
		                   {"Paying", "Pay Outstanding Bill"}};
		
		//list of all buttons in registration tab
		JButton[] bLis = makeTab(funs, paymentTab); 
		
		//add listener for each button
		for (JButton b : bLis){
		    b.addActionListener(new PaymentTabListener(bLis));
	    }
	}
}



