package com.ebbum;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ebbum.RequestClass;
import com.ebbum.ResponseClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
//build하는 방법 : cmd의 cd ~/lambda-java-example에 들어가서 mvn clean install을 치면 됨.
//lambda-java-example/target/serverless-aws~~.jar을 lambda함수에 업로드하면됨.

public class returnBest implements RequestHandler<RequestClass, ResponseClass> {
	@Override
	public ResponseClass handleRequest(RequestClass requestClass, Context context) {
		
	    String url = System.getenv("DB_URL");
	    String id = System.getenv("DB_ID");
	    String pw = System.getenv("DB_PW");
		
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Map<String, String>> clothes = new ArrayList<>();
       // Integer clothes_num = requestClass.getPictureNum();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();

            //recommand table에서 정보를 가져와서 반환
            rs = stmt.executeQuery("SELECT * from dailybest");
            while(rs.next()) {
	    		int lastIdx = clothes.size();
            	clothes.add(new HashMap<>());
            	clothes.get(lastIdx).put("kind", rs.getString(2));
            	clothes.get(lastIdx).put("posterURL", rs.getString(1));         	
            }
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
        
        return new ResponseClass(clothes, 200, "SUCCESS");
	}
}
