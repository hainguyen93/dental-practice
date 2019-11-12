import java.sql.*;


public class Address {
	
	
	//register an address, 
	//if address already existing, return existing addressID,
	//if new, register new address to Address table, return newly-created addressID
	public int registerAddress(Connection con, String houseNo,  String street, String district,
                                String city, String postcode) throws SQLException{
		boolean existing = checkExistingAddress(con, houseNo, postcode);
		
		if (existing){   //existing address
			int existingAdd = getExistingAddressID(con, houseNo, postcode);
			return existingAdd;             //return existing addressID
		} 
		
		int newAdd = addNewAddress(con, houseNo,  street, district, city, postcode);
		return 	newAdd;   //return new created addressID
	}
	
	
	//check an address exists in database or not
	public boolean checkExistingAddress(Connection con, String houseNo, String postcode) throws SQLException{
			
		//SQL query, using prepared statement    	    
		String query = "SELECT * FROM Address WHERE houseNo = ? and postcode = ?";
    	PreparedStatement stmt = con.prepareStatement(query); 
    	    
    	//set values for wild card 
	    stmt.setString(1, houseNo);
	    stmt.setString(2, postcode);
	    
	    //execute the query
	    ResultSet rs = stmt.executeQuery(); 
	    
	    return (rs.next() != false); 
	}
	
		
	//add new address to table Address, return newly-registered addressID	
	public int addNewAddress(Connection con, String houseNo,  String street, String district,
			                  String city, String postcode) throws SQLException{			
	    	
		//sql query
	    String query = "INSERT INTO Address VALUES (null,?,?,?,?,?)";
	    PreparedStatement stmt1 = con.prepareStatement(query); 
	    
	    //set values for wild card 
	    stmt1.setString(1, houseNo);
	    stmt1.setString(2, street);
	    stmt1.setString(3, district);
	    stmt1.setString(4, city);
	    stmt1.setString(5, postcode);
	    
	    //execute the query
	    stmt1.executeUpdate();    
	    
	    //return the last created key
	    Statement stmt2 = con.createStatement();
	    String query1 = "SELECT LAST_INSERT_ID() FROM Address";
	    ResultSet rs = stmt2.executeQuery(query1); //get newly-created addressID
	    rs.next();  //move to 1st element
	    int addressID = rs.getInt(1);    	    
    	
		return addressID;  //return newly-created primary key
	}
	
	
	//get addressID for an existing address
	public int getExistingAddressID(Connection con, String houseNo, String postcode) throws SQLException{
		
		//sql query
	    String query = "SELECT addressID FROM Address WHERE houseNo = ? and postcode = ?";
	    PreparedStatement stmt = con.prepareStatement(query); 
	    
	    //set values for wild card 
	    stmt.setString(1, houseNo);
	    stmt.setString(2, postcode);
	   
	    //execute the query
	    ResultSet rs = stmt.executeQuery();   
	    rs.next(); //point to 1st element
	    int addressID = rs.getInt("addressID");  	    
    	
		return addressID; 
	}
		
}
