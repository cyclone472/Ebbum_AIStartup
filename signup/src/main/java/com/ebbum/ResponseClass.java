package com.ebbum;

public class ResponseClass {
	
    public Integer exitCode;
    public String resultSet;

    public ResponseClass() {}
    
    public ResponseClass(Integer exitCode, String resultSet) {
    	this.exitCode = exitCode;
    	this.resultSet = resultSet;
    }
    
    public String getResultSet() {
    	return resultSet;
    }
    
    public void setResultSet(String resultSet) {
    	this.resultSet = resultSet;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }    
}