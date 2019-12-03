package com.ebbum;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ResponseClass {

    private List<Map<String, String>> clothes;
    private Integer exitCode;
    private String resultSet;
    
    public ResponseClass() {}
    
    public ResponseClass(Integer exitCode, String resultSet) {
    	clothes = new ArrayList<>();
    	this.exitCode = exitCode;
    	this.resultSet = resultSet;
    }

    public ResponseClass(List<Map<String, String>> input, Integer exitCode, String resultSet) {
        this.clothes = new ArrayList<>();
        if(input.get(0).get("posterURL").contains("shoes") || input.get(0).get("posterURL").contains("bottoms")) {
	        for(int i = 0; i < input.size(); i++) {
	        	clothes.add(new HashMap<>());
	        	int newIdx = clothes.size()-1;
	        	clothes.get(newIdx).put("ID", input.get(i).get("ID"));
	        	clothes.get(newIdx).put("posterURL", input.get(i).get("posterURL"));
	        	clothes.get(newIdx).put("name", input.get(i).get("name"));
	        	clothes.get(newIdx).put("category", input.get(i).get("category"));
	        	clothes.get(newIdx).put("color", input.get(i).get("color"));
	        	clothes.get(newIdx).put("style", input.get(i).get("style"));
	        	clothes.get(newIdx).put("brand", input.get(i).get("brand"));
	        	clothes.get(newIdx).put("gender", input.get(i).get("gender"));
	        }
	        this.exitCode = exitCode;
	        this.resultSet = resultSet;
        }
        else {
	        for(int i = 0; i < input.size(); i++) {
	        	clothes.add(new HashMap<>());
	        	int newIdx = clothes.size()-1;
	        	clothes.get(newIdx).put("ID", input.get(i).get("ID"));
	        	clothes.get(newIdx).put("posterURL", input.get(i).get("posterURL"));
	        	clothes.get(newIdx).put("name", input.get(i).get("name"));
	        	clothes.get(newIdx).put("category", input.get(i).get("category"));
	        	clothes.get(newIdx).put("color", input.get(i).get("color"));
	        	clothes.get(newIdx).put("style1", input.get(i).get("style1"));
	        	clothes.get(newIdx).put("style2", input.get(i).get("style2"));
	        	clothes.get(newIdx).put("style3", input.get(i).get("style3"));
	        	clothes.get(newIdx).put("brand", input.get(i).get("brand"));
	        	clothes.get(newIdx).put("gender", input.get(i).get("gender"));
	        }
	        this.exitCode = exitCode;
	        this.resultSet = resultSet;        	
        }
    }
    
    public List<Map<String,String>> getClothes() {
    	return clothes;
    }
    
    public String getResultSet() {
    	return resultSet;
    }

    public Integer getExitCode() {
        return exitCode;
    }
}