package com.ebbum;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ResponseClass {

    private List<Map<String, String>> bestSelling;
    private Integer exitCode;
    private String resultSet;
    
    public ResponseClass() {}
    
    public ResponseClass(Integer exitCode, String resultSet) {
    	bestSelling = new ArrayList<>();
    	this.exitCode = exitCode;
    	this.resultSet = resultSet;
    }

    public ResponseClass(List<Map<String, String>> input, Integer exitCode, String resultSet) {
        this.bestSelling = new ArrayList<>();
        for(int i=0;i<input.size();i++) {
        	bestSelling.add(new HashMap<>());
        	int newIdx = bestSelling.size()-1;
        	bestSelling.get(newIdx).put("kind", input.get(i).get("kind"));
        	bestSelling.get(newIdx).put("posterURL", input.get(i).get("posterURL"));
        }
        this.exitCode = exitCode;
        this.resultSet = resultSet;
    }
    
    public List<Map<String, String>> getbestSelling() {
    	return bestSelling;
    }
    
    public String getResultSet() {
    	return resultSet;
    }
    public Integer getExitCode() {
        return exitCode;
    }
}