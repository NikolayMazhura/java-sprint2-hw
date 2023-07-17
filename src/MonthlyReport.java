import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReport {
    private ArrayList<String> monthlyReportLines;
    private Map<String, Map<String, Double>> itemsPerMonth;
    private Map<String, Double> highestExpensePerMonth;
    private Map<String, Double> highestIncomePerMonth;

    public MonthlyReport(ArrayList<String> monthlyReportLines) {
        this.monthlyReportLines = monthlyReportLines;
        itemsPerMonth = new HashMap<>();
        highestExpensePerMonth = new HashMap<>();
        highestIncomePerMonth = new HashMap<>();
    }

    public void printMonthlyReport() {
        // Проверяем, что месячные отчеты были считаны
        if (monthlyReportLines == null || monthlyReportLines.isEmpty()) {
            System.out.println("Необходимо сначала считать месячные отчёты.");
            return;
        }

        // Обходим каждую строку месячных отчетов
        for (String line : monthlyReportLines) {
            String[] lineContents = line.split(",");
            if (lineContents.length < 4) {
                continue;
            }


            String itemName = lineContents[0];
            boolean isExpense = Boolean.parseBoolean(lineContents[1]);
            int quantity = Integer.parseInt(lineContents[2]);
            double unitPrice = Double.parseDouble(lineContents[3]);
            double amount = quantity * unitPrice;
            String month = lineContents[lineContents.length - 1];


            if (!itemsPerMonth.containsKey(month)) {
                itemsPerMonth.put(month, new HashMap<>());
            }
            Map<String, Double> items = itemsPerMonth.get(month);
            if (!items.containsKey(itemName)) {
                items.put(itemName, 0.0);
            }
            double itemTotal = items.get(itemName);
            items.put(itemName, itemTotal + amount);


            if (isExpense) {
                double expense = highestExpensePerMonth.getOrDefault(month, 0.0);
                highestExpensePerMonth.put(month, Math.max(expense, amount));
            } else {
                double income = highestIncomePerMonth.getOrDefault(month, 0.0);
                highestIncomePerMonth.put(month, Math.max(income, amount));
            }
        }


        for (String month : itemsPerMonth.keySet()) {
            System.out.println("Месяц: " + month);


            String mostProfitableItem = "";
            double mostProfitableItemAmount = 0;
            for (Map.Entry<String, Double> entry : itemsPerMonth.get(month).entrySet()) {
                if (entry.getValue() > mostProfitableItemAmount) {
                    mostProfitableItem = entry.getKey();
                    mostProfitableItemAmount = entry.getValue();
                }
            }
            System.out.println("Самый прибыльный товар: " + mostProfitableItem + " (сумма: " + mostProfitableItemAmount + ")");


            double highestExpense = highestExpensePerMonth.getOrDefault(month, 0.0);
            System.out.println("Самая большая тратa: " + highestExpense + " (сумма: " + highestExpense + ")");
        }
    }
}