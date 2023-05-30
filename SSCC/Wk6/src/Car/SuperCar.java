package Car;

import java.util.Random;

public class SuperCar extends Car {
    private final Random random;
    private int booster = 0;

    public SuperCar(String name, int speed) {
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