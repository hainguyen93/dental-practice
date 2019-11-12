import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class ModifyListener implements ActionListener{
	
	String patientID;
		
	public ModifyListener(String patientID){
		this.patientID = patientID;
	}
	    
	//pay for a treatment
	public void payTreatment(Connection con, String name, String appointmentID) throws SQLException{		
			        	    	    
	    Statement stmt = con.createStatement();
	    
	    String query = "UPDATE Treatments "+
	                   "SET paid=true "+
	    		       "WHERE appointmentID = "+appointmentID+ " "+
	                   "AND name=\'"+name+"\'";
	    
	    stmt.executeUpdate(query);		
	}
	
	//update the level of service left for given patients
	public void updateService(Connection con, String service, int value, String patientID) throws SQLException{
		  	        	    	    
	    Statement stmt = con.createStatement();
	    
	    String query = "UPDATE Patients "+
	                   "SET "+ service +" = "+value+" "+
	    		       "WHERE patientID = "+patientID;    	                   
	    
	    stmt.executeUpdate(query);		
	}

	
	public void actionPerformed(ActionEvent e) {

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
    	    
    	    //get left level of service for this patient    	    
    	    Statement stmt1 = con.createStatement();
    	    
    	    String query1 = "SELECT coveredCheckup, coveredHygiene, coveredRepair "+
    	                    "FROM Patients "+
    	    		        "WHERE patientID = "+patientID;
    	    
    	    ResultSet rs1 = stmt1.executeQuery(query1);
    	    rs1.next();
    	    
    	    //number of free services left
    	    int checkup = rs1.getInt("coveredCheckup");
    	    int hygiene = rs1.getInt("coveredHygiene");
    	    int repair = rs1.getInt("coveredRepair");
    	        	    
    	    //get all unpaid treatments for given patient
    	    String query2 = "SELECT T.name, T.appointmentID "+
    	                    "FROM Appointments AS A, Treatments AS T "+
    	    		        "WHERE A.appointmentID = T.appointmentID "+
    	                    "AND T.paid = false "+
    	    		        "AND A.patientID="+patientID+" "+
    	    		        "ORDER BY A.date";
    	    
    	    Statement stmt2 = con.createStatement(); 
    	    
    	    //execute the query
    	    ResultSet rs2 = stmt2.executeQuery(query2);
    	        	    
    	    while (rs2.next()){
    	    	String name = rs2.getString("name");
    	    	String appointmentID = rs2.getString("appointmentID");
    	    	
    	    	if (name.equalsIgnoreCase("Checkup") && (checkup > 0)){
    	    		payTreatment(con, name, appointmentID);
    	    		checkup--;
    	    		updateService(con, "coveredCheckup", checkup, patientID);
    	    		
    	    	} else if (name.equalsIgnoreCase("Hygiene") && (hygiene > 0)){
    	    		payTreatment(con, name, appointmentID);
    	    		hygiene--;
    	    		updateService(con, "coveredHygiene", hygiene, patientID);
    	    		
    	    	} else if (repair > 0){
    	    		payTreatment(con, name, appointmentID);
    	    		repair--;
    	    		updateService(con, "coveredRepair", repair, patientID);
    	    	}  	    	    	
    	    }   	    
    	   
    	    //display modified total cost    	 
    	    JFrame f = new JFrame("Modified Review Treatments");
    	    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    	   
    	    Container contentPane = f.getContentPane();
    	    contentPane.setLayout(new BorderLayout());  
    	    
    	    JLabel l = new JLabel("The total cost has been modified");
    	    contentPane.add(l, BorderLayout.PAGE_START);
    	    
    	    JPanel pane = new JPanel(new GridLayout(0,4));
    	    pane.add(new Label("Treatment"));  //treatment name column
    	    pane.add(new Label("Cost"));  //cost column
    	    pane.add(new Label("Date"));  //date column
    	    pane.add(new Label("Partner"));	    //partner column		       		
        	   
    	    String query = "SELECT T.name, T.cost, A.date, A.partner "+
    	                   "FROM Appointments AS A, Treatments AS T "+
    	    		       "WHERE A.appointmentID = T.appointmentID "+
    	                   "AND T.paid = false "+
    	    		       "AND A.patientID="+patientID+" "+
    	    		       "ORDER BY A.date";
    	    
    	    Statement stmt = con.createStatement(); 
    	    
    	    //execute the query
    	    ResultSet rs = stmt.executeQuery(query);
    	    
    	    int newTotalCost = 0;
    	    
    	    while (rs.next()){
    	    	String name = rs.getString("name"); //treatment name
    	    	int cost = rs.getInt("cost");  //cost
    	    	String date = rs.getString("date");  //date
    	    	String partner = rs.getString("partner");  //partner
    	    	
    	    	pane.add(new JLabel(name));
    	    	pane.add(new JLabel(""+cost));
    	    	pane.add(new JLabel(date));
    	    	pane.add(new JLabel(partner));
    	    	
    	    	//update totalCost
    	    	newTotalCost += cost;
    	    }
    	    
    	    contentPane.add(pane, BorderLayout.CENTER);    	 
    	    
    	    JLabel l2 = new JLabel("New Total Cost:  " + newTotalCost);      	    
    	    contentPane.add(l2, BorderLayout.PAGE_END);
    	    
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