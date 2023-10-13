package Dfour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int line = 2;
        for (int i = 0; i < n; i++) {
            line += line-1;
        }

        System.out.print(line*line);
    }
}
