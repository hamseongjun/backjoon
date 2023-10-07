package nine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] arr = new int[n];
        // 배열 초기화
        for (int i = 0; i < n; i++){
            arr[i] = i+1;
        }

        int i, j;
        // 섞어
        for (int r = 0; r < m; r++) {
            i = scanner.nextInt();
            j = scanner.nextInt();
            int[] subArr = new int[j-i+1];
            for (int rr = 0; rr <= j-i; rr++) { // 역순 임시 배열 생성
                subArr[rr] = arr[j-rr-1];
            }
            for (int rr = 0; rr <= j-i; rr++) { // 역순 배열따라 원배열 체인지
                arr[i+rr-1] = subArr[rr];
            }
        }
        // 출력
        for (int num : arr)
            System.out.print(num + " ");
    }
}
