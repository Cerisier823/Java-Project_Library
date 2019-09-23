package ls;

import java.io.*;

public class Undergrad extends User{
	String grade, off_check;
	
	Undergrad(String ID, String name, String phone_num, String grade, String off_check){
		super(ID, name, phone_num);
		this.grade=grade;
		this.off_check=off_check;
		this.segmentNum="1";
		this.Bcount="3";
	}
	
	void addUndergrad(String fn_user) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out=new FileOutputStream(fn_user, true);
			
			//user�� ����
			info=getUserInfo();
			
			//���� �����ֱ�
			info=info+grade+off_check;
			
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
