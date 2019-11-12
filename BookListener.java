import java.awt.event.*;
import java.sql.*;
import java.text.*;

import javax.swing.*;

import java.util.Date;


//book listener
public class BookListener implements ActionListener{
	
	JComponent[] inputs;
	
	public BookListener(JComponent[] inputs){
		this.inputs = inputs;
	}	
	
	
	//check valid booking time
	//all appointment between 09:00 and 17:00
	public boolean checkValidTime(String start, String end) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		Date openingTime =  format.parse("09:00");  //opening time 9am
		Date closingTime =  format.parse("17:00");  //closing time 5pm
		
		boolean validTime = false;	
		
		Date startTime =  format.parse(start);  //appointment starts
		Date endTime =  format.parse(end);   //appointment ends
		
		
		//valid if startTime >= 09:00 and endTime <= 17:00
		if ((startTime.after(openingTime) || startTime.equals(openingTime))
				&& (endTime.before(closingTime)) || endTime.equals(closingTime)) {
			validTime = true;
		}	
		
		return validTime;		
	}
	
	
	//check overlapping appointments with other appointments of same partner
	//return true if overlapping happens
	public boolean checkOverlapSamePartner(Connection con, String date, String start, 
			                    String end, String partner) throws SQLException{	
		
		//check overlapping with other appointment of this partner
	    String query = "SELECT * "+
	                   "FROM Appointments "+
	    		       "WHERE date = ? "+
	                   "AND partner = ? "+
	    		       "AND ((start BETWEEN ? AND ?) "+
	                         "OR (end BETWEEN ? AND ? ))";
	                 
	    PreparedStatement stmt = con.prepareStatement(query); 
	      	   
	    //set values for wild card 
	    stmt.setString(1, date);    	    
	    stmt.setString(2, partner);
	    stmt.setString(3, start);    	
	    stmt.setString(4, end);    	
	    stmt.setString(5, start);    	
	    stmt.setString(6, end);  
	    
	    //execute the query
	    ResultSet rs = stmt.executeQuery();    
	    
	    return rs.next() ;	    
	}
	
	//check that patient has appointment with other partner at the same time or not
	public boolean checkOverlapOtherPartner(Connection con, String patientID, String date, String start, 
            String end, String partner) throws SQLException{	
		
		//check overlapping same patient with other partner 
		String otherPartner;
		
		if (partner.equalsIgnoreCase("Dentist")) {
			otherPartner = "hygienist";
		} else {
			otherPartner = "dentist";
		}
		
		String query =  "SELECT * "+
						"FROM Appointments "+
						"WHERE date = ? "+
						"AND partner = ? "+
						"AND patientID = ? "+
						"AND ((start BETWEEN ? AND ?) "+
						      "OR (end BETWEEN ? AND ? ))";
		
		PreparedStatement stmt2 = con.prepareStatement(query); 
		
		//set values for wild card 
		stmt2.setString(1, date);    	    
		stmt2.setString(2, otherPartner);
		stmt2.setString(3, patientID);    	
		stmt2.setString(4, start);    	
		stmt2.setString(5, end);    	
		stmt2.setString(6, start);  
		stmt2.setString(7, end); 
		
		ResultSet rs2 = stmt2.executeQuery();
		
		return rs2.next();	 
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
			JTextField dateField =(JTextField) inputs[0];  //date
		    String date = dateField.getText();
		    
		    JTextField startField = (JTextField) inputs[1]; //start time
		    String start = startField.getText();
		    
		    JComboBox partnerBox = (JComboBox) inputs[2];  //partner
		    String partner = (String) partnerBox.getSelectedItem(); 
		    
		    JTextField endField = (JTextField) inputs[3];  //end time
		    String end = endField.getText();
		    
		    JTextField patientIDField = (JTextField) inputs[4];  //patientID
		    String patientID = patientIDField.getText();
				
		    Connection con = null;
		    
		    try {
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
			    
			    String query = "INSERT INTO Appointments "+
	                           "VALUES (null,?,?,?,?,?)";
	
	            PreparedStatement stmt = con.prepareStatement(query); 
		    	
		        //check booking time valid or not
		        boolean validTime = checkValidTime(start, end);
				    
		        if (validTime) {  //valid time   		    
		            
		            //check overlap with other appointments of the partner 
		        	boolean overlap1 = checkOverlapSamePartner(con, date, start, end, partner);
		        	
		        	//check patient has appointment with other partner at same time or not
			    	boolean overlap2 = checkOverlapOtherPartner(con, patientID, date, start, end, partner);
			    	
				    if (overlap1){    //booking time overlaps				
		    	        			    	
				        JOptionPane.showMessageDialog(null, "Booking Time overlaps with other appointments of "+partner);
				        
				    } else if (overlap2) {  //other partner
				    	
				    	JOptionPane.showMessageDialog(null, "This patient has appointment with other partner around this time");
				    	
				    } else {   //not overlap               
		    	      	   
		    	        //set values for wild card 
		    	        stmt.setString(1, date);
		    	        stmt.setString(2, start);
		    	        stmt.setString(3, end);
		    	        stmt.setString(4, partner);
		    	        stmt.setString(5, patientID);
		    	        
		    	        //execute the query
		    	        stmt.executeUpdate(); 	    
		    	   
		    	        //message dialog	    	      
		    	        JOptionPane.showMessageDialog(null, "Booked successfully !");
				    }
		       } else {  //not valid booking time
		    	   
			    	//message dialog		       
			        JOptionPane.showMessageDialog(null, "Booking time must after 9am and before 5pm !");
		        }
		        
		    } catch (SQLException e1){
	    	    e1.printStackTrace();
	    	    
		    } catch (ParseException e2) {			
				e2.printStackTrace();
				
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