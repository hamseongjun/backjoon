package Car;

import java.util.Random;
import java.util.Scanner;

public class Input {
    static int getNumberOfCars() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("자동차의 갯수를 입력하세요.");
        return scanner.nextInt();
    }

    static Car createCar(Scanner scanner) {
        System.out.println("자동차의 속도를 입력하세요.");
        int speed = scanner.nextInt();
        System.out.println("자동차의 이름을 입력하세요.");
        String name = scanner.next();
        System.out.println("이 자동차는 슈퍼카인가요? 0 또는 1 입력");
        int isSuperCar = scanner.nextInt();

        if (isSuperCar == 1) {
            return new SuperCar(speed, name);
        } else {
            return new Car(speed, name);
        }
    }
}