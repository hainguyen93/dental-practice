import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;


//listener for registration tab
public class RegistrationTabListener implements ActionListener{
	
	JButton[] bLis;  //list of all buttons on registration tab
	
	public RegistrationTabListener(JButton[] bLis){
		this.bLis = bLis;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource(); //which button clicked
		Patient patient = new Patient();
		
		if (button == bLis[0]){ //register			
				try {
					patient.register();  //show registration form
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}			
		} else {  //subscribe or unsubscribe 
			try {
				patient.subscribe();  //show subscribe or unsubscribe form
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
		}
	}	
}