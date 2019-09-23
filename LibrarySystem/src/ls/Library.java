package ls;

import java.io.*;
import java.util.Scanner;

public class Library {
	String fn_user, fn_Ruser, fn_book, fn_Rbook, fn_rentdate, fn_overdue;
	final int Urecord_size=40, RUrecord_size=27, Brecord_size=40, RBrecord_size=15, RDrecord_size=14, ODrecord_size=11;
	RandomAccessFile file=null;
	final int[] Urecord= {3, 6, 11, 2, 1, 1};
	final int[] RUrecord= {3,9,15};
	final int[] Brecord= {3,27,8,2};
	final int[] RBrecord= {3,9,3};
	final int[] RDrecord= {3,3,8};
	final int[] ODrecord= {3,8};
	Scanner scanner=null;
	Functions func = null;
	String ID, name, rent_rsv_check;
	long user_position=0;
	
	Library(String fn_user, String fn_Ruser, String fn_book, String fn_Rbook, String fn_rentdate, String fn_overdue, Scanner scanner){
		this.fn_user=fn_user;
		this.fn_Ruser=fn_Ruser;
		this.fn_book=fn_book;
		this.fn_Rbook=fn_Rbook;
		this.fn_rentdate=fn_rentdate;
		this.fn_overdue=fn_overdue;
		this.scanner=scanner;
		this.func=new Functions(scanner);
	}
	
	boolean userSetting() {
		boolean login=false;
		
		try {
			file=new RandomAccessFile(fn_user,"r");
			int len;
			byte[] data = new byte[Urecord[0]];
			String tmp_ID;
			for(;;user_position++) {
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp_ID=new String(data);
				if(tmp_ID.compareTo(ID)==0) {
					//이름 입력
					data=new byte[Urecord[1]];
					file.read(data);
					name=new String(data);
					
					//대출&예약 체크 입력
					file.skipBytes(Urecord[2]);
					data=new byte[Urecord[3]];
					file.read(data);
					rent_rsv_check=new String(data);
					
					login=true;
					break;
				}
				
				file.skipBytes(Urecord_size-Urecord[0]);
			}
			
			file.close();
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		return login;
	}
	
	boolean displayBookinfo(RandomAccessFile in, int num){
		boolean end=false;
		
		try {
			String info="";
			byte[] data;
			int len=0;
			String tmp;
				
			for(int j=0; j<4;j++) {
				data=new byte[Brecord[j]];
				len=in.read(data);
				
				if(len==-1) {
					end=true;
					return end;
				}
					
				if(j==0)
					System.out.printf("%03d  ", num);
				else if(j==3) {
					info=new String(data);
					break;
				}

				info=new String(data);
				
				//출력할 때 최대한 열에 맞게 출력하도록 하기
				tmp=info.trim();
				for(int k=0; k<(Brecord[j]-tmp.length())/2; k++)
					info=info+" ";
				
				System.out.print(info+" ");
			}
				
			if(info.charAt(0)=='0')
				System.out.print("   X");
			else
				System.out.print("   O");
				
			if(info.charAt(1)=='0')
				System.out.println("      X");
			else
				System.out.println("      "+info.charAt(1)+"명");
			
		} catch (IOException e) {
			System.out.println("Error: ");
			e.getMessage();
		}
		return end;
	}//책 정보(한줄) 출력
	
	//책의 목록 출력
	void listBook() {
		System.out.println("-------*1. 도서 목록 보기*-------");
		System.out.println("No. 인덱스  책제목                                 저자      대출여부   예약정보");
		System.out.println("------------------------------------------------------");
		
		try {
			
			boolean end=false;
			int i=1;
			file = new RandomAccessFile(fn_book,"r");
			while(!end) {
				end=displayBookinfo(file,i);
				i++;
			}
			
			file.close();
			
		} catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		System.out.println("");
	}
	
	//책 검색
	void searchBook(){
		int menu_num;
		
		System.out.println("-------*2. 도서 검색하기*-------");
		System.out.println("1. 인덱스로 검색");
		System.out.println("2. 이름으로 검색");
		System.out.println("3. 작가로 검색");
		System.out.println("4. 종료");
		System.out.println("");
		menu_num=func.menuNumber(4);
		
		switch(menu_num) {
		case 1:
			searchBook_index();
			break;
		case 2:
			searchBook_name();
			break;
		case 3:
			searchBook_author();
			break;
		case 4:
			break;
		}
	}
	
	//인덱스로 책 검색
	void searchBook_index() {
		byte[] data;
		String tmp, index;
		int cnt=0, len=0, menu_num;
		
		System.out.print("책 인덱스 입력: ");
		index=scanner.nextLine();

		System.out.println("");
		System.out.print("검색할 책 인덱스: "+index);
		System.out.println("");
		System.out.println("");
		System.out.println("No. 인덱스  책제목                                 저자      대출여부   예약정보");
		System.out.println("------------------------------------------------------");
		
		
		try {
			file = new RandomAccessFile(fn_book,"r");
			
			for(int i=0;;i++) {
				data=new byte[Brecord[0]];
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.contains(index)) {
					file.seek(i*Brecord_size);
					displayBookinfo(file,cnt+1);
					cnt++;
				}
				else
					file.skipBytes(Brecord_size-Brecord[0]);
			}
			
			if(cnt==0) {
				System.out.println("존재하지 않는 책 인덱스입니다.");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("");
				menu_num=func.menuNumber(2);
				
				if(menu_num==1)
					;
				else{
					searchBook();
				}
			}
			else {
				System.out.println("");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("3. 예약하기");
				System.out.println("");
				menu_num=func.menuNumber(3);
				
				if(menu_num==1)
					;
				else if(menu_num==2)
					searchBook();
				else
					reserveBook();
			}

			file.close();
			
		} catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
	}
	
