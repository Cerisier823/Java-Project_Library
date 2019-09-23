package ls;

import java.io.*;

public class Professor extends User{
	String major;
	
	Professor(String ID, String name, String phone_num, String major){
		super(ID, name, phone_num);
		this.major=major;
		this.segmentNum="4";
		this.Bcount="5";
	}
	
	void addProfessor(String fn_user) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out=new FileOutputStream(fn_user, true);
			
			//user의 정보
			info=getUserInfo();
			
			//정보 더해주기
			major=func.fillWithBlank(major, 16);
			info=info+major;
			
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
