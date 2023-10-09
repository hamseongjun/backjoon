package two;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] arr = new int[9][9];
        int max = 0;
        int maxI = 0, maxJ = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                arr[i][j] = scanner.nextInt();
                if (arr[i][j] > max) {
                    max = arr[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        System.out.println(max);
        System.out.print(String.valueOf(maxI+1) + " " + String.valueOf(maxJ+1));
    }
}
