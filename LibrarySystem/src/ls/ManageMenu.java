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
			System.out.println("-------*서울과학기술대학교 도서관 관리 프로그램*-------");
			System.out.println("1. 학생 추가");
			System.out.println("2. 책 추가");
			System.out.println("3. 내일 반납 예정자 확인");
			System.out.println("4. 지난 연체날짜 숨기기");
			System.out.println("5. 종료");
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
				System.out.println("예기치 못한 오류가 발생했습니다.\n 시스템을 종료합니다.");
				break;
			}
			
		}
	}
	
	void addUserMenu() {
		int menu_num;
		
		System.out.println("-------*1. 이용자 추가*-------");
		
		System.out.println("1. 대학생");
		System.out.println("2. 대학원생");
		System.out.println("3. 졸업생");
		System.out.println("4. 교수");
		System.out.println("5. 강사");
		System.out.println("6. 직원");
		
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
			System.out.println("예기치 못한 오류가 발생했습니다.\n 시스템을 종료합니다.");
			break;
		}
	}
	
	String[] getUserInfo() {
		String ID, name, phone_num;
		String[] userinfo=new String[3];
		
		System.out.print("ID 입력(ex. 001) : ");
		ID=func.inputCorrectSize(3);
		
		System.out.print("이름 입력(ex. 홍길동 / (한글)3자까지) : ");
		name=func.inputNotOverSize(6);
		
		System.out.print("전화번호 입력(ex. 01012345678) : ");
		phone_num=func.inputNotOverSize(11);
		
		userinfo[0]=ID;
		userinfo[1]=name;
		userinfo[2]=phone_num;
		
		return userinfo;
	}

	int addUndergradMenu() {
		String grade, off_check, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*1. 대학생 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("학년 입력(ex. 1) : ");
		grade=func.inputCorrectSize(1);
		
		System.out.print("휴학 여부 입력(ex. 0 / 재학 중이면 0, 휴학 중이면 1) : ");
		off_check=func.inputCorrectSize(1);
		
		//넣을 정보 확인
		info=info+grade+"(학년)"+"/"+off_check;
		if(off_check.equals("1"))
			info=info+"(휴학중)";
		else
			info=info+"(재학중)";
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Undergrad ug =new Undergrad(userinfo[0], userinfo[1], userinfo[2], grade, off_check);
				ug.addUndergrad(fn_user);
				ug.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	int addPostgradMenu() {
		String grade, off_check, course, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*2. 대학원생 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("학년 입력(ex. 1) : ");
		grade=func.inputCorrectSize(1);
		
		System.out.print("휴학 여부 입력(ex. 0 / 재학 중이면 0, 휴학 중이면 1) : ");
		off_check=func.inputCorrectSize(1);
		
		System.out.print("과정 입력(ex. 0 / 석사면 0, 박사면 1) : ");
		course=func.inputCorrectSize(1);
		
		//넣을 정보 확인
		info=info+grade+"(학년)"+"/"+off_check;
		if(off_check.equals("1"))
			info=info+"(휴학중)";
		else
			info=info+"(재학중)";
		
		info=info+"/"+course;
		if(course.equals("1"))
			info=info+"(박사)";
		else
			info=info+"(석사)";
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Postgrad pg =new Postgrad(userinfo[0], userinfo[1], userinfo[2], grade, off_check, course);
				pg.addPostgrad(fn_user);
				pg.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	int addGradMenu() {
		String info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*3. 졸업생 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Grad gr =new Grad(userinfo[0], userinfo[1], userinfo[2]);
				gr.addGrad(fn_user);
				gr.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	int addProfessorMenu() {
		String major, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*4. 교수 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("전공 입력(ex. 컴공 / (한글)8자까지) : ");
		major=func.inputNotOverSize(16);
		
		//넣을 정보 확인
		info=info+major;
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Professor pf =new Professor(userinfo[0], userinfo[1], userinfo[2], major);
				pf.addProfessor(fn_user);
				pf.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	int addLectureMenu() {
		String info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*3. 강사 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Lecture le =new Lecture(userinfo[0], userinfo[1], userinfo[2]);
				le.addLecture(fn_user);
				le.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	int addStaffMenu() {
		String department, info="", cont="n";
		String[] userinfo;
		
		System.out.println("-------*6. 직원 추가*-------");
		
		userinfo=getUserInfo();
		
		for(int i=0; i<3; i++) {
			info=info+userinfo[i]+"/";
		}
		
		System.out.print("부서 입력(ex. 행정 / (한글)8자까지) : ");
		department=func.inputNotOverSize(16);
		
		//넣을 정보 확인
		info=info+department;
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Staff st =new Staff(userinfo[0], userinfo[1], userinfo[2], department);
				st.addStaff(fn_user);
				st.addRuser(fn_Ruser);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}
	
	int addBookMenu() {
		String index, Bname, author, info, cont="n";
		
		System.out.println("-------*2. 책 추가*-------");
		
		
		
		System.out.print("인덱스 입력(ex. 001) : ");
		index=func.inputCorrectSize(3);
		
		System.out.print("책 이름 입력(ex. 셜록홈즈 / (한글)13자까지) : ");
		Bname=func.inputNotOverSize(27);
		
		System.out.print("책 저자 입력(ex. 홍길동 / (한글)4자까지) : ");
		author=func.inputNotOverSize(8);
		
		//넣을 정보 확인
		info=index+"/"+Bname+"/"+author;
		
		System.out.println("입력하고자 하는 정보는 다음과 같습니다.");
		System.out.println(info);
		
		for(;;) {
			System.out.print("\n정보를 입력하시겠습니까? (y/n) : ");
			cont=scanner.nextLine();
			
			if(cont.equals("n")) {
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else if(cont.equals("y")) {
				Book book=new Book(index,Bname,author);
				book.addBook(fn_book, fn_Rbook);
				
				System.out.println("저장되었습니다.");
				System.out.println("메인 화면으로 돌아갑니다\n");
				return 0;
			}
			else
				System.out.println("잘못된 입력입니다. y와 n 중에 하나를 입력해주세요.");
		}
	}

	//내일 반납 예정자 표시
	void displayTomorrowReturn() {
		try {
			FileInputStream in = new FileInputStream(fn_rentdate);
			byte[] data=new byte[14];
			String returndate, ID;
			int len;
			boolean check=false;
			
			System.out.print("내일 반납해야하는 이용자 ID는 ");
			for(;;) {
				len=in.read(data);
				if(len==-1)
					break;
				ID=new String(data).substring(0,3);
				returndate=new String(data).substring(6);
				
				if(returndate.contains(" "))
					continue;
				
				if(func.getExceedDay(returndate, func.getDate())==1) {
					System.out.print(ID+"번 ");
					check=true;
				}
			}
			if(check)
				System.out.println("입니다.\n");
			else
				System.out.println("없습니다.\n");
			
			in.close();
		}catch (IOException e) {
			System.out.println("Error : ");
			e.getMessage();
		}
	}

	//연체지난정보는 빈칸으로 채우기
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
		
		System.out.println("정상적으로 지난 연체 날짜를 숨겼습니다.\n");
	}
}
