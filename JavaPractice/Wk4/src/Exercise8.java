import java.util.Scanner;
public class Exercise8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        int[] arr;

        while(true){    //지정된 범위(100보다 작은 양의 정수)가 아닐 경우 무한 반복.
            System.out.print("정수 몇개?");
            n = scanner.nextInt();

            if(n>=0 && n<100)
                break;
            System.out.print("지정된 범위를 초과했습니다. 다시 입력해주세요\n");
        }
        arr = new int[n];

        for(int i=0; i<arr.length; i++){
            int rand = (int)(Math.random()*100 + 1);
            int exits = 0;
            for(int j =0; j<=i; j++){
                if(rand == arr[j]){
                    exits = 1;
                    break;
                }
            }
            if(exits == 1) {
                i--;
                continue;
            }
            arr[i] = rand;
            if(i != 0 && i%10 == 0){
                System.out.print("\n");
            }
            System.out.printf("%3d ", arr[i]);
        }

    }
}
