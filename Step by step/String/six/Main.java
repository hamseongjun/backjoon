package six;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] arr = new int['z' - 'a' + 1];
        // 알파벳 배열 -1로 초기화
        Arrays.fill(arr, -1);

        String s = scanner.next();
        int location;
        for (int i = 0; i < s.length(); i++) {
            location = s.charAt(i) - 'a';
            if (arr[location] == -1) {
                arr[location] = i;
            }
        }
        // 결과 출력
        for (int element : arr) {
            System.out.print(element + " ");
        }
    }
}
