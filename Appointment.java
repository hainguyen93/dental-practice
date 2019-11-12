import java.awt.*;
import javax.swing.*;

public class Appointment {	
	
	
	//book method
	public void book(){		
		String title = "Book an appointment";
		String[] fields = {"Date (YYYY-MM-DD)", "Start Time (HH:MM)", "Partner", "End Time (HH:MM)","PatientID"};		
		JButton button = new JButton("Book Appointment");
		JComponent[] inputs = makeFrame(title, fields, button);
		button.addActionListener(new BookListener(inputs));				
	}
	
	
	//cancel method
	public void cancel(){
		String title = "Cancel an appointment";
		String[] fields = {"Date (YYYY-MM-DD)", "Start Time (HH:MM)", "Partner"};		
		JButton button = new JButton("Cancel Appointment");
		JComponent[] inputs = makeFrame(title, fields, button);
		button.addActionListener(new CancelListener(inputs));	
	}		
	
	
	//book empty appointment method
	public void bookEmpty(){		
		String title = "Book empty appointment";
		String[] fields = {"From Date (YYYY-MM-DD)", "To Date (YYYY-MM-DD)", "Partner"};	
		JButton button = new JButton("Book Empty Appointment");
		JComponent[] inputs = makeFrame(title, fields, button);
		button.addActionListener(new BookEmptyListener(inputs));		
	}
	

	//find method
	public void find(){		
		String title = "Find an appointment";
		String[] fields = {"Date (YYYY-MM-DD)", "PatientID"};		
		JButton button = new JButton("Find An Appointment");
		JComponent[] inputs = makeFrame(title, fields, button);
		button.addActionListener(new FindListener(inputs));		
	}
	
	
	//view calendar
	public void viewCalendar(String[] partners){
		String title = "View Appointment Calendar";
		String[] fields = {"From Date (YYYY-MM-DD)", "To Date (YYYY-MM-DD)"};		
		JButton button = new JButton("View All Appointment");
		JComponent[] inputs = makeFrame(title, fields, button);
		button.addActionListener(new ViewCalendarListener(inputs, partners));
	}
		
	
	//make frame
	public JComponent[] makeFrame(String title, String[] fields, JButton button){
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
				
		//list of all entered inputs
		int numFields = fields.length;   //number of fields
		JComponent[] inputs = new JComponent[numFields];
		
		//content 
		JPanel pane = new JPanel(new SpringLayout());		
		
		for (int i = 0; i < numFields; i++){
			JLabel label = new JLabel(fields[i], JLabel.TRAILING);
		    pane.add(label);
		    
		    if (i==2){   //partner field
		    	String[] partners = {"Dentist", "Hygienist"};  //all pertners
		    	JComboBox cb = new JComboBox(partners);
		    	inputs[i] = cb;  //add to inputs array
		    	cb.setSelectedIndex(0);
		    	label.setLabelFor(cb);
		    	
		    	pane.add(cb); //add to content
		    } else {
		    	JTextField textField = new JTextField(20);
		    	inputs[i] = textField; //add to inputs array
				label.setLabelFor(textField);
				
				pane.add(textField);  //add to content
			}	
		}		
		
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, numFields, 2);
				
	    frame.add(pane, BorderLayout.CENTER); //add content to frame	    
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		
		frame.add(buttonPanel, BorderLayout.PAGE_END);  //add button to frame
		frame.pack();
		frame.setVisible(true);
		return inputs;  //list of all entered inputs
	}	
}

