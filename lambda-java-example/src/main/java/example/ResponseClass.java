package example;

import java.util.List;
import java.util.ArrayList;

public class ResponseClass {
    private final String resultStr;
    private final int resultInt;
    private final List<Integer> resultList;
    
    ResponseClass(String s, int in) {
        this.resultStr = s;
        this.resultInt = in;
        resultList = new ArrayList<Integer>();
        for(int i=0;i<in;i++)
        	resultList.add(i + 740000);
    }

	public String getResultStr() {
	    return resultStr;
	}
	
	public int getResultInt() {
		return resultInt;
	}
	
	public List<Integer> getResultList() {
		return resultList;
	}
}