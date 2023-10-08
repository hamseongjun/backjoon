package one;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();
        int n = scanner.nextInt();


        System.out.print(str.charAt(n-1));
    }
}