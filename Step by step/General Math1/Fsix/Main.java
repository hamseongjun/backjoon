package Fsix;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int x = scanner.nextInt();

        int tmp = x;
        int row = 0, column = 0;
        for (int i = 1; tmp > 0; i++) {
            if (tmp <= i) {         // 피라미드에서 내 레벨을 찾았다는 의미.
                if (i%2 == 0) {     // 지그재그 (끝 인덱스가 짝수일 때)
                    row = tmp;
                    column = i - tmp + 1;
                }
                else {              // 지그재그 (끝 인덱스가 홀수일 때)
                    row = i - tmp + 1;
                    column = tmp;
                }
            }
            tmp -= i;       // 1, 2, 3, 4, ... 순차적으로 커지는 피라미드 앞 부분 없애기.
        }

        System.out.print(Integer.toString(row) + "/" + Integer.toString(column));
    }
}
