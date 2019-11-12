import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class RecordListener1 implements ActionListener{
	
	JTextField appField;
	JTextField treatmentField;
	
	public RecordListener1(JTextField appField, JTextField treatmentField){
	    this.appField = appField;
	    this.treatmentField = treatmentField;
	}

	
	public void actionPerformed(ActionEvent arg0) {
		
		//retrieve inputs from user
		String appointmentID = appField.getText(); //appointmentID
		String numTreatments = treatmentField.getText();  //no of treatments
		
		
		ValidateInputsForm check = new ValidateInputsForm();
	    boolean validInputs = check.validateForm(new JComponent[]{appField,treatmentField});
	    
	    //check all fields filled or not
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else {  //all fields filled
		
	    	int num = Integer.parseInt(numTreatments);  //number of treatments
	    	
			//main frame
			JFrame frame = new JFrame("Record " + numTreatments + " Treatments");
			frame.setLayout(new BorderLayout());
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			
					
			//content
			JPanel pane = new JPanel(new SpringLayout());
			pane.add(new JLabel(""));
			pane.add(new JLabel("Treatment Name"));  //tm name column
			pane.add(new  JLabel("Cost"));   //cost column		
			
			//list of pairs [name][cost]
			JComponent[][] tm = new JComponent[num][2];	
			
			for (int i = 0; i < num; i++) {
				
			    JLabel l = new JLabel("Treatment "+(i+1), JLabel.TRAILING);
			    pane.add(l);  
			    JTextField name = new JTextField(30);  //tm name field
			    pane.add(name);  //add to content
			    JTextField cost = new JTextField(7);  //cost
			    pane.add(cost);	//add to content
			    
			    tm[i][0] = name;  //save to tm 
			    tm[i][1] = cost;
			}
			
			int rows = num + 1; 
			
			//form layout
			FormLayout layout = new FormLayout();
			layout.makeAForm(pane, rows, 3);	
		
	
			//record button
			JButton okButton = new JButton("OK");  
			okButton.addActionListener(new RecordListener2(appointmentID, tm)); //put treatments list to listener
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(okButton);
			
			frame.add(pane, BorderLayout.CENTER);
			frame.add(buttonPanel, BorderLayout.PAGE_END);
			frame.pack();
			frame.setVisible(true);		
	    }
	}	
}

