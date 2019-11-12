import java.awt.*;
import javax.swing.*;


public class FormLayout {	
	
	public void makeAForm(Container pane, int totalRows, int totalCols){
		
		//get the layout manager of pane
		SpringLayout layout = (SpringLayout) pane.getLayout();		
								
		Spring x = Spring.constant(15);
		Spring y = Spring.constant(15);
		
		//list of all components in column 
		JComponent[] cp1 = categoriseIntoColumn(pane, totalRows, totalCols, 0);
		Spring col1Width = findMaxWidth(layout, cp1);
		
		//list components in column 2
		JComponent[] cp2 = categoriseIntoColumn(pane, totalRows, totalCols, 1);
		Spring col2Width = findMaxWidth(layout, cp2);	
		Spring rowHeight = findMaxHeight(layout, cp2);
		
		//find the height of column c		
		
		for (int i = 0; i < cp1.length; i++){					
			SpringLayout.Constraints constraint = layout.getConstraints(cp1[i]);				
			constraint.setX(x);				
			constraint.setWidth(col1Width);
			constraint.setY(y);	
			constraint.setHeight(rowHeight);		
			
			y = Spring.sum(y, Spring.sum(Spring.constant(15), rowHeight));			
		}
		
		x = Spring.sum(Spring.sum(col1Width, Spring.constant(15)), x);
		y = Spring.constant(15);	 		
		
		for (int i = 0; i < cp2.length; i++){					
			SpringLayout.Constraints constraint = layout.getConstraints(cp2[i]);				
			constraint.setX(x);		
			constraint.setWidth(col2Width);
			constraint.setY(y);	
			constraint.setHeight(rowHeight);	
			
			y = Spring.sum(y, Spring.sum(Spring.constant(15), rowHeight));
		}
		
		x = Spring.sum(Spring.sum(x, col2Width), Spring.constant(15));		
		
		//more than 2 columns
		if (totalCols >2){
			
			y = Spring.constant(15);
			
			//list components in column 3
			JComponent[] cp3 = categoriseIntoColumn(pane, totalRows, totalCols, 2);
			Spring col3Width = findMaxWidth(layout, cp3);
			
			for (int i = 0; i < cp3.length; i++){					
				SpringLayout.Constraints constraint = layout.getConstraints(cp3[i]);				
				constraint.setX(x);		
				constraint.setWidth(col3Width);
				constraint.setY(y);	
				constraint.setHeight(rowHeight);	
				
				y = Spring.sum(y, Spring.sum(Spring.constant(15), rowHeight));
			}
			
			x = Spring.sum(Spring.sum(x, col3Width), Spring.constant(15));	
		}
		
		//Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(pane);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);						
	}
	
		
	//return an array containing all components on that column
	public JComponent[] categoriseIntoColumn(Container pane, int totalRows, int totalCols, int col){
		
		JComponent[] cp = new JComponent[totalRows];
		
		for (int r = 0; r < totalRows; r++){			
			cp[r] = (JComponent) pane.getComponent(r*totalCols + col);			
		}		
		
		return cp;
	}	
	
	
	
	//find the maximum width between all component in a column
	public Spring findMaxWidth(SpringLayout layout, JComponent[] cp){	
		
		Spring maxWidth = Spring.constant(0);
		
		for (JComponent component : cp){
			SpringLayout.Constraints c = layout.getConstraints(component);
			
			//get width of this component
			Spring w = c.getWidth();
			
			maxWidth = Spring.max(maxWidth, w);
		}
		
		return maxWidth;
	}
	
	
	//find the maximum width between all component in a column
	public Spring findMaxHeight(SpringLayout layout, JComponent[] cp){
			
		Spring maxHeight = Spring.constant(0);
		
		for (JComponent component : cp){
			SpringLayout.Constraints c = layout.getConstraints(component);
			
			//get width of this component
			Spring w = c.getHeight();
			
			maxHeight = Spring.max(maxHeight, w);
		}
		
		return maxHeight;
	}
}
