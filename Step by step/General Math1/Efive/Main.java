package Efive;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        int size = 1;
        int tmp = n - 1;
        for (int i = 1; tmp > 0; i++) {
            if (tmp <= 6 * i)
                size += i;
            tmp -= 6 * i;
        }

        System.out.print(size);
        // 1
        // 2~7 = 6
        // 8~19 = 12
        // 20~37 = 18
    }
}
