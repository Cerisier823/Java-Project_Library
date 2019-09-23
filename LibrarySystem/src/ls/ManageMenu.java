package ls;

import java.util.Scanner;
import java.io.*;

public class ManageMenu {
	Scanner scanner=null;
	Functions func;
	
	ManageMenu(Scanner scanner){
		this.scanner=scanner;
		this.func=new Functions(scanner);
	}
	
	final String fn_user="user.bin";
	final String fn_Ruser="Ruser.bin";
	final String fn_book="book.bin";
	final String fn_Rbook="Rbook.bin";
	final String fn_rentdate="rentdate.bin";
	final String fn_overdue="overdue.bin";
	
	void displayManageMenu(){
		int menu_num;
		

		for(;;) {
			System.out.println("-------*������б�����б� ������ ���� ���α׷�*-------");
			System.out.println("1. �л� �߰�");
			System.out.println("2. å �߰�");
			System.out.println("3. ���� �ݳ� ������ Ȯ��");
			System.out.println("4. ���� ��ü��¥ �����");
			System.out.println("5. ����");
			System.out.println("");
			menu_num=func.menuNumber(5);
			
			if(menu_num==5)
				break;
			
			switch(menu_num) {
			case 1:
				addUserMenu();
				break;
			case 2:
				addBookMenu();
				break;
			case 3:
				displayTomorrowReturn();
				break;
			case 4:
				hidePastOverdue();
				break;
			default:
				System.out.println("����ġ ���� ������ �߻��߽��ϴ�.\n �ý����� �����մϴ�.");
				break;
			}
			
		}
	}
	
	void addUserMenu() {
		int menu_num;
		
		System.out.println("-------*1. �̿��� �߰�*-------");
		
		System.out.println("1. ���л�");
		System.out.println("2. ���п���");
		System.out.println("3. ������");
		System.out.println("4. ����");
		System.out.println("5. ����");
		System.out.println("6. ����");
		
		menu_num=func.menuNumber(6);
		
		switch(menu_num) {
		case 1:
			addUndergradMenu();
			break;
		case 2:
			addPostgradMenu();
			break;
		case 3:
			addGradMenu();
			break;
		case 4:
			addProfessorMenu();
			break;
		case 5:
			addLectureMenu();
			break;
		case 6:
			addStaffMenu();
			break;
		default:
			System.out.println("����ġ ���� ������ �߻��߽��ϴ�.\n �ý����� �����մϴ�.");
			break;
		}
	}
	
	String[] getUserInfo() {
		String ID, name, phone_num;
		String[] userinfo=new String[3];
		
		System.out.print("ID �Է�(ex. 001) : ");
		ID=func.inputCorrectSize(3);
		
		System.out.print("�̸� �Է�(ex. ȫ�浿 / (�ѱ�)3�ڱ���) : ");
		name=func.inputNotOverSize(6);
		
		System.out.print("��ȭ��ȣ �Է�(ex. 01012345678) : ");
		phone_num=func.inputNotOverSize(11);
		
		userinfo[0]=ID;
		userinfo[1]=name;
		userinfo[2]=phone_num;
		
		return userinfo;
	}

