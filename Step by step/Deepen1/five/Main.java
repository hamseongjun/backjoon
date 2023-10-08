package five;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();

        str = str.toUpperCase();

        // 배열 초기화
        Element[] elements = new Element['Z' - 'A' + 1];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new Element((char) ('A' + i), 0);
        }

        // 문자 하나 하나 순회
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < elements.length; j++) {
                if (elements[j].alphabet == str.charAt(i)) {
                    elements[j].count++;
                    break;
                }
            }
        }

        // 가장 많이 사용된 알파벳 탐색
        int max = 0;
        char result = 'n';
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].count > max) {
                max = elements[i].count;
                result = elements[i].alphabet;
                continue;
            }
            if (elements[i].count == max) {
                result = '?';
            }
        }
        // 출력
        System.out.print(result);
    }
}

class Element {
    char alphabet = 0;
    int count = 0;

    Element(char alphabet, int count) {
        this.alphabet = alphabet;
        this.count = count;
    }
}