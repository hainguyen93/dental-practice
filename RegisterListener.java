import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;


//listener for processing registration form
public class RegisterListener implements ActionListener{
	
	JComponent[] inputs; //list of all inputs entered
	
	public RegisterListener(){}
	
	public RegisterListener(JComponent[] inputs){
		this.inputs = inputs;
	}	

	
	//is under 18 ?
	public boolean isUnder18(String birthday) throws ParseException{		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date b = (Date) df.parse(birthday);  
		Calendar bDate = Calendar.getInstance();
		bDate.setTime(b); //calendar object of birth date
		
		//current date
		Calendar now = Calendar.getInstance();		
		
		//calculate age, only consider YEAR
		int age = now.get(Calendar.YEAR) - bDate.get(Calendar.YEAR);
		
		return (age < 18);
	}
	
		
	//activated when "Register New Patient" button clicked
	public void actionPerformed(ActionEvent e) {
		
		ValidateInputsForm check = new ValidateInputsForm();
		
	    int[] index = new int[]{0, 10};
	    
	    boolean validInputs = check.validateFormExtended(inputs, index);
	    
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else { 
	    	
			 //retrieve all values entered in registration form    	    
		    JComboBox titleBox = (JComboBox) inputs[0];  //title combo Box
		    String title = (String) titleBox.getSelectedItem();   //selected title 
		    
		    String surname  = ((JTextField) inputs[1]).getText();   //surname 	    
		    String forename = ((JTextField) inputs[2]).getText();   //fore-name    
		    String birthday = ((JTextField) inputs[3]).getText();   //birthday 	    
	        String phoneNo  = ((JTextField) inputs[4]).getText();    //phone no       
		    String houseNo  = ((JTextField) inputs[5]).getText();   //houseNo      	  
		    String street   = ((JTextField) inputs[6]).getText();   //street    
		    String district = ((JTextField) inputs[7]).getText();   //district    	   
		    String city     = ((JTextField) inputs[8]).getText();   //city     	    
		    String postcode = ((JTextField) inputs[9]).getText(); //post code    
		    
		    JComboBox planBox = (JComboBox) inputs[10];  //plan combo box
		    String planName = (String) planBox.getSelectedItem(); //selected plan   
	        	
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
	    	    
			    //insert new row into Patient table
	    	    PreparedStatement stmt = null;
	    	    
	    	    String query; 	        	   	    
	    	    
	    	    //check age is less than 18 or not
	    	    boolean less18 = isUnder18(birthday);
	    	    
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

	        	    //register address to Address table,
	        	    //get newly-created addressID or existing one if address already registered.
	        	    Address add = new Address();
	        	    int addressID = add.registerAddress(con, houseNo, street, district, city, postcode);
	        	    
	        	    //get level of service
	        	    Plans pl = new Plans();
	        	    int[] levelOfService = pl.getService(con, planName);
	        	    
	        	    int noCheckup = levelOfService[0];  //free noCheckup
	        	    int noHygiene  = levelOfService[1];  //free noHygiene
	        	    int noRepair = levelOfService[2];   //free noRepair
	        	            	            	    
	        	    if (planName.equalsIgnoreCase("Null")){   //not follow plan
	        	        query = "INSERT INTO Patients VALUES (null,?,?,?,?,?,?,null,?,?,?)";
	        	        stmt = con.prepareStatement(query);     
	        	        
	        	        stmt.clearParameters();
	        	        stmt.setString(7, "" + noCheckup);   
	        	        stmt.setString(8, "" + noHygiene);   
	        	        stmt.setString(9, "" + noRepair);   
	        	    }else{    //follow plan
	        	    	query = "INSERT INTO Patients VALUES (null,?,?,?,?,?,?,?,?,?,?)";    
	        	    	stmt = con.prepareStatement(query); 
	        	    	 
	        	    	stmt.clearParameters();
	        	    	stmt.setString(7, planName);
	        	        stmt.setString(8, "" + noCheckup);
	        	        stmt.setString(9, "" + noHygiene);
	        	        stmt.setString(10, "" + noRepair);
	        	    }    	    
	        	    
	        	    stmt.setString(1, title);     //title
	    	        stmt.setString(2, surname);   //surname
	    	        stmt.setString(3, forename);  //forename
	    	        stmt.setString(4, birthday);  //birthday
	    	        stmt.setString(5, phoneNo);  //phoneNo
	    	        stmt.setString(6, ""+addressID);   //addressID
	    	        
	        	    //execute
	        	    stmt.executeUpdate();  
	        	    
	        	    //return the newly-created value of primary key
	        	    Statement st = con.createStatement();
	        	    query = "SELECT LAST_INSERT_ID() FROM Patients";
	        	    
	        	    ResultSet rs = st.executeQuery(query);    	    
	        	    rs.next();    //move to first row
	        	    int patientID = rs.getInt(1);
	        	    
	        	    //dialog show patientID        	      
	    	        JOptionPane.showMessageDialog(null, "Patient ID is "+patientID);    	       
	    	    }
	    	    
		        
	    	} catch (SQLException e1){
	    	    e1.printStackTrace();
	    	    
	        } catch (ParseException e1) {			
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