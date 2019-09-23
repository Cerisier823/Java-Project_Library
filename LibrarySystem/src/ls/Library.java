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
					//�̸� �Է�
					data=new byte[Urecord[1]];
					file.read(data);
					name=new String(data);
					
					//����&���� üũ �Է�
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
				
				//����� �� �ִ��� ���� �°� ����ϵ��� �ϱ�
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
				System.out.println("      "+info.charAt(1)+"��");
			
		} catch (IOException e) {
			System.out.println("Error: ");
			e.getMessage();
		}
		return end;
	}//å ����(����) ���
	
	//å�� ��� ���
	void listBook() {
		System.out.println("-------*1. ���� ��� ����*-------");
		System.out.println("No. �ε���  å����                                 ����      ���⿩��   ��������");
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
	
	//å �˻�
	void searchBook(){
		int menu_num;
		
		System.out.println("-------*2. ���� �˻��ϱ�*-------");
		System.out.println("1. �ε����� �˻�");
		System.out.println("2. �̸����� �˻�");
		System.out.println("3. �۰��� �˻�");
		System.out.println("4. ����");
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
	
	//�ε����� å �˻�
	void searchBook_index() {
		byte[] data;
		String tmp, index;
		int cnt=0, len=0, menu_num;
		
		System.out.print("å �ε��� �Է�: ");
		index=scanner.nextLine();

		System.out.println("");
		System.out.print("�˻��� å �ε���: "+index);
		System.out.println("");
		System.out.println("");
		System.out.println("No. �ε���  å����                                 ����      ���⿩��   ��������");
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
				System.out.println("�������� �ʴ� å �ε����Դϴ�.");
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
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
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
				System.out.println("3. �����ϱ�");
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
	
	//�̸����� å �˻�
	void searchBook_name(){
		byte[] data;
		String tmp, book_name;
		int cnt=0, len=0, menu_num;
		
		System.out.print("å ���� �Է�: ");
		book_name=scanner.nextLine();

		System.out.println("");
		System.out.print("�˻��� å �̸�: "+book_name);
		System.out.println("");
		System.out.println("");
		System.out.println("No. �ε���  å����                                 ����      ���⿩��   ��������");
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
				System.out.println("�������� �ʴ� å �����Դϴ�.");
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
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
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
				System.out.println("3. �����ϱ�");
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

	//�۰��� å �˻�
	void searchBook_author() {
		byte[] data;
		String tmp, author;
		int cnt=0, len=0, menu_num;
		
		System.out.print("å �۰� �Է�: ");
		author=scanner.nextLine();

		System.out.println("");
		System.out.print("�˻��� å �۰�: "+author);
		System.out.println("");
		System.out.println("");
		System.out.println("No. �ε���  å����                                 ����      ���⿩��   ��������");
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
				System.out.println("�������� �ʴ� å �۰��Դϴ�.");
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
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
				System.out.println("1. �������� ���ư���");
				System.out.println("2. �ٽ� �˻��ϱ�");
				System.out.println("3. �����ϱ�");
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
	
	//å ����
	int reserveBook(){
		String index, tmp;
		byte[] data;
		int len, Ureserve_num, Breserve_num;
		long book_position=0;
		
		System.out.println("-------*3. �����ϱ�*-------");
		
		//�α����� ����ڰ� ���� �� �� �ִ��� �Ǵ�.
		Ureserve_num=(int)rent_rsv_check.charAt(1)-48;
		if(Ureserve_num==3) {
			System.out.println(name.trim()+"���� �� �̻� ������ �� �����ϴ�.\n");
			return illegalAccess(1);
		}
		
		//������ å ���� �Է¹���.
		System.out.print("������ å �ε��� �Է�(���� 0 ����): ");
		index=scanner.nextLine();
		
		//�Է��� �ε����� �ش��ϴ� å ���� ã��
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
				System.out.println("\nå�� �������� �ʽ��ϴ�.\n");
				file.close();
				return illegalAccess(1);
			}
			else {
				//fn_book ����
				file.skipBytes(Brecord[1]+Brecord[2]);
				data=new byte[Brecord[3]];
				file.read(data);
				tmp=new String(data);
				
				//���� ���� ����
				Breserve_num=(int)tmp.charAt(1)-48;
				
				//å�� ������ �������� ����.
				if(Breserve_num==3) {
					System.out.println(index+"�� å�� �� �̻� ������ �� �����ϴ�.\n");
					file.close();
					return illegalAccess(1);
				}
				
				//�����ڼ� ����
				tmp=tmp.substring(0,1)+func.plusminusone(1, tmp.charAt(1));
				
				file.seek((book_position+1)*Brecord_size-Brecord[3]);
				file.write(tmp.getBytes());
				
				file.close();
				
				//fn_Rbook ����
				file=new RandomAccessFile(fn_Rbook,"rw");
				
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				data=new byte[9];
				file.read(data);
				tmp=new String(data);
				
				//���Ӱ� �Է��� ������ ��� ����
				tmp=tmp.substring(0,Breserve_num*3)+ID+tmp.substring(3*(Breserve_num+1),9);
				
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.write(tmp.getBytes());
				
				file.close();
				
				//�������� ���� ����
				
				//fn_user ����
				file=new RandomAccessFile(fn_user,"rw");
				data=new byte[Urecord[0]];
				
				//������ å �� ����
				rent_rsv_check=rent_rsv_check.charAt(0)+func.plusminusone(1, rent_rsv_check.charAt(1));
				
				file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
				file.write(rent_rsv_check.getBytes());
				
				file.close();
				
				//fn_Ruser����
				file=new RandomAccessFile(fn_Ruser,"rw");
				
				data=new byte[RUrecord[1]];
				file.seek(user_position*RUrecord_size+RUrecord[0]);
				file.read(data);
				tmp=new String(data);
				
				//������ �������� ����
				tmp=tmp.substring(0,Ureserve_num*3)+index+tmp.substring(3*(Ureserve_num+1),9);				
				
				//�Է�
				file.seek(user_position*RUrecord_size+RUrecord[0]);
				file.write(tmp.getBytes());
				
				System.out.println(index+"�� å ������ �Ϸ�Ǿ����ϴ�.");
				System.out.println("���� �ش� å�� ����ڴ� "+(Breserve_num+1)+"�� �̰�,");
				System.out.println(name.trim()+"���� ���� �Ǽ��� "+rent_rsv_check.charAt(1)+"�� �Դϴ�.\n");
			}
			file.close();
		} 
		catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		return 0;
	}

	//���� & ���� & �ݳ��� �Ұ��� �� ���� �޴�
	//n�� 1�̸� ����, 2�̸� ����, 3�̸� �ݳ�
	int illegalAccess(int n) {
		int menu_num;
		
		String str1="�ٽ� �����ϱ�";
		String str2="�ٽ� �����ϱ�";
		String str3="�ٽ� �ݳ��ϱ�";
		
		System.out.println("1. �������� ���ư���");
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
			System.out.println("-------*4. �����ϱ�*-------");
			
			//�α����� ����ڰ� ���� �� �� �ִ��� �Ǵ�.
			
			//1. ��ü�Ȱ��
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
						System.out.println(name.trim()+"���� ��ü�Ǿ����ϴ�.");
						System.out.println(tmp+"���� ������ �Ұ��մϴ�.\n");
						file.close();
						return 0;
					}
					break;
				}
				
				file.skipBytes(ODrecord[1]);
			}
			
			file.close();
			//2. ���� �Ǽ��� ���� á�� ���
			Urent_num=(int)rent_rsv_check.charAt(0)-48;
			
			file=new RandomAccessFile(fn_user, "r");
			data=new byte[Urecord[5]];
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]+Urecord[3]+Urecord[4]);
			file.read(data);
			tmp=new String(data);
			if(Urent_num>=Integer.parseInt(tmp)) {
				System.out.println(name.trim()+"���� �� �̻� ������ �Ұ����մϴ�.\n");
				file.close();
				return illegalAccess(2);
			}
			file.close();
			
			//������ å ���� �Է¹���.
			System.out.print("������ å �ε��� �Է�(���� 0 ����): ");
			index=scanner.nextLine();
			
			//�Է��� �ε����� �ش��ϴ� å ���� ã��
			file=new RandomAccessFile(fn_book,"r");
			data=new byte[Brecord[0]];
			
			for(;;book_position++) {
				len=file.read(data);
				
				if(len==-1) {
					System.out.println("\nå�� �������� �ʽ��ϴ�.\n");
					file.close();
					return illegalAccess(2);
				}
				
				tmp=new String(data);
				if(tmp.compareTo(index)==0) {
					//å�� ����&�������� ����
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
			
			//å�� ���� ��
			if(Brent_num==1) {
				System.out.println("���� ���� å�̹Ƿ� ������ �Ұ��մϴ�.\n");
				return illegalAccess(2);
			}
				
			//å�� ���� ��
			if(Brsv_num>=1) {
				//����ڰ� �ش� å�� ������ 1�������� Ȯ��
				file=new RandomAccessFile(fn_Rbook,"rw");
				data=new byte[RBrecord[1]];
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.read(data);
				tmp=new String(data);

				if(ID.compareTo(tmp.substring(0,3))!=0) {
					System.out.println("���� "+Brsv_num+"���� ���� ���̹Ƿ� ������ �Ұ����մϴ�.\n");
					file.close();
					return illegalAccess(2);
				}
				else
					rsv_user=true;
				
				file.close();
			}
			
			//fn_user ����
			file=new RandomAccessFile(fn_user,"rw");
			if(rsv_user)
				tmp=Integer.toString(Urent_num+1)+func.plusminusone(0, rent_rsv_check.charAt(1));
			else
				tmp=Integer.toString(Urent_num+1)+rent_rsv_check.charAt(1);
			
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
			file.write(tmp.getBytes());
			rent_rsv_check=tmp;
			file.close();
			
			//fn_Ruser ����
			file=new RandomAccessFile(fn_Ruser,"rw");
			file.seek(user_position*RUrecord_size+RUrecord[0]);
			
			//�������� �̿����� ��� ������ å ���� ����
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
			
			//�������� �߰�
			file.seek(user_position*RUrecord_size+RUrecord[0]+RUrecord[1]);
			data=new byte[RUrecord[2]];
			file.read(data);
			tmp=new String(data);
			
			tmp=tmp.substring(0,Urent_num*3)+index+tmp.substring(3*(Urent_num+1),15);
			
			file.seek(user_position*RUrecord_size+RUrecord[0]+RUrecord[1]);
			file.write(tmp.getBytes());
			file.close();
			
			//fn_book ����
			file=new RandomAccessFile(fn_book,"rw");
			file.seek((book_position+1)*Brecord_size-Brecord[3]);
			if(rsv_user)
				tmp=Integer.toString(Brent_num+1)+Integer.toString(Brsv_num-1);
			else
				tmp=Integer.toString(Brent_num+1)+Integer.toString(Brsv_num);
			file.write(tmp.getBytes());
			file.close();
			
			//fn_Rbook ����
			file=new RandomAccessFile(fn_Rbook,"rw");
			
			//�������� �̿����϶� ������ ��� ����
			if(rsv_user) {
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				data=new byte[RBrecord[1]];
				file.read(data);
				tmp=new String(data);
				tmp=tmp.substring(3,9)+"000";
				file.seek(book_position*RBrecord_size+RBrecord[0]);
				file.write(tmp.getBytes());
			}
			
			//������ ��� ����
			file.seek((book_position+1)*RBrecord_size-RBrecord[2]);
			file.write(ID.getBytes());
			file.close();
			
			System.out.println(index+"�� å�� ������ �Ϸ�Ǿ����ϴ�.");
			
			//fn_rentdate�� ���� �߰�
			FileOutputStream out = new FileOutputStream(fn_rentdate, true);
			
			String returndate=func.plusDate(10, func.getDate());
			tmp=ID+index+returndate;
			out.write(tmp.getBytes());
			
			out.close();
			
			System.out.println("�ݳ����ڴ� "+returndate+"�Դϴ�.\n");
			
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
			System.out.println("-------*5. �ݳ� �ϱ�*-------");
			
			//�α����� ����ڰ� �ݳ� �� �� �ִ��� �Ǵ�.
			Urent_num=(int)rent_rsv_check.charAt(0)-48;
			
			if(Urent_num==0) {
				System.out.println("�ݳ��� å�� �������� �ʽ��ϴ�.\n");
				return 0;
			} 
			
			//���� ���� å ���� ����
			file = new RandomAccessFile(fn_Ruser,"r");
			data=new byte[RUrecord[2]];
			file.seek((user_position+1)*RUrecord_size-RUrecord[2]);
			file.read(data);
			tmp=new String(data);
			file.close();
			
			System.out.print("���� ���� ���� å�� ");
			for(int i=0;i<Urent_num;i++) {
				rent_books[i]=tmp.substring(3*i,3*(i+1));
				System.out.print(rent_books[i]+"�� ");
			}
			System.out.println("�Դϴ�.");
			System.out.print("�ݳ��� å�� �ε��� �Է�(���� 0 ����): ");
			index=scanner.nextLine();
			
			//�Է��� �ε����� ������ å�鿡 �ִ��� Ȯ��
			for(int i=0; i<Urent_num; i++) {
				if(index.compareTo(rent_books[i])==0)
					break;
				
				if(i==(Urent_num-1)) {
					System.out.println("������� ���� å�� �ε����Դϴ�.");
					return illegalAccess(3);
				}
			}
			
			//fn_user ����
			file=new RandomAccessFile(fn_user,"rw");
			tmp=func.plusminusone(0, rent_rsv_check.charAt(0))+rent_rsv_check.charAt(1);
			
			file.seek(user_position*Urecord_size+Urecord[0]+Urecord[1]+Urecord[2]);
			file.write(tmp.getBytes());
			rent_rsv_check=tmp;
			file.close();
			
			//fn_Ruser ����
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
			
			//fn_book ����
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
			
			//fn_Rbook ����, ������ �ʱ�ȭ
			file=new RandomAccessFile(fn_Rbook,"rw");
			file.seek((book_position+1)*RBrecord_size-RBrecord[2]);
			file.write("000".getBytes());
			file.close();
			
			System.out.println(index+"�� å �ݳ��� �Ϸ�Ǿ����ϴ�.");
			
			//��ü�� Ȯ�� �� fn_rentdate���� ����
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
			
			//��ü�� å �϶� fn_overdue ����
			if(overduedays>0) {
				System.out.println("�ش� å�� ��ü�Ǿ����ϴ�.");
				
				file=new RandomAccessFile(fn_overdue,"rw");
				boolean check=false;
				data=new byte[ODrecord[0]];
				String returndate=func.plusDate(overduedays, func.getDate());
				
				for(int i=0;;i++) {
					len=file.read(data);
					if(len==-1) {
						tmp=ID+returndate;
						file.write(tmp.getBytes());
						System.out.println(name.trim()+"���� "+returndate+"���� ������ �Ұ����մϴ�.\n");
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
							System.out.println(name.trim()+"���� "+returndate+"���� ������ �Ұ����մϴ�.\n");
							break;
						}
						else {
							System.out.println(name.trim()+"���� "+tmp+"���� ������ �Ұ����մϴ�.\n");
							break;
						}
					}
					
					file.skipBytes(ODrecord[1]);
				}
				
				file.close();
			}
			
			System.out.print("���� ���� ���� å�� ");
			if((int)rent_rsv_check.charAt(0)-48==0)
				System.out.println("�����ϴ�.\n");
			else {
				for(int i=0; i<Urent_num; i++) {
					if(index.compareTo(rent_books[i])!=0) 
						System.out.print(rent_books[i]+"�� ");
				}
				System.out.println("�Դϴ�.\n");
			}
			
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		return 0;
	}
	
	//�̿����� ���� �� ���� ��Ȳ ����
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
						System.out.println("���� ������ å�� ����,");
						file.skipBytes(RUrecord[1]);
					} else {
						data=new byte[RUrecord[1]];
						file.read(data);
						tmp=new String(data);
						System.out.print("���� ������ å��' ");
						for(int i=0; i<Ursv_num; i++) {
							System.out.print(tmp.substring(3*i,3*(i+1))+"�� ");
						}
						System.out.println("'���� �� "+Ursv_num+"���̰�,");
					}
					
					if(Urent_num==0) {
						System.out.println("���� ������ å�� �����ϴ�.\n");
					} else {
						data=new byte[RUrecord[2]];
						file.read(data);
						tmp=new String(data);
						System.out.print("���� ������ å��' ");
						for(int i=0; i<Urent_num; i++) {
							System.out.print(tmp.substring(3*i,3*(i+1))+"�� ");
						}
						System.out.println("'���� �� "+Urent_num+"���Դϴ�.\n");
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
