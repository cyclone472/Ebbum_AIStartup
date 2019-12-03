package com.ebbum;

public class RequestClass {
    private String id;
    private String pw;
    private String name;
    
    public RequestClass() {}

    public RequestClass(String id, String pw, String name) {
    	this.id = id;
    	this.pw = pw;
    	this.name = name;
    }
    
    public String getId() {
    	return id;
    }

    public String getPw() {
    	return pw;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setid(String s) {
    	this.id = s;
    }
    
    public void setpw(String s) {
    	this.pw = s;
    }
    
    public void setname(String name) {
    	this.name = name;
    }
}

