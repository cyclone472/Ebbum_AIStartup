package com.ebbum;

import com.ebbum.ResponseClass;
import com.ebbum.RequestClass;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Map;
import java.util.HashMap;

public class setUserInfo implements RequestHandler<RequestClass, ResponseClass> {
	@Override
	public ResponseClass handleRequest(RequestClass requestClass, Context context) {
		
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

            stmt.executeUpdate(String.format("UPDATE userinfo "
            		+ "SET height = %d, gender = \'%s\', topSize = \'%s\', pantsSize = \'%s\', age = %d, bodyType = \'%s\' WHERE id = \'%s\'",
            		requestClass.getHeight(), requestClass.getGender(), requestClass.getTopSize(), requestClass.getpantsSize(), requestClass.getAge(),
            		requestClass.getBodyType(), requestClass.getId()));
        }
        catch( ClassNotFoundException e){
            System.out.println("Driver Loading Failure");
        }
        catch( SQLException e){
            System.out.println("ERROR " + e);
            return new ResponseClass(400, "Bad request");
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
        
        return new ResponseClass(200, "SUCCESS");
	}
}
