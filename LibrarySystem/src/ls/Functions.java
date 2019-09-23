package ls;

import java.util.Calendar;
import java.util.Scanner;

public class Functions {
	Scanner scanner=null;
	
	Functions(Scanner scanner){
		this.scanner=scanner;
	}
	
	Functions(){
		
	}
	
	int menuNumber(int n) { //�Է��� �޴� ��ȣ�� �����ϴ� �Լ�, �޴� ������(n)�� ���� �߸��� �Է��� �ٽ� �ް� ��.
		
		
		int menu_num=0;
		
		for(;;) {
			System.out.println("--------------------------------------");
			System.out.print("�޴� ��ȣ �Է� : ");
			menu_num = scanner.nextInt();
			scanner.nextLine();
			
			if(1<=menu_num && menu_num<=n) {
				System.out.print("\n\n");
				break;
			}
			else
				System.out.printf("�߸��� �Է��Դϴ�. 1~%d ������ ��ȣ�� �Է��ϼ���. \n\n",n);
			
			
		}
		
		return menu_num;
	}
	
	String fillWithBlank(String str, int size) {
		byte[] tmp=str.getBytes();
		String blank="";
		
		if(tmp.length==size)
			;
		else
			for(int i=tmp.length; i<size; i++)
				blank+=" ";
		
		str+=blank;
		return str;
	}
	
	String inputCorrectSize(int size) {
		String str;
		byte[] tmp;
		
		for(;;) {
			str=scanner.nextLine();
			tmp=str.getBytes();
			if(tmp.length==size)
				break;
			
			System.out.print("�߸��� �Է��Դϴ�. ���ø� ���� �ٽ� �Է����ּ��� : ");
		}
		
		return str;
	}
	
	String inputNotOverSize(int size) {
		String str;
		byte[] tmp;
		
		for(;;) {
			str=scanner.nextLine();
			tmp=str.getBytes();
			if(tmp.length<=size)
				break;
			
			System.out.print("ũ�Ⱑ �ʰ��߽��ϴ�. �ٽ� �Է����ּ��� : ");
		}
		
		return str;
	}
	
	//������ ���ڸ� +1or -1���༭ ���� ��ȯ
	//n�� 0�̸� -1, 1�̸� +1
	String plusminusone(int n, char ch) {
		int ch_n=(int)ch;
		
		if(n==0)
			ch_n--;
		if(n==1)
			ch_n++;
		
		ch=(char)ch_n;
		
		String str=Character.toString(ch);
		
		return str;
	}
	
	boolean isLeapYear(int year) {
		boolean leapyear=false;
		
		if((year%4)==0 && (year%100)!=0 | (year%400)==0)
			leapyear=true;
		
		return leapyear;
	}
	
	int getDaysInMonth(int year, int month) {
		boolean leapyear=isLeapYear(year);
		int days_in_month=0;
		switch(month) {
		case 2:
			if(leapyear)
				days_in_month=29;
			else
				days_in_month=28;
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days_in_month=31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days_in_month=30;
			break;
		}
		return days_in_month;
	}

	int[] toIntDate(String date_s) {
		int[] date=new int[3];
		
		date[0]=Integer.parseInt(date_s.substring(0,4));
		date[1]=Integer.parseInt(date_s.substring(4,6));
		date[2]=Integer.parseInt(date_s.substring(6,8));
		
		return date;
	}
	
	String toStringDate(int[] date) {
		String date_s, year_s, month_s, day_s;
		
		year_s=Integer.toString(date[0]);
		if(date[1]<10)
			month_s="0"+Integer.toString(date[1]);
		else
			month_s=Integer.toString(date[1]);
		if(date[2]<10)
			day_s="0"+Integer.toString(date[2]);
		else
			day_s=Integer.toString(date[2]);
		date_s=year_s+month_s+day_s;
		
		return date_s;
	}
	
	//���� ��¥ ��ȯ
	String getDate() {
		Calendar cal = Calendar.getInstance();
		int[] date=new int[3];
		String date_s;
		
		date[0]=cal.get(Calendar.YEAR);
		date[1]=cal.get(Calendar.MONTH)+1;
		date[2]=cal.get(Calendar.DAY_OF_MONTH);
		
		date_s=toStringDate(date);
		
		return date_s;
	}
	
	//��¥ ���ؼ� ��ȯ(n<29)
	String plusDate(int n, String org_date) {
		String date_s;
		int[] date=new int[3];
		int days_in_month=0;
		boolean leapyear=false;
		
		date=toIntDate(org_date);
		
		//��¥ ���ϱ�
		date[2]=date[2]+n;
		
		days_in_month=getDaysInMonth(date[0],date[1]);
		
		if(date[2]>days_in_month) {
			date[2]=date[2]-days_in_month;
			date[1]++;
		}
		
		if(date[1]>12) {
			date[1]=1;
			date[0]++;
		}
		
		date_s=toStringDate(date);
		
		return date_s;
	}
	
	//���� �⵵���� ��ü �� ���ϱ�
	int getTotalDays(int[] date, int year) {
		int totaldays=0;
		
		for(int i=year; i<date[0]; i++) {
			if(isLeapYear(i))
				totaldays+=366;
			else
				totaldays+=365;
		}
		
		for(int i=1; i<date[1]; i++) {
			totaldays+=getDaysInMonth(date[0],i);
		}
		
		totaldays+=date[2];
		
		return totaldays;
	}
	
	//��¥�� �� ��¥���� ���� �� ��������, �������ų� ������ 0
	int getExceedDay(String date_s, String org_date_s) {
		int[] date=new int[3];
		int[] org_date=new int[3];
		int gap=0;
		
		date=toIntDate(date_s);
		org_date=toIntDate(org_date_s);
		
		if(date[0]<org_date[0])
			gap=0;
		else if(date[0]>org_date[0]) {
			gap=getTotalDays(date, org_date[0])-getTotalDays(org_date,org_date[0]);
		}
		else { //���� ���� ���
			if(date[1]<org_date[1])
				gap=0;
			else if(date[1]>org_date[1]) {
				gap=getTotalDays(date, org_date[0])-getTotalDays(org_date,org_date[0]);
			}
			else { //�ޱ��� ���� ���
				if(date[2]<org_date[2])
					gap=0;
				else if(date[2]>org_date[2]) {
					gap=getTotalDays(date, org_date[0])-getTotalDays(org_date,org_date[0]);
				}
				else //�ϱ��� ���� ���
					gap=0;
			}
		}
		
		return gap;
	}
}
