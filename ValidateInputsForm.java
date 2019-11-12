import java.util.Arrays;

import javax.swing.*;

public class ValidateInputsForm {
	
	//validate all inputs entered in the form
	//for use if the form containing JTextField only
	public boolean validateForm(JComponent[] inputs){
		
		for(JComponent txtField : inputs) {
			
		   JTextField txt = (JTextField) txtField;
		   
		   String value = txt.getText(); //value entered in form
		   
		   if (value.equals("")){
			   
		      return false;
		   }
		}
		
		return true;
	}
	
	
	//For use if the form contains both JTextField and JComboBox
	//array "index" indicates the index of JComboBox components in the "inputs" array
	public boolean validateFormExtended(JComponent[] inputs, int[] index){
		
		for (int i = 0; i < inputs.length; i++){
			
			//if component is a combo box
			boolean isComboBox = containsIn(i, index);
			
			if (!isComboBox) {
				
				JTextField txt = (JTextField) inputs[i];
				   
			    String value = txt.getText(); //value entered in form
			   
			    if (value.equals("")){				   
			        return false;
			    }
			}
		}
		
		return true;
	}
	
	
	//check an array contains a value or not
	public boolean containsIn(int x, int[] index){
		for (int i : index){
			if (i == x){
				return true;
			}
		}
		
		return false;
	}
	
}


