// Main.java — Students version
import java.io.File;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;

    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    static int[][][] data = new int[MONTHS][DAYS][COMMS];
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            String filename = "Data_Files/" + months[m] + ".txt";
            Scanner reader = null;

            try {
                File file = new File(filename);
                if (!file.exists()) continue;

                reader = new Scanner(file);
                if (reader.hasNextLine()) reader.nextLine();

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");

                    if (parts.length < 3) continue;

                    int day = Integer.parseInt(parts[0].trim()) - 1;
                    String commodity = parts[1].trim();
                    int profit = Integer.parseInt(parts[2].trim());

                    int commIdx = -1;
                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equalsIgnoreCase(commodity)) {
                            commIdx = c;
                            break;
                        }
                    }

                    if (commIdx != -1 && day >= 0 && day < DAYS) {
                        data[m][day][commIdx] = profit;
                    }
                }
            } catch (Exception e) {

            } finally {
                if (reader != null) reader.close();
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int[] totals = new int[COMMS];
        for (int i = 0; i < DAYS; i++) {
            for (int j = 0; j < COMMS; j++) {
                totals[j] += data[month][i][j];
            }
        }

        int max = 0;
        for (int c = 1; c < COMMS; c++) {
            if (totals[c] > totals[max]) {
                max = c;
            }
        }
        return commodities[max] + " " + totals[max];
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) return -99999;
        int total = 0;
        for (int i = 0; i < COMMS; i++) {
            total += data[month][day - 1][i];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 1 || from > DAYS || to < 1 || to > DAYS || from > to) return -99999;

        int commindex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equalsIgnoreCase(commodity)) {
                commindex = c;
                break;
            }
        }
        if (commindex == -1) return -99999;

        int total = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d < to; d++) {
                total += data[m][d][commindex];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;
        int maxday = 1;
        int maxTotal = 0;

        for (int i = 0; i < DAYS; i++) {
            int newtotal = 0;
            for (int j = 0; j < COMMS; j++) {
                newtotal += data[month][i][j];
            }
            if (newtotal > maxTotal) {
                maxTotal = newtotal;
                maxday = i + 1;
            }
        }

        return maxday;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int commindex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equalsIgnoreCase(comm)) {
                commindex = i;
                break;
            }
        }
        if (commindex == -1) return "INVALID_COMMODITY";

        int maxmonth = 0;
        int maxtotal = 0;
        for (int m = 0; m < MONTHS; m++) {
            int total = 0;
            for (int d = 0; d < DAYS; d++) {
                total += data[m][d][commindex];
            }
            if (total > maxtotal) {
                maxtotal = total;
                maxmonth = m;
            }
        }
        return months[maxmonth];
    }

    public static int consecutiveLossDays(String comm) {
        int commindex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equalsIgnoreCase(comm)) {
                commindex = c;
                break;
            }
        }
        if (commindex == -1) return -1;

        int maxstreak = 0;
        int currentstreak = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                if (data[i][j][commindex] < 0) {
                    currentstreak++;
                    if (currentstreak > maxstreak) maxstreak = currentstreak;
                } else {
                    currentstreak = 0;
                }
            }
        }
        return maxstreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int commidex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equalsIgnoreCase(comm)) {
                commidex = i;
                break;
            }
        }
        if (commidex == -1) return -1;

        int count = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                if (data[i][j][commidex] > threshold) count++;
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;
        int maxSwing = 0;
        for (int i = 0; i < DAYS - 1; i++) {
            int day1 = 0, day2 = 0;
            for (int j = 0; j < COMMS; j++) {
                day1 += data[month][i][j];
                day2 += data[month][i + 1][j];
            }
            int swing = day1Total - day2Total;
            if (swing < 0) swing = -swing;
        }
        if (swing > maxSwing) {
            maxSwing = swing;
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        int comm1 = -1, comm2 = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equalsIgnoreCase(c1)) comm1 = i;
            if (commodities[i].equalsIgnoreCase(c2)) comm2 = i;
        }
        if (comm1 == -1 || comm2 == -1) return "INVALID_COMMODITY";

        long total1 = 0, total2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += data[m][d][comm1];
                total2 += data[m][d][comm2];
            }
        }
        if (total1 > total2) {
            return c1 + " is better by " + (total1 - total2);
        } else if (total2 > total1) {
            return c2 + " is better by " + (total2 - total1);
        } else {
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int[] weekTotals = new int[4];

        for (int w = 0; w < 4; w++) {
            for (int d = w * 7; d < (w + 1) * 7; d++) {
                for (int c = 0; c < COMMS; c++) {
                    weekTotals[w] += data[month][d][c];
                }
            }
        }
        int maxW = 0;
        for (int w = 1; w < 4; w++) {
            if (weekTotals[w] > weekTotals[maxW]) maxW = w;
        }
        return "Week " + (maxW + 1);
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}