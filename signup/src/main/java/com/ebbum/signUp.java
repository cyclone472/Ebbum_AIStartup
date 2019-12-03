package com.ebbum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ebbum.RequestClass;
import com.ebbum.ResponseClass;

//build하는 방법 : cmd의 cd ~/lambda-java-example에 들어가서 mvn clean install을 치면 됨.
//lambda-java-example/target/serverless-aws~~.jar을 lambda함수에 업로드하면됨.

public class signUp implements RequestHandler<RequestClass, ResponseClass> {
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
            
            String _id = requestClass.getId();
            String _pw = requestClass.getPw();
            String _name = requestClass.getName();
            
            rs = stmt.executeQuery(String.format("SELECT id from userinfo where id = \'%s\'", _id));
           
            if(!rs.isBeforeFirst()) {
            	stmt.executeUpdate(String.format("INSERT INTO userinfo (ID, pw, name) values (\"%s\", \"%s\", \"%s\")", _id, _pw, _name));
                try {
                    if(conn != null && !conn.isClosed())
                       conn.close();
                    if(stmt != null && !stmt.isClosed())
                        stmt.close();
                }
                catch( SQLException e){
                    e.printStackTrace();
                }
            	return new ResponseClass(200, "SUCCESS");
            }
            else {
                try {
                    if(conn != null && !conn.isClosed())
                       conn.close();
                    if(stmt != null && !stmt.isClosed())
                        stmt.close();
                    if(rs != null && !rs.isClosed())
                        rs.close();
                }
                catch( SQLException e){
                        e.printStackTrace();
                }
            	return new ResponseClass(403, "Forbidden");          	
            }
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
        return new ResponseClass(404, "Not Found");
	 }
}
