package example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
//build하는 방법 : cmd창에서 cd ~/lambda-java-example로 들어가서 mvn clean install 하면 됨
//lambda-java-example/target/serverless-aws~~.jar 파일을 lambda로 업로드하면됨

public class Hello {
    public ResponseClass handler(RequestClass input) {
    	System.out.println("한글 해보기");
        return new ResponseClass(input.getStr() + " 한글 테스트", input.getInteg());
    }	
}