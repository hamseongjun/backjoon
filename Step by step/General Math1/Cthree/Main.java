package Cthree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            int money = scanner.nextInt();

            int quarter = money / 25;
            money -= quarter * 25;

            int dime = money / 10;
            money -= dime * 10;

            int nickel = money / 5;
            money -= nickel * 5;

            int penny = money % 5;

            System.out.println(quarter + " " + dime + " " + nickel + " " + penny);
        }
    }
}
