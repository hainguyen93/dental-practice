import java.sql.*;

public class Plans {
	
	
	public int[] getService(Connection con, String plan) throws SQLException{
		int[] level;  //level of service
		
		if (plan.equalsIgnoreCase("Null")){  //not follow plan
			level = new int[]{0,0,0};
		} else {   //follow plan
			level = getLevelOfService(con, plan);
		}
		
		return level;
	}
	
	
	//get level of service of plan
	public int[] getLevelOfService(Connection con, String plan) throws SQLException{
		
		int[] levelOfService = new int[3];		
	
	    String query = "SELECT * FROM HealthCarePlanTypes WHERE name = \""+plan+"\"";
	    Statement stmt = con.createStatement(); 
	    ResultSet rs = stmt.executeQuery(query);
	    
	    rs.next();  //move to first element
	    levelOfService[0] = rs.getInt("noCheckup");
	    levelOfService[1] = rs.getInt("noHygiene");
	    levelOfService[2] = rs.getInt("noRepair");    	   
	   
		return levelOfService;
	}	
}
