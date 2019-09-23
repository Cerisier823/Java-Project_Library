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
			System.out.println("-------*������б�����б� ������ �ý���*-------");
			System.out.println("1. ������");
			System.out.println("2. �̿���");
			System.out.println("3. ����");
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
				System.out.println("����ġ ���� ������ �߻��߽��ϴ�.\n �ý����� �����մϴ�.");
				break;
			}
			
		}
		
		System.out.println("������ �ý����� �����մϴ�.");

		scanner.close();

	}
	
}
