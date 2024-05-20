package emaillist;

import emaillist.dao.EmailListDao;
import emaillist.vo.EmailListVo;

import java.util.List;
import java.util.Scanner;

public class EmaillistApp {
    private static Scanner scanner = new Scanner(System.in);
    private static EmailListDao emailListDao = new EmailListDao();
    public static void main(String[] args) {
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
        System.out.print("성:");
        String fisrtName = scanner.nextLine();

        System.out.print("이름:");
        String lastName = scanner.nextLine();

        System.out.print("이메일:");
        String email = scanner.nextLine();

        EmailListVo vo = new EmailListVo();
        vo.setFirstName(fisrtName);
        vo.setLastName(lastName);
        vo.setEmail(email);

        emailListDao.insert(vo);
        doList();
    }

    private static void doDelete() {
        System.out.println("이메일:");
        String email = scanner.nextLine();

        emailListDao.deleteByEmail(email);
    }

    private static void doList() {
        List<EmailListVo> list = emailListDao.findAll();
        for (EmailListVo vo : list) {
            System.out.println(vo.getFirstName()+" "+vo.getLastName()+" : "+vo.getEmail());
        }
    }

}
