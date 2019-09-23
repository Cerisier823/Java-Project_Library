package ls;

import java.io.*;

public class Staff extends User{
	String department;
	
	Staff(String ID, String name, String phone_num, String department){
		super(ID, name, phone_num);
		this.department=department;
		this.segmentNum="6";
		this.Bcount="3";
	}
	
	void addStaff(String fn_user) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out=new FileOutputStream(fn_user, true);
			
			//user�� ����
			info=getUserInfo();
			
			//���� �����ֱ�
			department=func.fillWithBlank(department, 16);
			info=info+department;
			
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
