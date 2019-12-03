package com.ebbum.login;

public class RequestClass {

    public String id;
    public String pw;
    
    public RequestClass() {}

    public RequestClass(String id, String pw) {
    	this.id = id;
    	this.pw = pw;
    }
    
    public String getId() {
    	return id;
    }

    public String getPw() {
    	return pw;
    }
    
    public void setId(String s) {
    	this.id = s;
    }
    
    public void setPw(String s) {
    	this.pw = s;
    }
}

