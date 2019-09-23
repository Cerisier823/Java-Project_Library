package ls;

import java.io.*;

public class Postgrad extends Undergrad{
	String course;
	
	Postgrad(String ID, String name, String phone_num, String grade, 
			String off_check, String course){
		super(ID, name, phone_num, grade, off_check);
		this.course=course;
		this.segmentNum="2";
		this.Bcount="3";
	}
	
	void addPostgrad(String fn_user) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out=new FileOutputStream(fn_user, true);
			
			//user의 정보
			info=getUserInfo();
			
			//정보 더해주기
			info=info+grade+off_check+course;
			
			//나머지 공백 채우기
			info=func.fillWithBlank(info, record_size);
			
			//디버깅
			//System.out.println("info 디버깅 : "+info);
			
			//스트링을 바이트로 변환
			oneRecord=info.getBytes();
			
			out.write(oneRecord);
			out.close();
			
		} catch (IOException e) {
			System.out.print("Error : ");
			e.getMessage();
		}
	}
	
}
