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
			
			//user�� ����
			info=getUserInfo();
			
			//������ ���� ä���
			info=func.fillWithBlank(info, record_size);
			
			//�����
			//System.out.println("info ����� : "+info);
			
			//��Ʈ���� ����Ʈ�� ��ȯ
			oneRecord=info.getBytes();
			
			out.write(oneRecord);
			out.close();
			
		} catch (IOException e) {
			System.out.print("Error : ");
			e.getMessage();
		}
	}
	
}
