import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class RecordListener2 implements ActionListener{
	
	JComponent[][] tm;  // lis of pairs (treatment name, cost)
	String appID;  //appointmentID
	
	public RecordListener2(String appID, JComponent[][] tm){
		this.appID = appID;
		this.tm = tm;
	}
	
	public void actionPerformed(ActionEvent e){
		
		//check all fields filled or not
		ValidateInputsForm check = new ValidateInputsForm();
		
	    boolean validInputs = true;
	    
	    for (JComponent[] row : tm){	    	

	    	boolean tempValue = check.validateForm(row);
	    	
	    	//if one row unfilled, the whole form is not valid
	    	if (!tempValue){
	    		validInputs = false;
	    	}
	    }
	        
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
	    	       	    
	    	    String query  = "INSERT INTO Treatments "+
	    	                    "VALUES (" + appID + ", ?, ?, false)";  
	    	    
	    	    PreparedStatement stmt = con.prepareStatement(query);
	    	    	    
	    	    for (int row = 0; row < tm.length; row++){
	    	    	JTextField nameField = (JTextField) tm[row][0];  //name field
	    	    	String name = nameField.getText();  //treatment name
	    	    	
	    	    	JTextField costField = (JTextField) tm[row][1];  //cost field
	    	    	String cost = costField.getText();  //cost     
	    	    	
	    	    	stmt.clearParameters();  //clear all existing parameters
	    	    	stmt.setString(1, name);
	    	    	stmt.setString(2, cost);
	    	    	
	    	    	stmt.executeUpdate();  //execute query  
	    	    	   	    	
	    	    }  	
	    	    
	    	    //dialog to show result    	    	
    	        JOptionPane.showMessageDialog(null, "All treatments recorded successfully");
	    	    
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


