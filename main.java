import java.io.File;
import java.util.Scanner;

public class main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    // 3D array: [month][day][commodity]
    static int[][][] data = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            String filename = "Data_Files/" + months[m] + ".txt";
            Scanner reader = null;

            try {
                reader = new Scanner(new File(filename));
                reader.nextLine(); // Skip header line

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");

                    for (int d = 0; d < data.length; d++) {
                        int day = Integer.parseInt(parts[0]) - 1; // Convert to 0-indexed
                        String commodity = parts[1];
                        int profit = Integer.parseInt(parts[2]);

                        // Find commodity index
                        int commIdx = -1;
                        for (int c = 0; c < COMMS; c++) {
                            if (commodities[c].equals(commodity)) {
                                commIdx = c;
                                break;
                            }
                        }

                        // Store data if valid
                        if (commIdx != -1 && day >= 0 && day < DAYS) {
                            data[m][day][commIdx] = profit;
                        }
                    }
                }
            } catch (Exception e) {
                // Handle file read errors silently
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS ========

    public static String mostProfitableCommodityInMonth(int month) {if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int[] totals = new int[COMMS];
        for (int d = 0; d < DAYS; d++) {
            for (int c = 0; c < COMMS; c++) {
                totals[c] += data[month][d][c];
            }
        }

        int maxIdx = 0;
        for (int c = 1; c < COMMS; c++) {
            if (totals[c] > totals[maxIdx]) {
                maxIdx = c;
            }
        }

        return commodities[maxIdx] + " " + totals[maxIdx];

    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) return -99999;

        int total = 0;
        int dayIdx = day - 1; // Convert to 0-indexed
        for (int c = 0; c < COMMS; c++) {
            total += data[month][dayIdx][c];
        }
        return total;
    }



    public static int commodityProfitInRange(String commodity, int from, int to) {

    }

    public static int bestDayOfMonth(int month) {

    }

    public static String bestMonthForCommodity(String comm) {

    }

    public static int consecutiveLossDays(String comm) {

    }

    public static int daysAboveThreshold(String comm, int threshold) {


    }

    public static int biggestDailySwing(int month) {

    }

    public static String compareTwoCommodities(String c1, String c2) {


    }

    public static String bestWeekOfMonth(int month) {

    }

    public static void main(String[] args) {

}