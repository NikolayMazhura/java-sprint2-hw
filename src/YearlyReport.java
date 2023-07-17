
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    private ArrayList<String> yearlyReportLines;  // список строк годового отчета
    private HashMap<String, Double> incomePerMonth;  // сумма доходов по месяцам
    private HashMap<String, Double> expensesPerMonth;  // сумма расходов по месяцам

    public YearlyReport(ArrayList<String> yearlyReportLines) {
        this.yearlyReportLines = yearlyReportLines;
        this.incomePerMonth = new HashMap<>();
        this.expensesPerMonth = new HashMap<>();
    }

    // метод для вывода годового отчета
    public void printYearlyReport() {

        // проверяем, что список строк годового отчета не пустой
        if (yearlyReportLines == null || yearlyReportLines.isEmpty()) {
            System.out.println("Необходимо сначала считать годовой отчёт.");
            return;
        }

        // получаем год из первой строки годового отчета
        String year = "";
        if (!yearlyReportLines.isEmpty()) {
            String[] firstLineContents = yearlyReportLines.get(0).split(",");
            if (firstLineContents.length >= 3) {
                year = firstLineContents[2];
            }
        }

        // вычисляем сумму доходов и расходов по месяцам
        for (String line : yearlyReportLines) {
            String[] lineContents = line.split(",");
            if (lineContents.length < 3) {
                continue;
            }
            boolean isExpense = Boolean.parseBoolean(lineContents[0]);
            double amount = Double.parseDouble(lineContents[1]);
            String month = lineContents[2];
            if (isExpense) {
                expensesPerMonth.put(month, expensesPerMonth.getOrDefault(month, 0.0) + amount);
            } else {
                incomePerMonth.put(month, incomePerMonth.getOrDefault(month, 0.0) + amount);
            }
        }

        // вычисляем общую сумму доходов и расходов за год, а также средние значения по месяцам
        double totalIncome = 0;
        double totalExpenses = 0;
        for (double income : incomePerMonth.values()) {
            totalIncome += income;
        }
        for (double expense : expensesPerMonth.values()) {
            totalExpenses += expense;
        }
        double averageIncome = totalIncome / incomePerMonth.size();
        double averageExpense = totalExpenses / expensesPerMonth.size();

        // выводим отчет на экран
        System.out.println("Годовой отчет за " + year + ":");
        for (String month : incomePerMonth.keySet()) {
            System.out.println("Прибыль за месяц " + month + ": " + incomePerMonth.get(month));
        }
        for (String month : expensesPerMonth.keySet()) {
            System.out.println("Расходы за месяц " + month + ": " + expensesPerMonth.get(month));
        }
        System.out.println("Средний доход за год: " + averageIncome);
        System.out.println("Средние расходы за год: " + averageExpense);
    }
}