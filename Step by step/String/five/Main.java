package five;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        String str = scanner.next();

        int sum = 0;
        String toInt;
        for(int i = 0; i < n; i++) {
            toInt = str.substring(i, i+1);
            sum += Integer.parseInt(toInt);
        }

        System.out.print(sum);
    }
}
