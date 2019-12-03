package com.ebbum;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ResponseClass {

    private List<Map<String, String>> recommend;
    private Integer exitCode;
    private String resultSet;
    
    public ResponseClass() {}
    
    public ResponseClass(Integer exitCode, String resultSet) {
    	recommend = new ArrayList<>();
    	this.exitCode = exitCode;
    	this.resultSet = resultSet;
    }

    public ResponseClass(List<Map<String, String>> input, Integer exitCode, String resultSet) {
        this.recommend = new ArrayList<>();
        for(int i=0;i<input.size();i++) {
        	recommend.add(new HashMap<>());
        	int newIdx = recommend.size()-1;
        	recommend.get(newIdx).put("style", input.get(i).get("style"));
        	recommend.get(newIdx).put("posterURL", input.get(i).get("posterURL"));
        }
        this.exitCode = exitCode;
        this.resultSet = resultSet;
    }
    
    public List<Map<String, String>> getrecommend() {
    	return recommend;
    }
    
    public String getResultSet() {
    	return resultSet;
    }
    public Integer getExitCode() {
        return exitCode;
    }
}