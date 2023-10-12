package Btwo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // 입력된 수
        int b = scanner.nextInt(); // B진법

        // 최대 자릿수 구하기
        int max = 0;
        for (int i = 0; (n / (int) Math.pow(b, i)) > 0; i++) {
            max = i;
        }
        int[] arrInt = new int[max + 1];

        // 각 자리별 10진수 숫자 구하기
        int operand = n;
        for (int i = max; i >= 0; i--) {
            int quotient = operand / (int) Math.pow(b, i);
            arrInt[max - i] = quotient;
            operand %= (int) Math.pow(b, i);
        }
        // 알파벳으로 변환
        char[] arrChar = new char[arrInt.length];
        for (int i = 0; i < arrInt.length; i++) {
            if (arrInt[i] >= 10 && arrInt[i] <= 35) {
                arrChar[i] = (char) ('A' + arrInt[i] - 10);
            } else if (arrInt[i] >= 0) {
                arrChar[i] = Character.forDigit(arrInt[i], 10);
            } else System.out.print("error");
        }

        // 출력
        for (char element : arrChar) {
            System.out.print(element);
        }
    }
}
