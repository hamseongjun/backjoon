import Car.Car;
import Car.SuperCar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    @Test
    @DisplayName("객체 생성 테스트")
    void carGenerateTest(String name, int speed) {
        SuperCar superCar = new SuperCar(name, speed);
        Car car = new Car(name, speed);

        assertEquals(superCar.speed, speed);
    }
}
