package example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
//build�ϴ� ��� : cmdâ���� cd ~/lambda-java-example�� ���� mvn clean install �ϸ� ��
//lambda-java-example/target/serverless-aws~~.jar ������ lambda�� ���ε��ϸ��

public class Hello {
    public ResponseClass handler(RequestClass input) {
    	System.out.println("�ѱ� �غ���");
        return new ResponseClass(input.getStr() + " �ѱ� �׽�Ʈ", input.getInteg());
    }	
}