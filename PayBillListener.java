import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


//listener dealing with paying outstanding bill
public class PayBillListener implements ActionListener{
	JTextField inputs;
	
	public PayBillListener(JTextField inputs){
		this.inputs = inputs;
	}
	public void actionPerformed(ActionEvent e) {
		
		//retrieve input value
		String patientID = inputs.getText();  //patientID
		
		ValidateInputsForm check = new ValidateInputsForm();
	    boolean validInputs = check.validateForm(new JComponent[]{inputs});
	    
	    //check all fields filled or not
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else {  //all fields filled
	    	
	    	Connection con = null;
		    
			try {

				/**
				String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
				*/
				//connect to database
				String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
				
			    con = DriverManager.getConnection(dbName, dbUser, password);	
			    
	    	    String query = "UPDATE Treatments "+
	    	                   "SET paid = true "+
	    	    		       "WHERE appointmentID in (SELECT A.appointmentID "+
	    	    		                               "FROM Appointments AS A "+
	    	    		                               "WHERE patientID = "+patientID+")";
	    	    
	    	    Statement stmt = con.createStatement();
	    	    stmt.executeUpdate(query);
	    	    
	    	    //message dialog    	   
	    	    JOptionPane.showMessageDialog(null, "All treatments have been paid !");    	    
	    	   
	    	} catch (SQLException e1){
	    	    e1.printStackTrace();
	        } finally {
	    	    if (con !=null)
					try {
						con.close();
					} catch (SQLException e1) {					
						e1.printStackTrace();
					}
	        }
	    }
		
        
				
	}	
}
