package Car;

import java.util.ArrayList;
import java.util.Random;

public class RandomGo implements Go{
    final private Random random;
    RandomGo() {
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }
    public void race(ArrayList<Car> cars, int runningTime) {
        for (int i = 0; i < runningTime; i++) {
            for (Car participant : cars) {
                if (random.nextInt(2) == 1) {
                    participant.go();
                }
            }
        }
    }
}
