import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;



//listener for payment tab 
class PaymentTabListener implements ActionListener{
	
	JButton[] bLis;  //all buttons on payment tab
	
	public PaymentTabListener(JButton[] bLis){
		this.bLis = bLis;
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();  //which button clicked
		Treatments tm = new Treatments();
		
		if (button == bLis[0]){  //review or modify treatments
		    tm.review();		
		} else {   //pay outstanding bill
			tm.payBill();
		}
	}	
}

