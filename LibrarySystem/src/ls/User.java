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
	
	String getUserInfo() { //���Ͽ� �Է��� ����� �̿��ڵ��� ���� ��� �Լ�
		String info;
		//name�� ���̸� 4�� �����ֱ�
		name = func.fillWithBlank(name, 6);
		
		//��ȭ��ȣ ���� 11�ڷ� �����ֱ�
		phone_num = func.fillWithBlank(phone_num, 11);
		
		//���� ���� �����ֱ�
		info=ID+name+phone_num+rent_rsv_check+segmentNum+Bcount;
		
		return info;
	}
	
	//����� ����&���� ���� ���
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
