package com.ebbum;

//import com.ebbum.ResponseClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class getUserInfo implements RequestHandler<RequestClass, ResponseClass> {
	@Override
	public ResponseClass handleRequest(RequestClass requestClass, Context context) {
	    String url = System.getenv("DB_URL");
	    String id = System.getenv("DB_ID");
	    String pw = System.getenv("DB_PW");
		
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        ResponseClass ret = new ResponseClass();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();

    		String userId = requestClass.getId();
            rs = stmt.executeQuery("SELECT * from userinfo where id = \'" + userId + "\'");
            
            //if result has no tuple
            if(!rs.isBeforeFirst()) {
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
                return new ResponseClass(-1);
            }
            while(rs.next()) {
            	ret.setgender(rs.getString(4));
            	ret.setage(rs.getInt(5));
            	ret.setheight(rs.getInt(6));
            	ret.settopSize(rs.getString(7));
            	ret.setpantsSize(rs.getInt(8));
            	ret.setbodyType(rs.getString(9));
            }
        }
        catch( ClassNotFoundException e){
            System.out.println("Driver Loading Failure");
        }
        catch( SQLException e){
            System.out.println("ERROR " + e);
            return new ResponseClass(-1);
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
