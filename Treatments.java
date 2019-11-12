import java.awt.*;

import javax.swing.*;


public class Treatments {
		
	//review all treatments (unpaid only)
	public void review(){
		
		//main frame
		JFrame frame = new JFrame("Review treatments");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
				
		JPanel pane = new JPanel(new SpringLayout());
		
		//patientID field
		JLabel l = new JLabel("PatientID", JLabel.TRAILING);
	    pane.add(l);
	    JTextField patientIDField = new JTextField(20);
	    l.setLabelFor(patientIDField);
		pane.add(patientIDField);			
		
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, 1, 2); 	
				
		JButton reviewButton = new JButton("Review treatments");
		reviewButton.addActionListener(new ReviewListener(patientIDField));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(reviewButton);
		
		frame.add(pane, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.PAGE_END);		
		frame.pack();
		frame.setVisible(true);
	}
	
	//pay outstanding bill
	public void payBill(){
		JFrame frame = new JFrame("Pay treatments");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
				
		//content
		JPanel pane = new JPanel(new SpringLayout());
		
		//patientID field
		JLabel l = new JLabel("PatientID", JLabel.TRAILING);
	    pane.add(l);
	    JTextField patientIDField = new JTextField(20);
	    //textList[i] = textField;
		l.setLabelFor(patientIDField);
		pane.add(patientIDField);			
		
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, 1, 2);	
				
		JButton payButton = new JButton("Pay Treatments");
		payButton.addActionListener(new PayBillListener(patientIDField));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(payButton);
		
		frame.add(pane, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.PAGE_END);			
		frame.pack();
		frame.setVisible(true);
	}	
	
	//record treatments
	public void record(){

		//main frame 
		JFrame frame =  new JFrame("Record Treatments");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		Container con = frame.getContentPane();
		con.setLayout(new BorderLayout());
		
		//inputs array
		JComponent[] inputs = new JComponent[2]; 
		
		JPanel pane = new JPanel(new SpringLayout());
		
		//appointmentID field
		JLabel appointmentIDLabel = new JLabel("Appointment ID", JLabel.TRAILING);
		pane.add(appointmentIDLabel);
		JTextField appField = new JTextField(20);  
		inputs[0] = appField; //add to inputs array
		appointmentIDLabel.setLabelFor(appField);
		pane.add(appField);		
		
		//treatments field 
		JLabel treatmentLabel = new JLabel("Number of treatments ?", JLabel.TRAILING);
		pane.add(treatmentLabel);
		JTextField treatmentField = new JTextField(20);
		inputs[1] = treatmentField;  //add to inputs array
		treatmentLabel.setLabelFor(treatmentField);
		pane.add(treatmentField);
		
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, 2, 2);     
				
		//buttons
		JButton recordButton = new JButton("Record");
		recordButton.addActionListener(new RecordListener1(appField, treatmentField));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(recordButton);
		
		//add to content pane
		con.add(pane, BorderLayout.CENTER);
		con.add(buttonPanel, BorderLayout.PAGE_END);	
		
		frame.pack();
		frame.setVisible(true);
	}
}
