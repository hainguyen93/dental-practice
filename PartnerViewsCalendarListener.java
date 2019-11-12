import java.awt.event.*;

public class PartnerViewsCalendarListener implements ActionListener{
	
	String partner;
	
	public PartnerViewsCalendarListener(String partner){
		this.partner = partner;
	}
	
	public void actionPerformed(ActionEvent e) {		
		Appointment app = new Appointment();
		
		//which partner's calendar to show
		String[] partners = {partner};
		app.viewCalendar(partners);				
	}	
}