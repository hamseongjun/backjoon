package seven;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        int r;
        String s;

        for (int i = 0; i < t; i++) {
            r = scanner.nextInt();
            s = scanner.next();
            for (int j = 0; j < s.length(); j++) {
                for (int k = 0; k < r; k++) {
                    System.out.print(s.charAt(j));
                }
            }
            System.out.println();
        }
    }
}
