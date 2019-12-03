package example;

public class RequestClass {
	private String str;
	private int integ;
    //private List<Integer> list;
    
    RequestClass() {}
    
	public String getStr() {
        return str;
    }
    
	public void setstr(String str) {
        this.str = str;
    }
	
	public int getInteg() {
		return integ;
	}
	
	public void setinteg(int integ) {
		this.integ = integ + 3;
	}
	/*
	public List<Integer> getList() {
		return list;
	}
	
	public void setlist(ArrayList<Integer> inputList) {
		list = new ArrayList<Integer>();
		for(int i=0;i<inputList.size();i++)
			list.add(inputList.get(i) + 697400);
	}
	*/
}
