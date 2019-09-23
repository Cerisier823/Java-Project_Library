package ls;

import java.io.*;

public class User {

	/*
	 ID(3), name(4), phone_num(11), rent_rsv_check(2), segmentNum(1), Bcount(1)
	 */
	
	String  ID, name, phone_num, rent_rsv_check, segmentNum, Bcount;
	
	final int record_size=40;
	
	FileOutputStream out=null;
	Functions func=new Functions();
	
	User(String ID, String name, String phone_num){
		this.ID=ID;
		this.name=name;
		this.phone_num=phone_num;
		this.rent_rsv_check="00";
	}
	
	String getUserInfo() { //파일에 입력할 공통된 이용자들의 정보 얻는 함수
		String info;
		//name의 길이를 4로 맞춰주기
		name = func.fillWithBlank(name, 6);
		
		//전화번호 길이 11자로 맞춰주기
		phone_num = func.fillWithBlank(phone_num, 11);
		
		//넣을 정보 합쳐주기
		info=ID+name+phone_num+rent_rsv_check+segmentNum+Bcount;
		
		return info;
	}
	
	//사용자 대출&예약 정보 기록
	void addRuser(String fn_Ruser) {
		try {
			out=new FileOutputStream(fn_Ruser, true);
			String info;
			byte[] info_b;
			
			info=ID+"000000000000000"+"000000000";
			info_b=info.getBytes();
			out.write(info_b);
			out.close();
		}
		catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
	}
	
}
