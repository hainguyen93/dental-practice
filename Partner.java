import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Partner extends JFrame{
	
	String partner;		
	
	public Partner(String partner){
		super(partner.substring(0, 1).toUpperCase()+partner.substring(1));
		this.partner = partner;
		setSize(500,450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		
				
		setContentPane(makeContent());  //make content
		
		setVisible(true);
	}

	
	//record seen patient, treatments, costs
	private JPanel makeContent() {
		JPanel contentPane = new JPanel(new GridLayout(0,1));
		
		//view Calendar
		JPanel pane1 = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints(); //centered buttons
		pane1.setBorder(BorderFactory.createTitledBorder("View Calendar"));  //set border
		
		JButton button1 = new JButton("View Appointments Calendar"); //button
		button1.addActionListener(new PartnerViewsCalendarListener(partner));
		button1.setPreferredSize(new Dimension(300, 30));
		pane1.add(button1, gbc);
		contentPane.add(pane1);			
		
		//record appointments
		JPanel pane2 = new JPanel(new GridBagLayout());			
		pane2.setBorder(BorderFactory.createTitledBorder("Record treatments"));  //set border
		
		JButton button2 = new JButton("Record Treatments"); //button
		button2.addActionListener(new RecordListener());
		button2.setPreferredSize(new Dimension(300, 30));
		pane2.add(button2, gbc);
		contentPane.add(pane2);	
		
		return contentPane;
	}
}


class RecordListener implements ActionListener{	
		
	public void actionPerformed(ActionEvent e) {
		Treatments tm = new Treatments();
		tm.record();
	}	
}

