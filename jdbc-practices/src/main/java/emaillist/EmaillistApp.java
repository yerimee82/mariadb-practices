package emaillist;

import java.util.Scanner;

public class EmaillistApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("(l)ist d)elete i)nsert (q)uit > ");
            String command = scanner.nextLine();
            if("l".equals(command)) {
                doList();
            } else if ("d".equals(command)) {
                doDelete();
            } else if ("i".equals(command)) {
                doInsert();
            } else if ("q".equals(command)){
                break;
            } else {
                System.out.println("다른 것을 입력하세요.");
            }
        }
        
        scanner.close();
    }

    private static void doInsert() {
        System.out.println("doInsert");
    }

    private static void doDelete() {
        System.out.println("doDelete");
    }

    private static void doList() {
        System.out.println("doList");
    }

}
