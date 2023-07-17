import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> allLines = new ArrayList<String>();
        ArrayList<String> yearReport = new ArrayList<String>();

        while (true) {
            printMenu();
            int command = scanner.nextInt();

            if (command == 1) {

                for (int month = 1; month <= 3; month++) {
                    String fileName = String.format("m.2021%02d.csv", month);
                    ArrayList<String> fileLines = fileReader.readFileContents(fileName);
                    allLines.addAll(fileLines);
                }
                System.out.println("Все месячные отчеты были успешно считаны!");

            } else if (command == 2) {

                yearReport = fileReader.readFileContents("y.2021.csv");
                System.out.println("Годовой отчет был успешно считан!");

            } else if (command == 3) {

                    MonthTotalPerYear monthTotalPerYear = new MonthTotalPerYear("2021");
                    if (monthTotalPerYear.areReportsRead(yearReport, allLines)) {
                        monthTotalPerYear.calculateTotals(allLines);
                        monthTotalPerYear.checkTotals(yearReport, allLines);
                    } else {
                        System.out.println("Необходимо сначала считать месячные и годовой отчёты.");
                    }

            } else if (command == 4) {
                MonthlyReport monthlyReport = new MonthlyReport(allLines);
                monthlyReport.printMonthlyReport();

            } else if (command == 5) {
                YearlyReport yearlyReport = new YearlyReport(yearReport);
                yearlyReport.printYearlyReport();

            } else if (command == 6) {
                scanner.close();
                System.out.println("Программа завершена!");
                break;

            } else {
                System.out.println("Некорректная команда!");
            }
        }
    }

    static void printMenu() {
        System.out.println("Здравствуйте, выберите действие:");
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию обо всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("6. Завершить программу");
    }
}
