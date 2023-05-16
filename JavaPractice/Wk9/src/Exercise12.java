import java.util.Scanner;

class Seat {
    private String name;
    private boolean IsEmpty = true;

    public Seat() {
        name = "___";
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (IsEmpty) {  //빈 좌석만 예약 가능하도록
            this.name = name;
            IsEmpty = false;
            return true;
        } else
            return false;
    }

    public void resetName() {
        this.name = "___";
    }
}

class ConcertReservationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] seatGrade = {"S", "A", "B"};
    private static final Seat[][] seats = new Seat[seatGrade.length][10];

    public static void reserve() {  // 1.예약
        int seatGradeToNum;
        while (true) {  // 1~3 외에 다른 값 입력 방지
            try {
                System.out.print("좌석구분 S(1), A(2), B(3)>>");
                seatGradeToNum = scanner.nextInt();
                if (seatGradeToNum < 1 || seatGradeToNum > seats.length)
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                else break;
            }
            catch(Exception e) {
                System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                scanner.nextLine();
                continue;
            }
        }

        System.out.print(seatGrade[seatGradeToNum - 1] + ">> ");    //선택한 좌석 출력
        for (int i = 0; i < seats[seatGradeToNum - 1].length; i++)
            System.out.print(seats[seatGradeToNum - 1][i].getName() + " ");
        System.out.println();

        String name;
        int reserveNum;
        while (true) {
            try {
                System.out.print("이름>>");
                name = scanner.next();
                System.out.print("번호>>");
                reserveNum = scanner.nextInt();

                if (reserveNum < 1 || reserveNum > seats[seatGradeToNum - 1].length) {  //1~10 외에 다른 값 입력 방지
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                scanner.nextLine();
                continue;
            }

            boolean IsReserved = false;
            for (int i = 0; i < seats[seatGradeToNum - 1].length; i++) {
                if (name.equals(seats[seatGradeToNum - 1][i].getName())) {  // 2자리 이상 중복 예약 방지
                    System.out.println("이미 예약되어있습니다. 다른 이름을 입력해주세요");
                    IsReserved = true;
                    break;
                }
            }
            if (IsReserved) continue;

            if (!seats[seatGradeToNum - 1][reserveNum - 1].setName(name)) { //빈 좌석만 예약 가능
                System.out.println("이 좌석은 이미 예약되어있습니다. 다른 좌석을 선택해주세요.");
                continue;
            } else {    //예약 성공
                System.out.println(seatGrade[seatGradeToNum - 1] + "-" + reserveNum + "좌석을 예약했습니다.");
                System.out.println();
                break;
            }
        }
    }

    public static void showSeats() {    // 2.조회
        for (int i = 0; i < seats.length; i++) {
            System.out.print(seatGrade[i] + ">> ");
            for (int j = 0; j < seats[i].length; j++)
                System.out.print(seats[i][j].getName() + " ");
            System.out.println();
        }
        System.out.println("<<<조회를 완료하였습니다.>>>");
        System.out.println();
    }

    public static void cancel() {   // 3.취소
        int seatGradeToNum;
        String name;
        boolean IsExist = false;

        while (true) {
            try {
                System.out.print("좌석 S:1, A:2, B:3 >>");
                seatGradeToNum = scanner.nextInt();
                if (seatGradeToNum < 1 || seatGradeToNum > seats.length)    //1~3 외에 다른 값 입력 방지
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                else break;
            }
            catch (Exception e){
                System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                scanner.nextLine();
                continue;
            }
        }

        System.out.print(seatGrade[seatGradeToNum - 1] + ">> ");    //선택한 좌석 출력
        for (int i = 0; i < seats[seatGradeToNum - 1].length; i++)
            System.out.print(seats[seatGradeToNum - 1][i].getName() + " ");
        System.out.println();

        while (true) {
            System.out.print("이름>>");
            name = scanner.next();
            for (int i = 0; i < seats[seatGradeToNum - 1].length; i++) {
                if (name.equals(seats[seatGradeToNum - 1][i].getName())) {  //예약자명단에 이름 있는지 확인
                    seats[seatGradeToNum - 1][i].resetName();
                    System.out.println(name + "님의 예약을 취소했습니다.");
                    System.out.println();
                    IsExist = true;
                    break;
                }
            }
            if (!IsExist) {
                System.out.println("일치하는 이름이 없습니다. 다시 입력해주세요.");
                break;
            }
            else break;
        }
    }

    public static void run() {
        int option = 0;

        for (int i = 0; i < seats.length; i++) {    //객체 배열 생성 및 초기화
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat();
            }
        }
        System.out.println("명품콘서트홀 예약 시스템입니다.");
        System.out.println();
        while (true) {
            try {
                System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4 >> ");
                option = scanner.nextInt();
            }
            catch (Exception e) {
                System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
                scanner.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    ConcertReservationSystem.reserve();
                    break;
                case 2:
                    ConcertReservationSystem.showSeats();
                    break;
                case 3:
                    ConcertReservationSystem.cancel();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
            }
        }
    }
}

public class Exercise12 {
    public static void main(String[] args) {
        ConcertReservationSystem.run();
    }
}
