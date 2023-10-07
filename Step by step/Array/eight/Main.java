package eight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean[] arr = new boolean[42];
        int rem, count = 0;

        for (int i = 0; i < 10; i ++) {
            rem = scanner.nextInt();
            rem %= 42;
            arr[rem] = true;
        }

        for (int i = 0; i < 42; i++) {
            if (arr[i])
                count++;
        }

        System.out.print(count);
    }
}
