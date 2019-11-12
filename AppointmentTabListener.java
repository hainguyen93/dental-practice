import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


//listener for appointment tab 
public class AppointmentTabListener implements ActionListener{
	
	JButton[] bLis;  //all buttons on appointment tab
	
	public AppointmentTabListener(JButton[] bLis){
		this.bLis = bLis;
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource(); //which button clicked
		Appointment app = new Appointment();
		
		if (button == bLis[0]){ //book appointment
			app.book();
		} else if (button == bLis[1]){ //cancel
		    app.cancel();
		} else if (button == bLis[2]){ //book empty appointment
			app.bookEmpty();
		} else if (button == bLis[3]){  //find an appointment
			app.find();
		} else {   //view appointments calendar
			String partners[] = new String[]{"dentist","hygienist"};
			app.viewCalendar(partners);
		}
	}	
}
