import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


//find listener
class FindListener implements ActionListener{
	
    JComponent[] inputs;
	
	public FindListener(JComponent[] inputs){
		this.inputs = inputs;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//get values entered
		JTextField dateField = (JTextField) inputs[0];  //date
	    String date = dateField.getText();
	    
	    JTextField patientIDField = (JTextField) inputs[1];  //patientID
	    String patientID = patientIDField.getText();
	    
	    ValidateInputsForm check = new ValidateInputsForm();
	    boolean validInputs = check.validateForm(inputs);
	    
	    
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
	    	    
	    	    String query = "SELECT start, end, partner "+
	    	                   "FROM Appointments "+
	    	    		       "WHERE date = ? and patientID = ? "+
	    	                   "ORDER BY start";
	    	    
	    	    PreparedStatement stmt = con.prepareStatement(query); 
	    	    
	    	    //set values for wild card 
	    	    stmt.setString(1, date);
	    	    stmt.setString(2, patientID);    	    
	    	    
	    	    //execute the query
	    	    ResultSet rs = stmt.executeQuery();
	    	    
	    	    //frame to show result
	    	    JFrame f = new JFrame("Found Result");
	    	    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	    
	    	    Container contentPane = f.getContentPane();
	    	    contentPane.setLayout(new GridLayout(0,3));   
	    	    
	    	    contentPane.add(new Label("Start Time"));  //start time column
	    	    contentPane.add(new Label("End Time"));  //end time column
	    	    contentPane.add(new Label("Partner"));  //partner column    	    
	    	    
	    	    while (rs.next()){
	    	    	String start = rs.getString("start"); //start
	    	    	String end = rs.getString("end");   //end
	    	    	String partner= rs.getString("partner"); //partner
	    	    	
	    	    	contentPane.add(new Label(start));
	    	    	contentPane.add(new Label(end));
	    	    	contentPane.add(new Label(partner));
	    	    }
	    	    
	    	    f.pack();
	    	    f.setVisible(true);
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