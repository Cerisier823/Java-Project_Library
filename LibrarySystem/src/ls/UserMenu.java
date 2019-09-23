package ls;

import java.util.Scanner;

public class UserMenu {
	Scanner scanner=null;
	final String fn_user="user.bin", fn_Ruser="Ruser.bin",
			fn_book="book.bin", fn_Rbook="Rbook.bin", 
			fn_rentdate="rentdate.bin", fn_overdue="overdue.bin";
	
	UserMenu(Scanner scanner){
		this.scanner=scanner;
	}
	
	void displayUserMenu() {	
		int menu_num;
		Library login_user = new Library(fn_user, fn_Ruser, fn_book, fn_Rbook, fn_rentdate, fn_overdue, scanner);
		boolean login=false;
		
		Functions func = new Functions(scanner);
		
		System.out.println("-------*이용자 로그인*-------");
		while(!login) {
			System.out.print("ID 입력: ");
			login_user.ID=scanner.nextLine();
			login=login_user.userSetting();
			if(!login)
				System.out.println("존재하지 않는 사용자입니다.");
			System.out.println("");
		}
		
		for(;;) {
			System.out.println("-------*서울과학기술대학교 도서관*-------");
			System.out.println(login_user.name.trim()+"님, 환영합니다.");
			System.out.println("1. 도서 목록 보기");
			System.out.println("2. 도서 검색");
			System.out.println("3. 예약");
			System.out.println("4. 대출");
			System.out.println("5. 반납");
			System.out.println("6. 예약 및 대출 현황");
			System.out.println("7. 종료");
			System.out.println("");
			menu_num=func.menuNumber(7);
			
			if(menu_num==7)
				break;
			
			switch(menu_num) {
			case 1:
				login_user.listBook();
				break;
			case 2:
				login_user.searchBook();
				break;
			case 3:
				login_user.reserveBook();
				break;
			case 4:
				login_user.rentBook();
				break;
			case 5:
				login_user.returnBook();
				break;
			case 6:
				login_user.displayRsvRent();
				break;
			default:
				System.out.println("예기치 못한 오류가 발생했습니다.\n");
				break;
			}
			
		}
	}
}
