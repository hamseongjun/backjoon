import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Car {
    protected int speed = 0;
    protected String name;
    protected int score = 0;

    Car(int speed, String name) {
        this.speed = speed;
        this.name = name;
    }

    void go() {
        score += speed;
    }

    void printInfo() {
        System.out.println("스피드는" + speed + "이고, 이름은 " + name + "입니다.");
    }

    void sayScore() {
        System.out.println("Score: " + score);
    }
}

class SuperCar extends Car {
    private final Random random;
    private int booster = 0;

    SuperCar(int speed, String name) {
        super(speed, name);
        final long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    @Override
    void go() {
        if (random.nextInt(2) == 1) {
            score += 2 * speed;
            booster++;
        } else {
            score += speed;
        }
    }

    @Override
    void sayScore() {
        System.out.println("Score: " + score + ", booster: " + booster);
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
            System.out.println("이 자동차는 슈퍼카인가요? 0 또는 1 입력");
            int IsSuperCar = scanner.nextInt();

            if (IsSuperCar == 1) {
                Car car = new SuperCar(speed, name);
                cars.add(i, car);
            } else if (IsSuperCar == 0) {
                Car car = new Car(speed, name);
                cars.add(i, car);
            }
        }

        System.out.println("\n-----경기 참가자 소개-----");
        for (Car participant : cars)
            participant.printInfo();

        System.out.println("\n경기를 몇 초 동안 진행할까요?");
        int runningTime = scanner.nextInt();

        for (int i = 0; i < runningTime; i++) {
            for (Car participant : cars)
                if (random.nextInt(2) == 1) {
                    participant.go();
                }
        }

        System.out.println("\n---최종 결과 발표---");
        for (Car participant : cars)
            participant.sayScore();
    }
}