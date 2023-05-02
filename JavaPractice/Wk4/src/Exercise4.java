import java.util.Scanner;
public class Exercise4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("소문자 알파벳 하나를 입력하시오>>");

        String s = scanner.next();
        char c = s.charAt(0);

        for(int i=0; i<c-'a'+1; i++){
            for(char j='a'; j<c-i+1; j++){
                System.out.print((j));
            }
            System.out.print("\n");
        }
    }
}
