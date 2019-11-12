import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


//viewCalendar listener
public class ViewCalendarListener implements ActionListener{
	
    JComponent[] inputs;
    String[] partners;
	
	public ViewCalendarListener(JComponent[] inputs, String[] partners){
		this.inputs = inputs;
		this.partners = partners;
	}

	public void actionPerformed(ActionEvent e) {
		
		//get values entered
		JTextField fromField = (JTextField) inputs[0]; //from date
	    String from = fromField.getText();  
	    
	    JTextField toField = (JTextField) inputs[1]; //to date
	    String to = toField.getText();  
	    
	    ValidateInputsForm check = new ValidateInputsForm();
	    boolean validInputs = check.validateForm(inputs);
	    
	    //check all fields filled or not
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else {  //all fields filled
	    	 
		    //frame to show result
		    JFrame frame = new JFrame("Appointment Calendar"); //calendar	    
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	    
		    
		    JTabbedPane tabPane = new JTabbedPane();	    
		    
		    JPanel dentistTab;  //dentist calendar
		    JPanel hygienistTab;  //hygienist calendar
		  
			try {
				
				if (partners.length > 1){  //for both partners
					dentistTab = makeCalendar("Dentist", from, to);
					
					hygienistTab = makeCalendar("Hygienist", from, to);
					
					tabPane.addTab("Dentist", null, dentistTab, null);			
				    tabPane.addTab("Hygienist", null, hygienistTab, null);
				    
				} else {  //for one partner
					String partner = partners[0]; 				
					JPanel tab = makeCalendar(partner, from, to);
					tabPane.addTab(partner, null, tab, null);
				}			
			    
			} catch (SQLException e1) {			
				e1.printStackTrace();
			}           
		    
			frame.setContentPane(tabPane);	
			frame.pack();
			frame.setVisible(true);        					
	    }	   	
	}
	
	
	//make calendar for a partner
	public JPanel makeCalendar(String partner, String from, String to) throws SQLException{
		
		JPanel calendar = new JPanel(new GridLayout(0,4)); //4 columns	
		
	    calendar.add(new JLabel("Date"));  //date column
	    calendar.add(new JLabel("Start time"));  //start time column
	    calendar.add(new JLabel("End time"));	  //end time column
	    calendar.add(new JLabel("Patient Name"));  //patient name column
	    
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
    	    
    	    String query = "SELECT * "+
    	                   "FROM Appointments "+
    	    		       "WHERE partner = ? "+
    	                   "AND date between ? and ? "+
    	    		       "AND patientID IS NOT NULL "+
    	                   "ORDER BY date, start";
    	    
    	    PreparedStatement stmt = con.prepareStatement(query);
    	    
    	    //set values for wild card 
    	    stmt.setString(1, partner);
    	    stmt.setString(2, from);
    	    stmt.setString(3, to);
    	    
    	    //execute the query
    	    ResultSet rs = stmt.executeQuery();
    	    
    	    while (rs.next()){
    	       	calendar.add(new JLabel(rs.getString("date"))); //date
    	       	calendar.add(new JLabel(rs.getString("start"))); //start time
    	       	calendar.add(new JLabel(rs.getString("end"))); //end time
    	       	
    	       	//retrieve patient name
    	       	String patientID = rs.getString("patientID"); //patient ID
    	       	
    	       	query = "SELECT surname, forename "+
    	       	        "FROM Patients "+
    	       			"WHERE patientID = "+patientID;
    	       	
    	       	Statement st = con.createStatement();
    	       	
    	       	ResultSet name = st.executeQuery(query); 
    	       	
    	       	name.next(); //point to first row
    	       	
    	       	String forename = name.getString("forename");  //forename
    	       	String surname = name.getString("surname");  //surname
    	       	String patientName =  forename +" "+ surname;
    	       	
    	       	calendar.add(new JLabel(patientName)); //add patient name
    	    }
    	} catch (SQLException e){
    	    e.printStackTrace();
        } finally {
    	    if (con !=null) con.close();
        }
		
		return calendar;		
	}
	
}