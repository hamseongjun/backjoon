import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Car {
    private final int speed;
    private final String name;
    private int score;

    Car(int speed, String name) {
        this.speed = speed;
        this.name = name;
    }

    void plusScore(int num) {
        score += num;
    }

    void printInfo() {
        System.out.println("스피드는" + speed + "이고, 이름은 " + name + "입니다.");
    }

    int getSpeed() {
        return speed;
    }

    int getScore() {
        return score;
    }
}

public class HwCar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);

        int n;
        System.out.println("자동차의 갯수를 입력하세요.");
        n = scanner.nextInt();

        ArrayList<Car> cars = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println(i + 1 + "번 째 자동차의 스피드를 입력하세요.");
            int speed = scanner.nextInt();
            System.out.println(i + 1 + "번 째 자동차의 이름을 입력하세요.");
            String name = scanner.next();

            Car car = new Car(speed, name);
            cars.add(i, car);
        }

        System.out.println("\n-----경기 참가자 소개-----");
        for (Car participant : cars)
            participant.printInfo();

        System.out.println("\n경기를 몇 초 동안 진행할까요?");
        int runningTime = scanner.nextInt();

        for (int i = 0; i < runningTime; i++) {
            for (Car participant : cars)
                participant.plusScore(participant.getSpeed() * random.nextInt(2));
        }

        System.out.println("\n---최종 결과 발표---");
        for (Car participant : cars)
            System.out.println(participant.getScore());

    }
}