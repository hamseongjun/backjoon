package Car;

import java.util.ArrayList;
import java.util.Random;

public class AlwaysGo implements Go {
    public void race(ArrayList<Car> cars, int runningTime) {
        for (int i = 0; i < runningTime; i++) {
            for (Car participant : cars)
                participant.go();
        }
    }
}
