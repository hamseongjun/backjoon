import java.util.Scanner;
public class Exercise16 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] str = {"가위", "바위", "보"};

        System.out.println("컴퓨터와 가위 바위 보 게임을 합니다.");
        while (true){
            System.out.print("가위 바위 보>>");
            String user = scanner.next();
            String computer = str[(int)(Math.random()*3)];

            if(user.equals("그만")){
                System.out.println("게임을 종료합니다...");
                break;
            }

            System.out.print("사용자 = "+user+" , 컴퓨터 = "+computer+", ");
            switch (user){
                case("가위"):
                    if (computer.equals("가위"))
                        System.out.println("비겼습니다.");
                    else if(computer.equals("바위"))
                        System.out.println("컴퓨터가 이겼습니다.");
                    else if(computer.equals("보"))
                        System.out.println("사용자가 이겼습니다.");
                    break;
                case("바위"):
                    if (computer.equals("가위"))
                        System.out.println("사용자가 이겼습니다.");
                    else if(computer.equals("바위"))
                        System.out.println("비겼습니다.");
                    else if(computer.equals("보"))
                        System.out.println("컴퓨터가 이겼습니다.");
                    break;
                case("보"):
                    if (computer.equals("가위"))
                        System.out.println("컴퓨터가 이겼습니다.");
                    else if(computer.equals("바위"))
                        System.out.println("사용자가 이겼습니다.");
                    else if(computer.equals("보"))
                        System.out.println("비겼습니다.");
                    break;
            }
        }
    }
}
