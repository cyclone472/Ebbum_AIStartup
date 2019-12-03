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
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
//build�ϴ� ��� : cmd�� cd ~/lambda-java-example�� ���� mvn clean install�� ġ�� ��.
//lambda-java-example/target/serverless-aws~~.jar�� lambda�Լ��� ���ε��ϸ��.

public class returnImage implements RequestHandler<RequestClass, ResponseClass> {
	private String topStyleClassifier[][][];
	private String bottomStyleClassifier[][];
	private final int bottomKind = 5;
	private final int topKind = 4;
	private final String[] style1Is = new String[] {"#���ν�Ʈ������", "#���ν�Ʈ������", "#�ܼ��� ����", "#������ ����"};
	private final String[] style2Is = new String[] {"#�����", "#���̳�", "#ī��", "#����"};
	private final String[] style3Is = new String[] {"#������", "#���´���"};
	@Override
	public ResponseClass handleRequest(RequestClass requestClass, Context context) {
		
	    String url = System.getenv("DB_URL");
	    String id = System.getenv("DB_ID");
	    String pw = System.getenv("DB_PW");
		
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Map<String, String>> clothes = new ArrayList<>();
        String clothesKind = requestClass.getClothesKind();
        Integer clothesNum = requestClass.getPictureNum();
        String bodyType = requestClass.getBodyType();
        int height = requestClass.getHeight();        
        
        List<Map<String, String>> retList = new ArrayList<>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();
            
            List<String> style = new ArrayList<>();
            List<String> style1 = new ArrayList<>();
            List<String> style2 = new ArrayList<>();
            List<String> style3 = new ArrayList<>();
            
            //���ǿ� �´� �ʵ��� �з�
            setClassifier();
            int findBodyIdx = (int)(bodyType.charAt(0) - 'A');
            int findHeightIdx = (height > 170 ? 6 : 5);
            System.out.println("bodyIdx, heightIdx : " + findBodyIdx + " " + findHeightIdx);
            switch(clothesKind) {
            case "bottoms":
            	for(int i = 0; i < bottomKind; i++) {
            		//body�� ���ǰ� height�� ������ ��� �����ϴ� �ʵ��� ����
            		if(!bottomStyleClassifier[findBodyIdx][i].equals("") &&
            				bottomStyleClassifier[findBodyIdx][i].equals(bottomStyleClassifier[findHeightIdx][i])) {
            			style.add(bottomStyleClassifier[findBodyIdx][i]);
            		}
            	}            		
            	break;
            case "shoes":
            	/* empty */
            	break;
            case "outers":
            case "tops":
        		for(int i = 0; i < 3; i++) {
        			for(int j = 0; j < topKind; j++) {
                		if(!topStyleClassifier[i][findBodyIdx][j].equals("") &&
                				topStyleClassifier[i][findBodyIdx][j].equals(topStyleClassifier[i][findHeightIdx][j])) {
                			switch(i) {
                			case 0:
                				style1.add(topStyleClassifier[i][findBodyIdx][j]);
                				break;
                			case 1:
                				style2.add(topStyleClassifier[i][findBodyIdx][j]);
                				break;
                			case 2:
                				style3.add(topStyleClassifier[i][findBodyIdx][j]);
                				break;
                			default:
                			}
                		}            			
            		}
            	}
            	break;
            default:
            	throw new SQLException();
            }
            
            System.out.println(style.size() + " " + style1.size() + " " + style2.size() + " " + style3.size());
            for(int i=0;i<style.size();i++) {
            	System.out.print(style.get(i) + "   ");
            }
            System.out.println();
            for(int i=0;i<style1.size();i++) {
            	System.out.print(style1.get(i) + "   ");
            }
            System.out.println();
            for(int i=0;i<style2.size();i++) {
            	System.out.print(style2.get(i) + "   ");
            }
            System.out.println();
            for(int i=0;i<style3.size();i++) {
            	System.out.print(style3.get(i) + "   ");
            }
            System.out.println();
            
            
            if(clothesKind.equals("bottoms")) {
            	for(int i=0;i<style.size();i++) {
                	rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE style = \'%s\' AND gender != \'w\'", clothesKind, style.get(i))); 
        	    	while(rs.next()) {
        	    		int lastIdx = clothes.size();
        	    		clothes.add(new HashMap<>());
        	    		clothes.get(lastIdx).put("ID", Integer.toString(rs.getInt(1)));
        	    		clothes.get(lastIdx).put("name", rs.getString(2));
        	    		clothes.get(lastIdx).put("category", rs.getString(3));
        	    		clothes.get(lastIdx).put("color", Integer.toString(rs.getInt(4)));
        	    		clothes.get(lastIdx).put("style", rs.getString(5));
        	    		clothes.get(lastIdx).put("brand", rs.getString(6));
        	    		clothes.get(lastIdx).put("gender", rs.getString(7));
        	        	clothes.get(lastIdx).put("posterURL", rs.getString(8).substring(0, rs.getString(8).length() - 1));
        	    	}
            	}            	
            }
            else if(clothesKind.equals("shoes")) {
            	rs = stmt.executeQuery("SELECT * FROM shoes WHERE gender = \'m\'"); 
    	    	while(rs.next()) {
    	    		int lastIdx = clothes.size();
    	    		clothes.add(new HashMap<>());
    	    		clothes.get(lastIdx).put("ID", Integer.toString(rs.getInt(1)));
    	    		clothes.get(lastIdx).put("name", rs.getString(2));
    	    		clothes.get(lastIdx).put("category", rs.getString(3));
    	    		clothes.get(lastIdx).put("color", Integer.toString(rs.getInt(4)));
    	    		clothes.get(lastIdx).put("style", rs.getString(5));
    	    		clothes.get(lastIdx).put("brand", rs.getString(6));
    	    		clothes.get(lastIdx).put("gender", rs.getString(7));
    	        	clothes.get(lastIdx).put("posterURL", rs.getString(8).substring(0, rs.getString(8).length() - 1));
    	    	}
            }
            else {
            	for(int i=0;i<style1.size();i++) {
            		for(int j=0;j<style2.size();j++) {
            			for(int k=0;k<style3.size();k++) {
                        	rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE style1 = \'%s\' AND style2 = \'%s\' AND style3 = \'%s\' AND gender != \'W\'",
                        			clothesKind, style1.get(i), style2.get(j), style3.get(k))); 
                        	System.out.println("style1, 2, 3 : " + style1.get(i) + " " + style2.get(j) + " " + style3.get(k));
                	    	while(rs.next()) {
                	    		int lastIdx = clothes.size();
                	    		clothes.add(new HashMap<>());
                	    		clothes.get(lastIdx).put("ID", Integer.toString(rs.getInt(1)));
                	    		clothes.get(lastIdx).put("name", rs.getString(2));
                	    		clothes.get(lastIdx).put("category", rs.getString(3));
                	    		clothes.get(lastIdx).put("color", Integer.toString(rs.getInt(4)));
                	    		clothes.get(lastIdx).put("style1", style1Is[(int)(rs.getString(5).charAt(0) - 'a')]);
                	    		clothes.get(lastIdx).put("style2", style2Is[(int)(rs.getString(6).charAt(0) - 'a')]);
                	    		clothes.get(lastIdx).put("style3", style3Is[(int)(rs.getString(7).charAt(0) - 'a')]);
                	    		clothes.get(lastIdx).put("brand", rs.getString(8));
                	    		clothes.get(lastIdx).put("gender", rs.getString(9));
                	        	clothes.get(lastIdx).put("posterURL", rs.getString(10).substring(0, rs.getString(10).length() - 1));
                	    	}
            			}            				
            		}
            	}
            }

            int columnsNumber = clothes.size();
            //��� Ʃ���� ��ȯ�ؾ��� ���� �������� �� ������ �������� clothes_num����ŭ ��ȯ�Ѵ�.
            if(clothesNum < columnsNumber) {
                //clothes�� ����� ������ ���� �� �������� �ϳ��� pick �Ͽ� �� ��ȯ 
            	for(int i = 0; i < clothesNum; i++) {
	                Random generator = new Random();
	                int idx = generator.nextInt(clothes.size());
	                
	                //clothes�� �ʵ� �� �ϳ��� ��� ���� ������ result List�� �� ������ ���ҿ� �߰��Ѵ�.
	                retList.add(new HashMap<>());
	                for (Map.Entry<String, String> entry : clothes.get(idx).entrySet()) {
	                    retList.get(retList.size()-1).put(entry.getKey(), entry.getValue());
	                }	                
	                //�̹� �߰��� ���� ����
	                clothes.remove(idx);
            	}
            }
            //��� Ʃ���� ������ ��ȯ�ؾ��� ���� �������� �� ������ ��� Ʃ���� ������ŭ�� ��ȯ�Ѵ�.
            else {
            	for(int i=0;i<columnsNumber;i++) {
	                retList.add(new HashMap<>());
	                for (Map.Entry<String, String> entry : clothes.get(i).entrySet())
	                    retList.get(retList.size()-1).put(entry.getKey(), entry.getValue());
            	}
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
	    
        return new ResponseClass(retList, 200, "SUCCESS");
	}
	
	void setClassifier() {
		topStyleClassifier = new String[][][] {
			{{"a", "b", "c", "d"}, {"", "b", "c", ""}, {"a", "", "c", ""}, {"", "b", "c", ""}, {"a", "b", "c", "d"}, {"", "", "c", "d"}, {"a", "b", "c", "d"}},
			{{"a", "b", "", ""}, {"a", "b", "c", "d"}, {"a", "b", "c", "d"}, {"a", "b", "c", ""}, {"a", "b", "c", "d"}, {"a", "b", "c", "d"}, {"a", "b", "c", "d"}},
			{{"a", "b", "", ""}, {"", "b", "", ""}, {"", "b", "", ""}, {"", "b", "", ""}, {"a", "b", "", ""}, {"", "b", "", ""}, {"a", "b", "", ""}}
		};
		bottomStyleClassifier = new String[][] {
			{"wide", "baggy", "pattern", "", ""}, {"wide", "baggy", "", "slim", ""}, {"wide", "baggy", "pattern", "", ""}, {"wide", "baggy", "", "", ""},
				{"wide", "baggy", "pattern", "slim", "skinny"}, {"", "", "pattern", "slim", "skinny"}, {"wide", "baggy", "pattern", "slim", "skinny"}
		};
	}
}
