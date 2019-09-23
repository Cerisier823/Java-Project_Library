package ls;
import java.util.Scanner;

public class LibraryMain {
	
	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		Functions func = new Functions(scanner);
		UserMenu um=new UserMenu(scanner);
		ManageMenu mm=new ManageMenu(scanner);
		
		int menu_num;

		for(;;) {
			System.out.println("-------*서울과학기술대학교 도서관 시스템*-------");
			System.out.println("1. 관리자");
			System.out.println("2. 이용자");
			System.out.println("3. 종료");
			System.out.println("");
			menu_num=func.menuNumber(3);
			
			if(menu_num==3)
				break;
			
			switch(menu_num) {
			case 1:
				mm.displayManageMenu();
				break;
			case 2:
				um.displayUserMenu();
				break;
			default:
				System.out.println("예기치 못한 오류가 발생했습니다.\n 시스템을 종료합니다.");
				break;
			}
			
		}
		
		System.out.println("도서관 시스템을 종료합니다.");

		scanner.close();

	}
	
}
