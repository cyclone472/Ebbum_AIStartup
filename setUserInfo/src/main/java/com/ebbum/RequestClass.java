package com.ebbum;

public class RequestClass {
	private String id;
    private Integer height;
    private String gender;
    private String topSize;
    private Integer pantsSize;
    private Integer age;
    private String bodyType;
    
    public RequestClass() {}
    
    public String getId() {
    	return id;
    }
    
    public int getHeight() {
    	return height;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public String getTopSize() {
    	return topSize;
    }
    
    public int getpantsSize() {
    	return pantsSize;
    }
    
    public String getBodyType() {
    	return bodyType;
    }
    
    public int getAge() {
    	return age;
    }
    
    public void setid(String i) {
    	this.id = i;
    }
    
    public void setheight(int h) {
    	this.height = h;
    }
    
    public void setgender(String s) {
    	this.gender = s;
    } 
    
    public void settopSize(String s) {
    	this.topSize = s;
    }
    
    public void setpantsSize(int p) {
    	this.pantsSize = p;
    }
    
    public void setbodyType(String b) {
    	this.bodyType = b;
    }
    
    public void setage(int a) {
    	this.age = a;
    }
}
