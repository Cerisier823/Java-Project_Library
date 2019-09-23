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
			
			//Bname의 길이를 25로 맞춰주기
			Bname = func.fillWithBlank(Bname, 27);
			
			//author의 길이를 8로 맞춰주기
			author = func.fillWithBlank(author, 8);
			
			//넣을 정보 합쳐주기
			info=index+Bname+author+Brent_rsv_check;
			
			//스트링을 바이트로 변환
			//for(int i=0; i<record_size; i++) {
			//	oneRecord[i]=(byte)info.charAt(i);
			//}
			oneRecord=info.getBytes();
			
			out.write(oneRecord);
			out.close();
			
			//대출&예약중인 책 목록 추가
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
