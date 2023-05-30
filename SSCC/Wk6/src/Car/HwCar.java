package Car;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HwCar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Input.getNumberOfCars();
        ArrayList<Car> cars = new ArrayList<Car>();

        for (int i = 0; i < n; i++) {
            System.out.println(i + 1 + "번째 자동차의");
            Car car = Input.createCar(scanner);
            cars.add(car);
        }

        Output.introduceParticipants(cars);

        System.out.println("\n경기를 몇 초 동안 진행할까요?");
        int runningTime = scanner.nextInt();

        RandomGo randomGo = new RandomGo();
        randomGo.race(cars, runningTime);

        Output.announceFinalResult(cars);
    }
}