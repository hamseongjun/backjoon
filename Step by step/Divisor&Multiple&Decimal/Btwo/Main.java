package Btwo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int i = 1;
        int order = 0;
        while (i <= n) {
            if (n % i == 0) {
                order++;
                if (order == k) {
                    System.out.println(i);
                    break;
                }
            }
            i++;
        }
        if (order < k) {
            System.out.println(0);
        }
    }
}
