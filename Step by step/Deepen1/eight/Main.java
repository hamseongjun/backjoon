package eight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = 20;

        // 20개 데이터 입력
        Cell[] cells = new Cell[n];
        for (int i = 0; i < n; i++) {
            String subject = scanner.next();
            double credit = scanner.nextDouble();
            String grade = scanner.next();
            cells[i] = new Cell(subject, credit, grade);
        }

        // 점수 계산
        double sumGrade = 0, sumCredit = 0;
        for (int i = 0; i < n; i++) {
            // P는 계산에서 제외
            if (cells[i].isPass)
                continue;

            sumGrade += (cells[i].credit * cells[i].grade);
            sumCredit += cells[i].credit;
        }
        // 결과 출력
        System.out.print(sumGrade / sumCredit);
    }
}

class Cell {
    String subject;
    double credit;
    double grade = 0;
    boolean isPass = false;

    Cell (String subject, double credit, String grade) {
        this.subject = subject;
        this.credit = credit;
        switch (grade) {
            case "A+":
                this.grade = 4.5;
                break;
            case "A0":
                this.grade = 4.0;
                break;
            case "B+":
                this.grade = 3.5;
                break;
            case "B0":
                this.grade = 3.0;
                break;
            case "C+":
                this.grade = 2.5;
                break;
            case "C0":
                this.grade = 2.0;
                break;
            case "D+":
                this.grade = 1.5;
                break;
            case "D0":
                this.grade = 1.0;
                break;
            case "F":
                this.grade = 0.0;
                break;
            default:
                isPass = true;
                break;
        }
    }
}