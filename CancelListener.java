import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

//cancel listener
class CancelListener implements ActionListener{

    JComponent[] inputs;
	
	public CancelListener(JComponent[] inputs){
		this.inputs = inputs;
	}
	
	public void actionPerformed(ActionEvent e) {
		
        int[] index = new int[]{2};	    
        
        ValidateInputsForm check = new ValidateInputsForm();
        
        boolean validInputs = check.validateFormExtended(inputs, index);
	    
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else { 
		
			//get values entered
			JTextField dateField = (JTextField) inputs[0]; //date
		    String date = dateField.getText();
		    
		    JTextField startField = (JTextField) inputs[1]; //start time
		    String start = startField.getText();
		    
		    JComboBox partnerBox = (JComboBox) inputs[2];  //partner
		    String partner = (String) partnerBox.getSelectedItem(); 
		    
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
	    	    
	    	    String query = "DELETE FROM Appointments "+
	    	                   "WHERE date = ? "+
	    	    		       "AND start = ? "+
	    	                   "AND partner = ?";
	    	    
	    	    PreparedStatement stmt = con.prepareStatement(query); 
	    	    
	    	    //set values for wild card 
	    	    stmt.setString(1, date);
	    	    stmt.setString(2, start);
	    	    stmt.setString(3, partner);
	    	    
	    	    //execute the query
	    	    stmt.executeUpdate();
	    	    
	    	    //message dialog    	   
	    	    JOptionPane.showMessageDialog(null, "Cancelled successfully !");
	    	    
	    	} catch (SQLException ex){
	    	    ex.printStackTrace();
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