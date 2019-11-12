import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.swing.*;



//book empty listener
public class BookEmptyListener implements ActionListener{

    JComponent[] inputs;
	
	public BookEmptyListener(JComponent[] inputs){
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
		
			//retrieve inputs values
			JTextField fromField = (JTextField) inputs[0];  //from date
		    String from = fromField.getText();		   
		    
		    JTextField toField = (JTextField) inputs[1];  //to date
		    String to = toField.getText();		    
		    
		    JComboBox partnerBox = (JComboBox) inputs[2];  //partner
		    String partner = (String) partnerBox.getSelectedItem(); 
		    
		    //get num of day-offs
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    
		    Calendar c1 = Calendar.getInstance();  //from date
		    Calendar c2 = Calendar.getInstance();   //to date
		    
		    try {	    	
				c1.setTime(format.parse(from));
				c2.setTime(format.parse(to));
				
			} catch (ParseException e2) {				
				e2.printStackTrace();
			}	   
		    	    
		    //get num of day-offs
		    int numDays = 0;
		    Calendar d = (Calendar) c1.clone();
		    
		    while (d.before(c2)){
		    	d.add(Calendar.DAY_OF_MONTH, 1);
		    	numDays++;
		    } 
		    
		    
			Connection con = null;
			
			try {			
	             
				//connect to database
				String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
				/**
				//connect to database
				String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
				*/
			    con = DriverManager.getConnection(dbName, dbUser, password);	    
			    
	    	    String query = "INSERT INTO Appointments "+
	    	                   "VALUES (null, ?, '09:00', '17:00', ?, null)";
	    	    
	    	    PreparedStatement stmt = con.prepareStatement(query); 
	    	    
	    	    for (int i =0; i <= numDays; i++){    	        
	    	        String date = format.format(c1.getTime());  //convert calendar object to string 	    	       
	    	        
	    	        stmt.clearParameters();  //clear old parameters
	    	       
	    	        //set values for wild card 
	        	    stmt.setString(1, date);
	        	    stmt.setString(2, partner);
	        	    
	        	    stmt.executeUpdate();   
	        	    c1.add(Calendar.DATE, 1); //add i days to from date
	    	    }  	  
	    	    
	    	    //message dialog    	   
	    	    JOptionPane.showMessageDialog(null, "Booked successfully !");
	    	    
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




