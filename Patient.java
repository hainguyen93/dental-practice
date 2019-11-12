import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.util.*;

import javax.swing.*;


public class Patient {
		
	//register a new patient
	//create the registration form
	public void register() throws SQLException{		
		JFrame frame = new JFrame("Register new patient");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
				
		//main content
		JPanel pane = new JPanel(new SpringLayout());		
		
		//array containing all inputs entered
		JComponent[] inputs = new JComponent[11]; 
		
		//Title field
		JLabel titleLabel = new JLabel("Title", JLabel.TRAILING);
	    pane.add(titleLabel);    //add to main content	    
	    String[] titleLis = {"Ms","Miss","Mrs", "Mr", "Dr", "Prof"};  //all common titles
	    JComboBox titleBox = new JComboBox(titleLis);
	    inputs[0] = titleBox;    //add title combo box to inputs list
	    titleLabel.setLabelFor(titleBox);
		pane.add(titleBox);     //add title combo box to content
		
		//other fields: surname, forename, birthday, ...		
		String[] fields = {"Surname", "Forename", "Birthday (YYYY-MM-DD)","Phone Number",
						   "House Number", "Street", "District", "City" ,"Postcode"};
		
		int numFields = fields.length;
		
		for (int i = 0; i < numFields ; i++){
			JLabel l = new JLabel(fields[i], JLabel.TRAILING);
		    pane.add(l);
		    JTextField textField = new JTextField(20);
		    inputs[i+1] = textField;
			l.setLabelFor(textField);
			pane.add(textField);				
		}		
		
		//health care plan combo box
		JLabel planLabel = new JLabel("Healthcare Plan", JLabel.TRAILING);
		pane.add(planLabel);  //add plan label to content
	    
		
		//list of all health-care plans offered by the dental practice
		//automatically updated with any newly-introduced plan
	    String[] planLis = getPlansLis();
	    JComboBox planBox = new JComboBox(planLis);
	    inputs[10] = planBox;    //add plan box to inputs list
	    planLabel.setLabelFor(planBox);
		pane.add(planBox);    
				
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, 11, 2);
	    
		JButton registerButton = new JButton("Register New Patient");  
	    registerButton.addActionListener(new RegisterListener(inputs)); //listener
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(registerButton);
		
		frame.add(pane, BorderLayout.CENTER);  //add content to main frame
		frame.add(buttonPanel, BorderLayout.PAGE_END); //add button to main frame
		frame.pack();
		frame.setVisible(true);
	}
	
	//subscribe or unsubscribe(setting NULL)
	public void subscribe() throws SQLException{
		
		//main frame
		JFrame frame = new JFrame("Subscribe patient to plan");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		frame.setLayout(new BorderLayout());
				
		//fields
		JComponent[] inputs = new JComponent[2]; 
		JPanel pane = new JPanel(new SpringLayout());
		
		//patientID field
		JLabel l1 = new JLabel("PatientID", JLabel.TRAILING);
	    pane.add(l1);
	    JTextField patientIDField = new JTextField(20);
	    inputs[0] = patientIDField;  //add 
		l1.setLabelFor(patientIDField);
		pane.add(patientIDField);		
		
		//healthcare plan combobox
		JLabel l2 = new JLabel("Healthcare Plan", JLabel.TRAILING);
	    pane.add(l2);	    
	    String[] plans = getPlansLis(); //get all offered plans 	    
	    JComboBox planField = new JComboBox(plans);
	    inputs[1] = planField;
		l2.setLabelFor(planField);
		pane.add(planField);	
		
		//form layout
		FormLayout layout = new FormLayout();
		layout.makeAForm(pane, 2, 2);
				
		JButton subscribeButton = new JButton("Subscribe Patient To Plan");
		subscribeButton.addActionListener(new SubscribeListener(inputs));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(subscribeButton);
		
		frame.add(pane, BorderLayout.CENTER); 
		frame.add(buttonPanel, BorderLayout.PAGE_END);		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	//get all offered health care plan
	public String[] getPlansLis() throws SQLException{
		Connection con = null;
		String[] plans = null;
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
    	    
    	    String query = "SELECT name FROM HealthCarePlanTypes ORDER BY name"; //retrieve all name
    	    
    	    Statement stmt = con.createStatement(); 
    	       
    	    //execute the query
    	    ResultSet rs = stmt.executeQuery(query); 
    	    
    	    ArrayList pl = new ArrayList(); 
    	    pl.add("Null");
    	    
    	    while (rs.next()){
    	    	String plan = rs.getString("name");
    	    	pl.add(plan);
    	    }
    	    
    	    //convert array list to array
    	    plans = new String[pl.size()];
    	    pl.toArray(plans);      	    
    	    
    	} catch (SQLException e){
    	    e.printStackTrace();
        } finally {
    	    if (con !=null) con.close();
        }	
		return plans; //return the list of all offered plans
	}	
	
}

	