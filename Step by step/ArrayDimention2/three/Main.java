package three;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] arr = new char[5][];

        // 2차원 배열에 입력 데이터 저장
        for (int i = 0; i < 5; i++) {
            arr[i] = scanner.nextLine().toCharArray();
        }
        // 가장 길이가 긴 행의 length 구하기
        int maxLength = 0;
        for (int i = 0; i < 5; i++) {
            if (arr[i].length > maxLength) {
                maxLength = arr[i].length;
            }
        }
        // 출력
        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < 5; j++) {
                // 짧아서 없는 글자 넘어가기.
                if (arr[j].length != maxLength && i + 1 > arr[j].length)
                    continue;
                System.out.print(arr[j][i]);
            }
        }

    }
}