	int addUndergradMenu() {
		String grade, off_check, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*1. ���л� �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("�г� �Է�(ex. 1) : ");
		grade=func.inputCorrectSize(1);
		
		System.out.print("���� ���� �Է�(ex. 0 / ���� ���̸� 0, ���� ���̸� 1) : ");
		off_check=func.inputCorrectSize(1);
		
		//���� ���� Ȯ��
		info=info+grade+"(�г�)"+"/"+off_check;
		if(off_check.equals("1"))
			info=info+"(������)";
		else
			info=info+"(������)";
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Undergrad ug =new Undergrad(userinfo[0], userinfo[1], userinfo[2], grade, off_check);
				ug.addUndergrad(fn_user);
				ug.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	int addPostgradMenu() {
		String grade, off_check, course, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*2. ���п��� �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("�г� �Է�(ex. 1) : ");
		grade=func.inputCorrectSize(1);
		
		System.out.print("���� ���� �Է�(ex. 0 / ���� ���̸� 0, ���� ���̸� 1) : ");
		off_check=func.inputCorrectSize(1);
		
		System.out.print("���� �Է�(ex. 0 / ����� 0, �ڻ�� 1) : ");
		course=func.inputCorrectSize(1);
		
		//���� ���� Ȯ��
		info=info+grade+"(�г�)"+"/"+off_check;
		if(off_check.equals("1"))
			info=info+"(������)";
		else
			info=info+"(������)";
		
		info=info+"/"+course;
		if(course.equals("1"))
			info=info+"(�ڻ�)";
		else
			info=info+"(����)";
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Postgrad pg =new Postgrad(userinfo[0], userinfo[1], userinfo[2], grade, off_check, course);
				pg.addPostgrad(fn_user);
				pg.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	int addGradMenu() {
		String info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*3. ������ �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Grad gr =new Grad(userinfo[0], userinfo[1], userinfo[2]);
				gr.addGrad(fn_user);
				gr.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	int addProfessorMenu() {
		String major, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*4. ���� �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("���� �Է�(ex. �İ� / (�ѱ�)8�ڱ���) : ");
		major=func.inputNotOverSize(16);
		
		//���� ���� Ȯ��
		info=info+major;
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Professor pf =new Professor(userinfo[0], userinfo[1], userinfo[2], major);
				pf.addProfessor(fn_user);
				pf.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	int addLectureMenu() {
		String info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*3. ���� �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Lecture le =new Lecture(userinfo[0], userinfo[1], userinfo[2]);
				le.addLecture(fn_user);
				le.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	int addStaffMenu() {
		String department, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*6. ���� �߰�*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("�μ� �Է�(ex. ���� / (�ѱ�)8�ڱ���) : ");
		department=func.inputNotOverSize(16);
		
		//���� ���� Ȯ��
		info=info+department;
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Staff st =new Staff(userinfo[0], userinfo[1], userinfo[2], department);
				st.addStaff(fn_user);
				st.addRuser(fn_Ruser);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}
	
	int addBookMenu() {
		String index, Bname, author, info, cont="n";
		
		System.out.println("-------*2. å �߰�*-------");
		
		
		
		System.out.print("�ε��� �Է�(ex. 001) : ");
		index=func.inputCorrectSize(3);
		
		System.out.print("å �̸� �Է�(ex. �ȷ�Ȩ�� / (�ѱ�)13�ڱ���) : ");
		Bname=func.inputNotOverSize(27);
		
		System.out.print("å ���� �Է�(ex. ȫ�浿 / (�ѱ�)4�ڱ���) : ");
		author=func.inputNotOverSize(8);
		
		//���� ���� Ȯ��
		info=index+"/"+Bname+"/"+author;
		
		System.out.println("�Է��ϰ��� �ϴ� ������ ������ �����ϴ�.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n������ �Է��Ͻðڽ��ϱ�? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Book book=new Book(index,Bname,author);
				book.addBook(fn_book, fn_Rbook);
				
				System.out.println("����Ǿ����ϴ�.");
				System.out.println("���� ȭ������ ���ư��ϴ�\n");
				return 0;
			}
			else
				System.out.println("�߸��� �Է��Դϴ�. y�� n �߿� �ϳ��� �Է����ּ���.");
		}
	}

	//���� �ݳ� ������ ǥ��
	void displayTomorrowReturn() {
		try {
			FileInputStream in = new FileInputStream(fn_rentdate);
			byte[] data=new byte[14];
			String returndate, ID;
			int len;
			boolean check=false;
			
			System.out.print("���� �ݳ��ؾ��ϴ� �̿��� ID�� ");
			for(;;) {
				len=in.read(data);
				if(len==-1)
					break;
				ID=new String(data).substring(0,3);
				returndate=new String(data).substring(6);
				
				if(returndate.contains(" "))
					continue;
				
				if(func.getExceedDay(returndate, func.getDate())==1) {
					System.out.print(ID+"�� ");
					check=true;
				}
			}
			if(check)
				System.out.println("�Դϴ�.\n");
			else
				System.out.println("�����ϴ�.\n");
			
			in.close();
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
	}

	//��ü���������� ��ĭ���� ä���
	void hidePastOverdue(){
		try {
			RandomAccessFile file = new RandomAccessFile(fn_overdue,"rw");
			byte[] data=new byte[8];
			int len;
			String tmp;
			
			for(int i=0;;i++) {
				file.skipBytes(3);
				len=file.read(data);
				if(len==-1)
					break;
				tmp=new String(data);
				
				if(tmp.contains(" "))
					continue;
				
				if(func.getExceedDay(func.getDate(), tmp)>0) {
					file.seek(i*11);
					file.write("           ".getBytes());
				}
			}
			file.close();
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
		
		System.out.println("���������� ���� ��ü ��¥�� ������ϴ�.\n");
	}
}
