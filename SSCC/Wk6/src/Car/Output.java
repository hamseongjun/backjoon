package Car;

import java.util.ArrayList;

public class Output {
    static void introduceParticipants(ArrayList<Car> cars) {
        System.out.println("\n---경기 참가자 소개---");
        for (Car participant : cars) {
            participant.printInfo();
        }
    }

    static void announceFinalResult(ArrayList<Car> cars) {
        System.out.println("\n---최종 결과 발표---");
        for (Car participant : cars) {
            participant.sayScore();
        }
    }
}
