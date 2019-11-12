import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;

import javax.swing.*;


//listener for subscribe/unsubscribe
class SubscribeListener implements ActionListener {
	
	JComponent[] inputs;
	
	public SubscribeListener(JComponent[] inputs){
		this.inputs = inputs;
	}

	public void actionPerformed(ActionEvent e) {
		
ValidateInputsForm check = new ValidateInputsForm();
		
	    int[] index = new int[]{1};
	    
	    boolean validInputs = check.validateFormExtended(inputs, index);
	    
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else { 
	    	    
			Connection con = null;
			
			try {				
			
			    JTextField patientIDField = (JTextField) inputs[0];  //patientID
	    	    String patientID =  patientIDField.getText();   
	    	    
	    	    JComboBox planBox = (JComboBox) inputs[1]; //plan combo box
		    	String planName =  (String) planBox.getSelectedItem(); //planName   
		    	    
		    	/**
				//connect to database
				String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
				*/
		    	//connect to database
		    	String dbName = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007";
				String dbUser = "team007";
				String password = "abe58763";
		    	
			    con = DriverManager.getConnection(dbName, dbUser, password);	  	       	    
	    	    
			    String query = "SELECT birthday "+
			                   "FROM Patients "+
			    		       "WHERE patientID = "+patientID;
			    
			    Statement st = con.createStatement();
			    
			    ResultSet rs = st.executeQuery(query);
			    
			    rs.next();
			    
			    String birthday = rs.getString("birthday");
			     	           	   	    
	    	    
	    	    //check age is less than 18 or not
	    	    boolean less18 = new RegisterListener().isUnder18(birthday);
	    	    
	    	    if ((less18 && !(planName.equalsIgnoreCase("NHS Free Plan") || planName.equalsIgnoreCase("Null")))
	    	    		|| (!less18 && (planName.equalsIgnoreCase("NHS Free Plan")))){
	    	    	
	    	    	if (less18){  //less than 18 age
	    	    		
	    	    		 //children under 18 can only have NHS free plan 	    
	    	    		JOptionPane.showMessageDialog(null, "Children under 18 can only choose NHS Free Plan");
	    	    	} else {
	    	    		
	    	    		// adult cannot choose NHS Free Plan
	    	    		JOptionPane.showMessageDialog(null, "Adult cannot choose NHS Free Plan ");       	        
	    	    	}
	    	    	
	    	    } else {
	       	    	   	    	    
		    	    Statement stmt = con.createStatement();
		    	    
		    	    if (planName.equalsIgnoreCase("Null")){  //unsubscribe
		    	    	
		    	    	query = "UPDATE Patients "+
		    	                "SET planName = null, coveredCheckup = 0, coveredHygiene = 0, coveredRepair = 0 "+
		    	    			"WHERE patientID = "+patientID;
		    	    	
		    	    }else{      //subscribe		    	    	
		    	   
		    	    	//get level of service
		        	    Plans pl = new Plans();
		        	    int[] levelOfService = pl.getService(con, planName);
		        	    int noCheckup = levelOfService[0];
		        	    int noHygiene = levelOfService[1];
		        	    int noRepair = levelOfService[2];
		        	    query = "UPDATE Patients "+
		        	            "SET planName = \"" + planName +"\", coveredCheckup = " + noCheckup + ", coveredHygiene="+noHygiene+", coveredRepair="+noRepair+
		        	            " WHERE patientID = "+patientID;
		    	    }
		    	    
		    	    //execute
		    	    stmt.executeUpdate(query);      	    
		    	    
		    	    //dialog show patientID
		    	   
		    	    if (planName.equalsIgnoreCase("Null")){  //unsunscribe
		    	    	
		    	        JOptionPane.showMessageDialog(null, "Unsubscribed successfully");
		    	        
		    	    } else {  //subscribe
		    	  
		    	        JOptionPane.showMessageDialog(null, "Subscribed successfully");
		    	    }   
	    	    }
		    	
	        } catch (SQLException | ParseException e1){
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
	


