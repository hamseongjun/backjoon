package CThree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = -1;
        int sum = 0;
        Queue<Integer> queue = new LinkedList<>();

        while (true) {
            n = scanner.nextInt();
            if (n == -1) {
                break;
            }

            sum = 0;

            for (int i = 1; i < n; i++) {
                if (n % i == 0) {
                    queue.add(i);
                    sum += i;
                }
            }

            if (sum == n) {
                System.out.print(n + " = " + queue.poll());
                while (!queue.isEmpty()) {
                    System.out.print(" + " + queue.poll());
                }
                System.out.println();
            }

            else {
                queue.clear();
                System.out.println(n + " is NOT perfect.");
            }
        }
    }
}
