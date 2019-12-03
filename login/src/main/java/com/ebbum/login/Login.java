package com.ebbum.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.ebbum.login.RequestClass;
import com.ebbum.login.ResponseClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//build하는 방법 : cmd의 cd ~/lambda-java-example에 들어가서 mvn clean install을 치면 됨.
//lambda-java-example/target/serverless-aws~~.jar을 lambda함수에 업로드하면됨.

public class Login implements RequestHandler<RequestClass, ResponseClass> {
	
	@Override
	public ResponseClass handleRequest(RequestClass requestClass, Context context) {
		
	    String url = System.getenv("DB_URL");
	    String id = System.getenv("DB_ID");
	    String pw = System.getenv("DB_PW");
	    
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String user_name = "";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();
            
            String user_id = requestClass.id;
            String user_pw = requestClass.pw;
            
            rs = stmt.executeQuery(String.format("SELECT name from userinfo where id = '%s' and pw = '%s'", user_id, user_pw));
            while(rs.next()) {
                user_name = rs.getString(1);            	
            }
            System.out.println(String.format("%s, %s, %s", user_name, user_id, user_pw));
        }
        catch(ClassNotFoundException e){
            System.out.println("Driver Loading Failure");
        }
        catch( SQLException e){
            System.out.println("ERROR " + e);
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
        if(user_name.equals(""))
        	return new ResponseClass("Don't exist!");
        else
        	return new ResponseClass(user_name, "Hello, " + user_name + "!", 200, "SUCCESS");
	 }
}