	//이름으로 책 검색
	void searchBook_name(){
		byte[] data;
		String tmp, book_name;
		int cnt=0, len=0, menu_num;
		
		System.out.print("책 제목 입력: ");
		book_name=scanner.nextLine();

		System.out.println("");
		System.out.print("검색할 책 이름: "+book_name);
		System.out.println("");
		System.out.println("");
		System.out.println("No. 인덱스  책제목                                 저자      대출여부   예약정보");
		System.out.println("------------------------------------------------------");
		
		
		try {
			file = new RandomAccessFile(fn_book,"r");
			
			for(int i=0;;i++) {
				file.skipBytes(Brecord[0]);
				data=new byte[Brecord[1]];
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.contains(book_name)) {
					file.seek(i*Brecord_size);
					displayBookinfo(file,cnt+1);
					cnt++;
				}
				else
					file.skipBytes(Brecord_size-Brecord[0]-Brecord[1]);
			}
			
			if(cnt==0) {
				System.out.println("존재하지 않는 책 제목입니다.");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("");
				menu_num=func.menuNumber(2);
				
				if(menu_num==1)
					;
				else{
					searchBook();
				}
			}
			else {
				System.out.println("");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("3. 예약하기");
				System.out.println("");
				menu_num=func.menuNumber(3);
				
				if(menu_num==1)
					;
				else if(menu_num==2)
					searchBook();
				else
					reserveBook();
			}

			file.close();
			
		} catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		
	}

	//작가로 책 검색
	void searchBook_author() {
		byte[] data;
		String tmp, author;
		int cnt=0, len=0, menu_num;
		
		System.out.print("책 작가 입력: ");
		author=scanner.nextLine();

		System.out.println("");
		System.out.print("검색할 책 작가: "+author);
		System.out.println("");
		System.out.println("");
		System.out.println("No. 인덱스  책제목                                 저자      대출여부   예약정보");
		System.out.println("------------------------------------------------------");
		
		
		try {
			file = new RandomAccessFile(fn_book,"r");
			
			for(int i=0;;i++) {
				file.skipBytes(Brecord[0]+Brecord[1]);
				data=new byte[Brecord[2]];
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.contains(author)) {
					file.seek(i*Brecord_size);
					displayBookinfo(file,cnt+1);
					cnt++;
				}
				else
					file.skipBytes(Brecord[3]);
			}
			
			if(cnt==0) {
				System.out.println("존재하지 않는 책 작가입니다.");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("");
				menu_num=func.menuNumber(2);
				
				if(menu_num==1)
					;
				else{
					searchBook();
				}
			}
			else {
				System.out.println("");
				System.out.println("1. 메인으로 돌아가기");
				System.out.println("2. 다시 검색하기");
				System.out.println("3. 예약하기");
				System.out.println("");
				menu_num=func.menuNumber(3);
				
				if(menu_num==1)
					;
				else if(menu_num==2)
					searchBook();
				else
					reserveBook();
			}

			file.close();
			
		} catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
	}
	
	//책 예약
	int reserveBook(){
		String index, tmp;
		byte[] data;
		int len, Ureserve_num, Breserve_num;
		long book_position=0;
		
		System.out.println("-------*3. 예약하기*-------");
		
		//로그인한 사용자가 예약 할 수 있는지 판단.
		Ureserve_num=(int)rent_rsv_check.charAt(1)-48;
		if(Ureserve_num==3) {
			System.out.println(name.trim()+"님은 더 이상 예약할 수 없습니다.\n");
			return illegalAccess(1);
		}
		
		//예약할 책 정보 입력받음.
		System.out.print("예약할 책 인덱스 입력(앞의 0 포함): ");
		index=scanner.nextLine();
		
		//입력한 인덱스에 해당하는 책 정보 찾음
		try {
			file=new RandomAccessFile(fn_book,"rw");
			data=new byte [Brecord[0]];
			
			for(;;book_position++) {
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.compareTo(index)==0)
					break;
				
				file.skipBytes(Brecord_size-Brecord[0]);
			}
			
			if(len==-1) {
				System.out.println("\n책이 존재하지 않습니다.\n");
				file.close();
				return illegalAccess(1);
			}
			else {
				//fn_book 수정
				file.skipBytes(Brecord[1]+Brecord[2]);
				data=new byte[Brecord[3]];
				file.read(data);
				tmp=new String(data);
				
				//이전 예약 숫자
				Breserve_num=(int)tmp.charAt(1)-48;
				
				//책의 예약이 끝났으면 종료.
				if(Breserve_num==3) {
					System.out.println(index+"번 책은 더 이상 예약할 수 없습니다.\n");
					file.close();
					return illegalAccess(1);
				}
				
				//예약자수 증가
				tmp=tmp.substring(0,1)+func.plusminusone(1, tmp.charAt(1));
				
				file.seek((book_position+1)*Brecord_size-Brecord[3]);
				file.write(tmp.getBytes());
				
				file.close();
				
				//fn_Rbook 수정
				file=new RandomAccessFile(fn_Rbook,"rw");
				
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				data=new byte[9];
				file.read(data);
				tmp=new String(data);
				
				//새롭게 입력할 예약자 목록 생성
				tmp=tmp.substring(0,Breserve_num*3)+ID+tmp.substring(3*(Breserve_num+1),9);
				
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.write(tmp.getBytes());
				
				file.close();
				
				//유저관련 파일 수정
				
				//fn_user 수정
				file=new RandomAccessFile(fn_user,"rw");
				data=new byte[Urecord[0]];
				
				//예약한 책 수 증가
				rent_rsv_check=rent_rsv_check.charAt(0)+func.plusminusone(1, rent_rsv_check.charAt(1));
				
				file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
				file.write(rent_rsv_check.getBytes());
				
				file.close();
				
				//fn_Ruser수정
				file=new RandomAccessFile(fn_Ruser,"rw");
				
				data=new byte[RUrecord[1]];
				file.seek(user_position*RUrecord_size+RUrecord[0]);
				file.read(data);
				tmp=new String(data);
				
				//저장할 예약정보 생성
				tmp=tmp.substring(0,Ureserve_num*3)+index+tmp.substring(3*(Ureserve_num+1),9);				
				
				//입력
				file.seek(user_position*RUrecord_size+RUrecord[0]);
				file.write(tmp.getBytes());
				
				System.out.println(index+"번 책 예약이 완료되었습니다.");
				System.out.println("현재 해당 책의 대기자는 "+(Breserve_num+1)+"명 이고,");
				System.out.println(name.trim()+"님의 예약 권수는 "+rent_rsv_check.charAt(1)+"권 입니다.\n");
			}
			file.close();
		} 
		catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		return 0;
	}

	//예약 & 대출 & 반납이 불가할 때 띄우는 메뉴
	//n이 1이면 예약, 2이면 대출, 3이면 반납
	int illegalAccess(int n) {
		int menu_num;
		
		String str1="다시 예약하기";
		String str2="다시 대출하기";
		String str3="다시 반납하기";
		
		System.out.println("1. 메인으로 돌아가기");
		switch(n) {
		case 1:
			System.out.println("2. "+str1);
			break;
		case 2:
			System.out.println("2. "+str2);
			break;
		case 3:
			System.out.println("2. "+str3);
			break;
		}
		menu_num=func.menuNumber(2);
		
		if(menu_num==1)
			return 0;
		else{
			switch(menu_num+n) {
			case 3:
				reserveBook();
				return 0;
			case 4:
				rentBook();
				return 0;
			case 5:
				returnBook();
				return 0;
			}
		}
		
		return 0;
		
	}
	
	int rentBook(){
		String index, tmp; 
		long book_position=0;
		byte[] data=null;
		int len, Urent_num, Brent_num, Brsv_num;
		boolean rsv_user=false;
		try {
			System.out.println("-------*4. 대출하기*-------");
			
			//로그인한 사용자가 대출 할 수 있는지 판단.
			
			//1. 연체된경우
			file = new RandomAccessFile(fn_overdue, "r");
			data=new byte[ODrecord[0]];
			
			for(;;) {
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.compareTo(ID)==0) {
					data=new byte[ODrecord[1]];
					file.read(data);
					tmp=new String(data);
					
					if(func.getExceedDay(tmp, func.getDate())>0) {
						System.out.println(name.trim()+"님은 연체되었습니다.");
						System.out.println(tmp+"까지 대출이 불가합니다.\n");
						file.close();
						return 0;
					}
					break;
				}
				
				file.skipBytes(ODrecord[1]);
			}
			
			file.close();
			//2. 대출 권수가 가득 찼을 경우
			Urent_num=(int)rent_rsv_check.charAt(0)-48;
			
			file=new RandomAccessFile(fn_user, "r");
			data=new byte[Urecord[5]];
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]+Urecord[3]+Urecord[4]);
			file.read(data);
			tmp=new String(data);
			if(Urent_num>=Integer.parseInt(tmp)) {
				System.out.println(name.trim()+"님은 더 이상 대출이 불가능합니다.\n");
				file.close();
				return illegalAccess(2);
			}
			file.close();
			
			//대출할 책 정보 입력받음.
			System.out.print("대출할 책 인덱스 입력(앞의 0 포함): ");
			index=scanner.nextLine();
			
			//입력한 인덱스에 해당하는 책 정보 찾음
			file=new RandomAccessFile(fn_book,"r");
			data=new byte[Brecord[0]];
			
			for(;;book_position++) {
				len=file.read(data);
				
				if(len==-1) {
					System.out.println("\n책이 존재하지 않습니다.\n");
					file.close();
					return illegalAccess(2);
				}
				
				tmp=new String(data);
				if(tmp.compareTo(index)==0) {
					//책의 대출&예약정보 저장
					file.skipBytes(Brecord[1]+Brecord[2]);
					data=new byte[Brecord[3]];
					file.read(data);
					tmp=new String(data);
					break;
				}				
				file.skipBytes(Brecord_size-Brecord[0]);			
			}
			file.close();
			
			Brent_num=(int)tmp.charAt(0)-48;
			Brsv_num=(int)tmp.charAt(1)-48;
			
			//책이 대출 중
			if(Brent_num==1) {
				System.out.println("대출 중인 책이므로 대출이 불가합니다.\n");
				return illegalAccess(2);
			}
				
			//책이 예약 중
			if(Brsv_num>=1) {
				//사용자가 해당 책의 예약자 1순위인지 확인
				file=new RandomAccessFile(fn_Rbook,"rw");
				data=new byte[RBrecord[1]];
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.read(data);
				tmp=new String(data);

				if(ID.compareTo(tmp.substring(0,3))!=0) {
					System.out.println("현재 "+Brsv_num+"명이 예약 중이므로 대출이 불가능합니다.\n");
					file.close();
					return illegalAccess(2);
				}
				else
					rsv_user=true;
				
				file.close();
			}
			
			//fn_user 수정
			file=new RandomAccessFile(fn_user,"rw");
			if(rsv_user)
				tmp=Integer.toString(Urent_num+1)+func.plusminusone(0, rent_rsv_check.charAt(1));
			else
				tmp=Integer.toString(Urent_num+1)+rent_rsv_check.charAt(1);
			
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
			file.write(tmp.getBytes());
			rent_rsv_check=tmp;
			file.close();
			
			//fn_Ruser 수정
			file=new RandomAccessFile(fn_Ruser,"rw");
			file.seek(user_position*RUrecord_size+RUrecord[0]);
			
			//예약자인 이용자일 경우 예약한 책 정보 수정
			if(rsv_user) {
				data=new byte[RUrecord[1]];
				file.read(data);
				tmp=new String(data);
				
				if(tmp.substring(0,3).compareTo(index)==0)
					tmp=tmp.substring(3,9)+"000";
				else if(tmp.substring(3,6).compareTo(index)==0)
					tmp=tmp.substring(0,3)+tmp.substring(6,9)+"000";
				else if(tmp.substring(6,9).compareTo(index)==0)
					tmp=tmp.substring(0,6)+"000";
				
				file.seek(user_position*RUrecord_size+RUrecord[0]);
				file.write(tmp.getBytes());
			}
			
			//대출정보 추가
			file.seek(user_position*RUrecord_size+RUrecord[0]+RUrecord[1]);
			data=new byte[RUrecord[2]];
			file.read(data);
			tmp=new String(data);
			
			tmp=tmp.substring(0,Urent_num*3)+index+tmp.substring(3*(Urent_num+1),15);
			
			file.seek(user_position*RUrecord_size+RUrecord[0]+RUrecord[1]);
			file.write(tmp.getBytes());
			file.close();
			
			//fn_book 수정
			file=new RandomAccessFile(fn_book,"rw");
			file.seek((book_position+1)*Brecord_size-Brecord[3]);
			if(rsv_user)
				tmp=Integer.toString(Brent_num+1)+Integer.toString(Brsv_num-1);
			else
				tmp=Integer.toString(Brent_num+1)+Integer.toString(Brsv_num);
			file.write(tmp.getBytes());
			file.close();
			
			//fn_Rbook 수정
			file=new RandomAccessFile(fn_Rbook,"rw");
			
			//예약자인 이용자일때 예약자 명단 수정
			if(rsv_user) {
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				data=new byte[RBrecord[1]];
				file.read(data);
				tmp=new String(data);
				tmp=tmp.substring(3,9)+"000";
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.write(tmp.getBytes());
			}
			
			//대출자 명단 수정
			file.seek((book_position+1)*RBrecord_size-RBrecord[2]);
			file.write(ID.getBytes());
			file.close();
			
			System.out.println(index+"번 책의 대출이 완료되었습니다.");
			
			//fn_rentdate에 정보 추가
			FileOutputStream out = new FileOutputStream(fn_rentdate, true);
			
			String returndate=func.plusDate(10, func.getDate());
			tmp=ID+index+returndate;
			out.write(tmp.getBytes());
			
			out.close();
			
			System.out.println("반납일자는 "+returndate+"입니다.\n");
			
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		return 0;
	}

	int returnBook(){
		String index, tmp;
		String[] rent_books=new String[5];
		long book_position=0;
		byte[] data=null;
		int len, Urent_num;
		
		try {
			System.out.println("-------*5. 반납 하기*-------");
			
			//로그인한 사용자가 반납 할 수 있는지 판단.
			Urent_num=(int)rent_rsv_check.charAt(0)-48;
			
			if(Urent_num==0) {
				System.out.println("반납할 책이 존재하지 않습니다.\n");
				return 0;
			} 
			
			//대출 중인 책 정보 저장
			file = new RandomAccessFile(fn_Ruser,"r");
			data=new byte[RUrecord[2]];
			file.seek((user_position+1)*RUrecord_size-RUrecord[2]);
			file.read(data);
			tmp=new String(data);
			file.close();
			
			System.out.print("현재 대출 중인 책은 ");
			for(int i=0;i<Urent_num;i++) {
				rent_books[i]=tmp.substring(3*i,3*(i+1));
				System.out.print(rent_books[i]+"번 ");
			}
			System.out.println("입니다.");
			System.out.print("반납할 책의 인덱스 입력(앞의 0 포함): ");
			index=scanner.nextLine();
			
			//입력한 인덱스가 대출한 책들에 있는지 확인
			for(int i=0; i<Urent_num; i++) {
				if(index.compareTo(rent_books[i])==0)
					break;
				
				if(i==(Urent_num-1)) {
					System.out.println("대출되지 않은 책의 인덱스입니다.");
					return illegalAccess(3);
				}
			}
			
			//fn_user 수정
			file=new RandomAccessFile(fn_user,"rw");
			tmp=func.plusminusone(0, rent_rsv_check.charAt(0))+rent_rsv_check.charAt(1);
			
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
			file.write(tmp.getBytes());
			rent_rsv_check=tmp;
			file.close();
			
			//fn_Ruser 수정
			file=new RandomAccessFile(fn_Ruser,"rw");
			file.seek(user_position*RUrecord_size+RUrecord[0]+RUrecord[1]);
			tmp="";
			if(Urent_num==1)
				file.write("000".getBytes());
			else {
				for(int i=0; i<Urent_num; i++) {
					if(index.compareTo(rent_books[i])!=0) 
						tmp=tmp+rent_books[i];
				}
				file.write(tmp.getBytes());
			}
			file.close();
			
			//fn_book 수정
			file=new RandomAccessFile(fn_book,"rw");
			data=new byte[Brecord[0]];
			for(book_position=0; ;book_position++) {
				file.read(data);
				tmp=new String(data);
				if(tmp.compareTo(index)==0) {
					file.skipBytes(Brecord[1]+Brecord[2]);
					data=new byte[Brecord[3]];
					file.read(data);
					tmp=new String(data);
					tmp=func.plusminusone(0, tmp.charAt(0))+tmp.charAt(1);
					file.seek((book_position+1)*Brecord_size-Brecord[3]);
					file.write(tmp.getBytes());
					break;
				}
				file.skipBytes(Brecord_size-Brecord[0]);
			}
			file.close();
			
			//fn_Rbook 수정, 대출자 초기화
			file=new RandomAccessFile(fn_Rbook,"rw");
			file.seek((book_position+1)*RBrecord_size-RBrecord[2]);
			file.write("000".getBytes());
			file.close();
			
			System.out.println(index+"번 책 반납이 완료되었습니다.");
			
			//연체자 확인 및 fn_rentdate내용 삭제
			file=new RandomAccessFile(fn_rentdate,"rw");
			data=new byte[RDrecord[1]];
			int overduedays=0;
			
			for(int i=0;;i++) {
				file.skipBytes(RDrecord[0]);
				len=file.read(data);
				if(len==-1)
					break;
				
				tmp=new String(data);
				if(tmp.compareTo(index)==0) {
					data=new byte[RDrecord[2]];
					file.read(data);
					tmp=new String(data);
					overduedays=func.getExceedDay(func.getDate(), tmp);
					
					file.seek(i*RDrecord_size);
					file.write("              ".getBytes());
					break;
				}
				
				file.skipBytes(RDrecord[2]);
			}
			file.close();
			
			//연체된 책 일때 fn_overdue 수정
			if(overduedays>0) {
				System.out.println("해당 책은 연체되었습니다.");
				
				file=new RandomAccessFile(fn_overdue,"rw");
				boolean check=false;
				data=new byte[ODrecord[0]];
				String returndate=func.plusDate(overduedays, func.getDate());
				
				for(int i=0;;i++) {
					len=file.read(data);
					if(len==-1) {
						tmp=ID+returndate;
						file.write(tmp.getBytes());
						System.out.println(name.trim()+"님은 "+returndate+"까지 대출이 불가능합니다.\n");
						break;
					}
					
					tmp=new String(data);
					if(tmp.compareTo(ID)==0) {
						data=new byte[ODrecord[1]];
						file.read(data);
						tmp=new String(data);
						
						if(func.getExceedDay(returndate, tmp)>0) {
							file.seek(i*ODrecord_size+ODrecord[0]);
							file.write(returndate.getBytes());
							System.out.println(name.trim()+"님은 "+returndate+"까지 대출이 불가능합니다.\n");
							break;
						}
						else {
							System.out.println(name.trim()+"님은 "+tmp+"까지 대출이 불가능합니다.\n");
							break;
						}
					}
					
					file.skipBytes(ODrecord[1]);
				}
				
				file.close();
			}
			
			System.out.print("현재 대출 중인 책은 ");
			if((int)rent_rsv_check.charAt(0)-48==0)
				System.out.println("없습니다.\n");
			else {
				for(int i=0; i<Urent_num; i++) {
					if(index.compareTo(rent_books[i])!=0) 
						System.out.print(rent_books[i]+"번 ");
				}
				System.out.println("입니다.\n");
			}
			
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		return 0;
	}
	
	//이용자의 예약 및 대출 현황 보기
	void displayRsvRent() {
		String tmp;
		byte[] data;
		int len, Ursv_num, Urent_num;
		
		Urent_num=(int)rent_rsv_check.charAt(0)-48;
		Ursv_num=(int)rent_rsv_check.charAt(1)-48;
		
		try {
			file = new RandomAccessFile(fn_Ruser,"rw");
			data=new byte[RUrecord[0]];
			for(;;) {
				len=file.read(data);
				if(len==-1)
					break;
				tmp=new String(data);
				if(tmp.compareTo(ID)==0){
					if(Ursv_num==0) {
						System.out.println("현재 예약한 책은 없고,");
						file.skipBytes(RUrecord[1]);
					} else {
						data=new byte[RUrecord[1]];
						file.read(data);
						tmp=new String(data);
						System.out.print("현재 예약한 책은' ");
						for(int i=0; i<Ursv_num; i++) {
							System.out.print(tmp.substring(3*i,3*(i+1))+"번 ");
						}
						System.out.println("'으로 총 "+Ursv_num+"권이고,");
					}
					
					if(Urent_num==0) {
						System.out.println("현재 대출한 책은 없습니다.\n");
					} else {
						data=new byte[RUrecord[2]];
						file.read(data);
						tmp=new String(data);
						System.out.print("현재 대출한 책은' ");
						for(int i=0; i<Urent_num; i++) {
							System.out.print(tmp.substring(3*i,3*(i+1))+"번 ");
						}
						System.out.println("'으로 총 "+Urent_num+"권입니다.\n");
					}
					
					break;
				}
				
				file.skipBytes(RUrecord_size-RUrecord[0]);
			}
			file.close();
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}

	}
}
