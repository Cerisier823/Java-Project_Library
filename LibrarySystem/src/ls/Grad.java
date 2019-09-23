package ls;

import java.io.*;

public class Grad extends User{
	
	Grad(String ID, String name, String phone_num){
		super(ID, name, phone_num);
		this.segmentNum="3";
		this.Bcount="2";
	}
	
	void addGrad(String fn_user) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out=new FileOutputStream(fn_user, true);
			
			//user의 정보
			info=getUserInfo();
			
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
