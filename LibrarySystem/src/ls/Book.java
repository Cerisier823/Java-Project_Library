package ls;

import java.io.*;

public class Book {
	String index, Bname, author, Brent_rsv_check;
	final int record_size=40;
	FileOutputStream out = null;
	Functions func = new Functions();
	
	Book(String index, String Bname, String author){
		this.index=index;
		this.Bname=Bname;
		this.author=author;
		this.Brent_rsv_check="00";
	}
	
	void addBook(String fn_book, String fn_Rbook) {
		byte[] oneRecord = new byte[record_size];
		String info;
		
		try {
			out = new FileOutputStream(fn_book, true);
			
			//Bname�� ���̸� 25�� �����ֱ�
			Bname = func.fillWithBlank(Bname, 27);
			
			//author�� ���̸� 8�� �����ֱ�
			author = func.fillWithBlank(author, 8);
			
			//���� ���� �����ֱ�
			info=index+Bname+author+Brent_rsv_check;
			
			//��Ʈ���� ����Ʈ�� ��ȯ
			//for(int i=0; i<record_size; i++) {
			//	oneRecord[i]=(byte)info.charAt(i);
			//}
			oneRecord=info.getBytes();
			
			out.write(oneRecord);
			out.close();
			
			//����&�������� å ��� �߰�
			out=new FileOutputStream(fn_Rbook, true);
			
			info=index+"000000000"+"000";
			oneRecord=info.getBytes();
			out.write(oneRecord);
			out.close();	
		}
		catch (IOException e) {
			System.out.print("Error : ");
			e.getMessage();
		}
	}

}
