package Dfour;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            numbers.add(scanner.nextInt()); // n만큼 리스트에 add
        }

        int count = 0;
        int i = 0;
        for (int number : numbers) {
            if (number == 2) {  // 숫자 2 소수 처리
                count++;
                continue;
            }

            for (i = 2; i < number; i++) {
                if (number % i == 0) {  // 약수가 있으면 break
                    break;
                }
            }
            if (i == number)
                count++;        // 소수면 count++
        }

        System.out.print(count);
    }
}
