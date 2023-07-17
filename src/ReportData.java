import java.util.ArrayList;
import java.util.HashMap;

public class ReportData {

    public static void compareReports(HashMap<String, ArrayList<String>> reports, HashMap<String, ArrayList<String>> yearlyReports) {
        // получаем данные из годового отчета
        ArrayList<String> yearlyReportData = yearlyReports.get("y.2021.csv");
        // получаем сумму всех доходов из годового отчета
        double yearlyTotalIncome = calculateTotalIncome(yearlyReportData);

        // перебираем месячные отчеты и сравниваем данные в них
        for (String fileName : reports.keySet()) {
            ArrayList<String> reportData = reports.get(fileName);
            double totalIncome = calculateTotalIncome(reportData);
            if (totalIncome != yearlyTotalIncome) {
                System.out.println("Ошибка: данные в отчете " + fileName + " не сходятся с годовым отчетом");
                return; // выходим из метода при обнаружении ошибки
            }
        }

        // если мы дошли до этой точки, значит ошибок не обнаружено
        System.out.println("Данные проверены, ошибок нет!");
    }

    public static void calculateMonthlyTotals(HashMap<String, ArrayList<String>> reportData) {
        FileReader reader = new FileReader();
        for (String fileName : reportData.keySet()) {
            String pathToFile = fileName;
            ArrayList<String> reportContent = reader.readFileContents(pathToFile);
            double totalIncome = 0;
            double totalExpense = 0;

            for (String line : reportContent) {
                String[] fields = line.split(",");
                double income = Double.parseDouble(fields[2]);
                double expense = Double.parseDouble(fields[3]);
                totalIncome += income;
                totalExpense += expense;
            }

            System.out.println("Отчет " + fileName + ":");
            System.out.println("Сумма доходов: " + totalIncome);
            System.out.println("Сумма расходов: " + totalExpense);
            System.out.println();
        }
    }

    public static void printAllReports(HashMap<String, ArrayList<String>> reports) {
        for (String fileName : reports.keySet()) {
            System.out.println("Отчет " + fileName + ":");
            ArrayList<String> reportData = reports.get(fileName);
            for (String line : reportData) {
                System.out.println(line);
            }
            System.out.println("Сумма доходов: " + calculateTotalIncome(reportData));
            System.out.println();
        }
    }

    public static void printYearlyReport(HashMap<String, ArrayList<String>> yearlyReports) {
        String fileName = "y.2021.csv";
        System.out.println("Годовой отчет " + fileName + ":");
        ArrayList<String> yearlyReportData = yearlyReports.get(fileName);
        for (String line : yearlyReportData) {
            System.out.println(line);
        }
        System.out.println("Сумма доходов: " + calculateTotalIncome(yearlyReportData));
        System.out.println();
    }

    private static double calculateTotalIncome(ArrayList<String> reportData) {
        double totalIncome = 0;
        for (String line : reportData) {
            String[] fields = line.split(",");
            double income = Double.parseDouble(fields[2]); // правильный индекс для доходов
            totalIncome += income;
        }
        return totalIncome;
    }
}