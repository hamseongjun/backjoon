package eight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        str = str.trim();
        String[] arr = str.split(" ");

        if (str == "")
            System.out.print(0);
        else
            System.out.print(arr.length);
    }
}
