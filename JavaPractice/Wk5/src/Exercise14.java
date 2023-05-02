import java.util.Scanner;
public class Exercise14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] course = {"Java", "C++", "HTML5", "컴퓨터구조", "안드로이드"};
        int[]score = {95, 88, 76, 62, 55};

        while(true){
            System.out.print("과목 이름>>");
            String search = scanner.next();

            if(search.equals("그만"))
                break;

            int i;
            for(i=0; i< course.length; i++){
                if(course[i].equals(search)){
                    int n = score[i];
                    System.out.println(course[i]+"의 점수는 "+n);
                    break;
                }
            }
            if(i == course.length)
                System.out.println("없는 과목입니다.");


        }
    }
}
