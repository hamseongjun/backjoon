import java.util.Scanner;
public class Exercise2 {
    public static void main(String[] args){
        System.out.print("1~99 사이의 정수를 입력하시오>>");
        Scanner in = new Scanner(System.in);
        String s = in.next();
        char first = s.charAt(0);
        char second = s.charAt(1);

        if((first == '3' || first == '6' || first == '9') && (second == '3' || second == '6' || second == '9')){
            System.out.print("박수짝짝");
        }
        else if((first == '3' || first == '6'|| first == '9') || (second == '3' || second == '6' || second == '9')) {
            System.out.print("박수짝");
        }
    }
}
