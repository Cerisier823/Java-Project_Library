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
			
			//user�� ����
			info=getUserInfo();
			
			//���� �����ֱ�
			info=info+grade+off_check+course;
			
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
