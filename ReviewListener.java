import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


class ReviewListener implements ActionListener{
	
	JTextField inputs;
	
	
	public ReviewListener(JTextField inputs){
		this.inputs = inputs;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//retrieve entered patientID value
		String patientID = inputs.getText();
		
		ValidateInputsForm check = new ValidateInputsForm();
	    boolean validInputs = check.validateForm(new JComponent[]{inputs});
	    
	    //check all fields filled or not
	    if (!validInputs){   //some fields not filled 
	    	
	    	//dialog show patientID        	      
	        JOptionPane.showMessageDialog(null, "At least one field not filled yet \n Please filling them all");    	   
	        
	    } else {  //all fields filled
	    	
	    	//frame to show result
		    JFrame f = new JFrame("Review Treatments");
		    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    
		    Container contentPane = f.getContentPane();  //content pane
		    contentPane.setLayout(new BorderLayout());  
		    
		    //description in the header
		    JLabel header = new JLabel("These are all unpaid treatments");
		    contentPane.add(header, BorderLayout.PAGE_START);
		    
		    //content
		    JPanel pane = new JPanel(new BorderLayout());
		    
		    JPanel pane1 = new JPanel(new GridLayout(0,4));
		    pane1.add(new Label("Treatment Name"));  //treatment name column
		    pane1.add(new Label("Cost"));  //cost column
		    pane1.add(new Label("Date"));  //date column
		    pane1.add(new Label("Partner"));	 //partner column	
		    
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
	    	    
	    	    String query = "SELECT T.name, T.cost, A.date, A.partner "+
	    	                   "FROM Appointments AS A, Treatments AS T "+
	    	    		       "WHERE A.appointmentID = T.appointmentID "+
	    	                   "AND T.paid = false "+
	    	    		       "AND A.patientID="+patientID+" "+
	    	    		       "ORDER BY A.date";
	    	    
	    	    Statement stmt = con.createStatement(); 
	    	    
	    	    //execute the query
	    	    ResultSet rs = stmt.executeQuery(query);
	    	    
	    	    int totalCost = 0;
	    	    
	    	    while (rs.next()){
	    	    	String name = rs.getString("name");  //treatment name
	    	    	int cost = rs.getInt("cost");  //cost
	    	    	String date = rs.getString("date");  //date
	    	    	String partner = rs.getString("partner");  //partner
	    	    	
	    	    	pane1.add(new JLabel(name));
	    	    	pane1.add(new JLabel(""+cost));
	    	    	pane1.add(new JLabel(date));
	    	    	pane1.add(new JLabel(partner));
	    	    	
	    	    	//update totalCost
	    	    	totalCost += cost;
	    	    }
	    	    
	    	    pane.add(pane1, BorderLayout.CENTER); 
	    	    
	    	    JLabel l2 = new JLabel("Total Cost:  "+totalCost);  //showing total cost
	    	    pane.add(l2, BorderLayout.PAGE_END);	
	    	    
	    	    contentPane.add(pane, BorderLayout.CENTER); //add to content pane
	    	    
	    	    //"modify" button
	    	    JPanel buttonPanel = new JPanel();
	    	    JButton modifyButton = new JButton("Modify Total Cost For On-Plan Patient");
	    	    buttonPanel.add(modifyButton);
	    	    contentPane.add(buttonPanel, BorderLayout.PAGE_END); //add to content pane
	    	    modifyButton.addActionListener(new ModifyListener(patientID));
	    	    
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
