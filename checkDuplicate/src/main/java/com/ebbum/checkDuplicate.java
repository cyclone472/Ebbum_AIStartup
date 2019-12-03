package com.ebbum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

public class checkDuplicate {
	// return JSON File
	//{ "isDuplicate" : boolean, "successfullyAccess" : boolean }
	public Map<String, Boolean> handler(Map<String, String> input) {
		Map<String, Boolean> ret = new HashMap<>();
	    
	    String url = System.getenv("DB_URL");
	    String id = System.getenv("DB_ID");
	    String pw = System.getenv("DB_PW");
	    
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("SELECT id FROM userinfo where id = \'" + input.get("id") + "\'");
            //if result has no tuple
            if(!rs.isBeforeFirst()) {
            	ret.put("isDuplicate", false);
            	ret.put("successfullyAccess", true);
            }
            else {
            	ret.put("isDuplicate", true);
            	ret.put("successfullyAccess", true);
            }
        }
        catch( ClassNotFoundException e){
            System.out.println("Driver Loading Failure");
            ret.put("isDuplicate", false);
            ret.put("successfullyAccess", false);
        }
        catch( SQLException e){
            System.out.println("ERROR " + e);
            ret.put("isDuplicate", false);
            ret.put("successfullyAccess", false);
        }
        finally{
            try{
                if( conn != null && !conn.isClosed()){
                    conn.close();
                }
                if( stmt != null && !stmt.isClosed()){
                    stmt.close();
                }
                if( rs != null && !rs.isClosed()){
                    rs.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
	    
		return ret;
	}
}
