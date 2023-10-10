package Aone;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] charArray = scanner.next().toCharArray();
        int n = scanner.nextInt();

        // N의 알파벳을 숫자로 변환
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < intArray.length; i++) {
            if (charArray[i] >= 'A' && charArray[i] <= 'Z')
                intArray[i] = charArray[i] - 'A' + 10;
            else
                intArray[i] = Character.getNumericValue(charArray[i]);
        }

        // 10진법 변환
        int sum = 0;
        for (int i = 0; i < intArray.length; i++) {
            sum += intArray[i] * Math.pow(n, intArray.length - i - 1);
        }
        System.out.print(sum);
    }
}
