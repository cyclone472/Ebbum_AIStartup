package com.ebbum.login;

public class ResponseClass {
	
    public String name;
    public String message;
    public Integer exitCode;
    public String resultSet;

    public ResponseClass() {}
    
    public ResponseClass(String message) {
    	this.name = "cannot found";
    	this.message = message;
    	this.exitCode = 404;
    	this.resultSet = "Not Found";
    }
    
    public ResponseClass(String name, String message, Integer exitCode, String resultSet) {
    	this.name = name;
    	this.message = message;
    	this.exitCode = exitCode;
    	this.resultSet = resultSet;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name = name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}