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
		
		System.out.println("-------*�̿��� �α���*-------");
		while(!login) {
			System.out.print("ID �Է�: ");
			login_user.ID=scanner.nextLine();
			login=login_user.userSetting();
			if(!login)
				System.out.println("�������� �ʴ� ������Դϴ�.");
			System.out.println("");
		}
		
		for(;;) {
			System.out.println("-------*������б�����б� ������*-------");
			System.out.println(login_user.name.trim()+"��, ȯ���մϴ�.");
			System.out.println("1. ���� ��� ����");
			System.out.println("2. ���� �˻�");
			System.out.println("3. ����");
			System.out.println("4. ����");
			System.out.println("5. �ݳ�");
			System.out.println("6. ���� �� ���� ��Ȳ");
			System.out.println("7. ����");
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
				System.out.println("����ġ ���� ������ �߻��߽��ϴ�.\n");
				break;
			}
			
		}
	}
}
