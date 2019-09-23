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
			
			//user�� ����
			info=getUserInfo();
			
			//���� �����ֱ�
			major=func.fillWithBlank(major, 16);
			info=info+major;
			
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
