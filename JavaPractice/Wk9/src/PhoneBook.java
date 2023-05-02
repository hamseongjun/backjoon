import java.util.Scanner;

class Phone {
    private String name;
    private String number;

    public Phone(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}

public class PhoneBook {
    int num;
    Scanner scanner = new Scanner(System.in);
    Phone[] phones;

    public void run() {
        System.out.print("인원수 >>");
        num = scanner.nextInt();
        phones = new Phone[num];
    }

    public void setInfo() {
        for (int i = 0; i < num; i++) {
            System.out.print("이름과 전화번호(이름과 번호는 빈칸없이 입력)>>");
            String name = scanner.next();
            String number = scanner.next();
            phones[i] = new Phone(name, number);
        }
        System.out.println("저장되었습니다...");
    }

    public void search() {
        boolean IsExist = false;
        while (true) {
            System.out.print("검색할 이름>>");
            String searchedName = scanner.next();

            if (searchedName.equals("그만")) break;

            for (int i = 0; i < num; i++) {
                if (searchedName.equals(phones[i].getName())) {
                    System.out.println(phones[i].getName() + "의 번호는 " + phones[i].getNumber() + " 입니다.");
                    IsExist = true;
                    break;
                }
            }

            if (IsExist == false) System.out.println(searchedName + "이 없습니다.");
        }
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.run();
        phoneBook.setInfo();
        phoneBook.search();
    }
}