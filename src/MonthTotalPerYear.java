import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthTotalPerYear {
    private String year;
    private Map<String, Double> totalExpensePerMonth;
    private Map<String, Double> totalIncomePerMonth;

    public MonthTotalPerYear(String year) {
        this.year = year;
        totalExpensePerMonth = new HashMap<>();
        totalIncomePerMonth = new HashMap<>();
    }

    public String getYear() {
        return year;
    }

    public void calculateTotals(ArrayList<String> yearlyReport) {
        // инициализируем переменные
        HashMap<Integer, Double> incomePerMonth = new HashMap<>();
        HashMap<Integer, Double> expensesPerMonth = new HashMap<>();

        // перебираем строки годового отчета
        for (String line : yearlyReport) {
            String[] values = line.split(",");
            if (values.length < 3) {
                continue;
            }

            // проверяем правильность формата данных перед попыткой преобразования строки в число
            int month = 0;
            try {
                month = Integer.parseInt(values[0]);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка чтения номера месяца: " + e.getMessage());
                continue;
            }

            double amount = Double.parseDouble(values[1]);
            boolean isExpense = Boolean.parseBoolean(values[2]);

            if (isExpense) {
                expensesPerMonth.put(month, expensesPerMonth.getOrDefault(month, 0.0) + amount);
            } else {
                incomePerMonth.put(month, incomePerMonth.getOrDefault(month, 0.0) + amount);
            }
        }

        // выводим результаты
        for (int month = 1; month <= 3; month++) {
            double income = incomePerMonth.getOrDefault(month, 0.0);
            double expenses = expensesPerMonth.getOrDefault(month, 0.0);
            double total = income - expenses;
            System.out.println("Месяц " + month + ": доходы = " + income + ", расходы = " + expenses + ", прибыль = " + total);
        }
    }

    public double getTotalExpenseForMonth(String month) {
        return totalExpensePerMonth.getOrDefault(month, 0.0);
    }

    public double getTotalIncomeForMonth(String month) {
        return totalIncomePerMonth.getOrDefault(month, 0.0);
    }

    public boolean verifyData(ArrayList<String> yearReport) {
        Map<String, Double> expensePerMonth = new HashMap<>();
        Map<String, Double> incomePerMonth = new HashMap<>();
        double totalExpense = 0;
        double totalIncome = 0;

        // Вычисляем общую сумму доходов и расходов для каждого месяца
        for (String month : totalExpensePerMonth.keySet()) {
            double expense = totalExpensePerMonth.getOrDefault(month, 0.0);
            expensePerMonth.put(month, expense);
            totalExpense += expense;
        }

        for (String month : totalIncomePerMonth.keySet()) {
            double income = totalIncomePerMonth.getOrDefault(month, 0.0);
            incomePerMonth.put(month, income);
            totalIncome += income;
        }

        // Сверяем полученные суммы с суммой доходов и расходов в годовом отчете
        for (String line : yearReport) {
            String[] values = line.split(",");
            if (values.length >= 3) {
                String month = values[0];
                double amount = Double.parseDouble(values[1]);
                boolean isExpense = Boolean.parseBoolean(values[2]);

                if (isExpense) {
                    if (!expensePerMonth.containsKey(month) || expensePerMonth.get(month) != amount) {
                        System.out.println("Ошибка в месяце " + month + ": сумма расходов не совпадает");
                        return false;
                    }
                } else {
                    if (!incomePerMonth.containsKey(month) || incomePerMonth.get(month) != amount) {
                        System.out.println("Ошибка в месяце " + month + ": сумма доходов не совпадает");
                        return false;
                    }
                }
            }
        }

        // Проверяем, что сумма доходов и расходов за год совпадает
        if (totalExpense != getTotalExpense() || totalIncome != getTotalIncome()) {
            System.out.println("Ошибка: сумма доходов/расходов за год не совпадает");
            return false;
        }

        System.out.println("Данные проверены успешно");
        return true;
    }

    public double getTotalExpense() {
        double sum = 0;
        for (double value : totalExpensePerMonth.values()) {
            sum += value;
        }
        return sum;
    }

    public double getTotalIncome() {
        double sum = 0;
        for (double value : totalIncomePerMonth.values()) {
            sum += value;
        }
        return sum;
    }
    public boolean areReportsRead(ArrayList<String> yearReport, ArrayList<String> allLines) {
        return yearReport != null && !yearReport.isEmpty() && allLines != null && !allLines.isEmpty();
    }

    public boolean checkTotals(ArrayList<String> yearReport, ArrayList<String> allLines) {
        boolean result = verifyData(yearReport);
        if (result) {
            System.out.println("Отчёты сверены успешно!");
        } else {
            System.out.println("При сверке отчётов возникли ошибки.");
        }
        return result;
    }
}