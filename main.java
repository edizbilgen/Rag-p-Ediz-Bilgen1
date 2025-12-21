import java.io.File;
import java.util.Scanner;

public class main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

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

    public static String mostProfitableCommodityInMonth(int month) {
       if((month < 0 || month >12)) {
           return "INVALID MONTH ";
       }
       int[] totalProfit = new int[COMMS];
       for(int d = 0; d < DAYS; d++) {
           for  (int c = 0; c < COMMS; c++) {
               totalProfit[c] += data[month][d][c];
               }
           }
       int index = 0;
       int profit = 0;
       for(int i = 0; i < COMMS; i++) {
           if(totalProfit[i] > profit) {
               profit = totalProfit[i];
               index = i;
           }
       }

       return "Commodity totalProfit "+ months[index] + profit ;
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month < 0 || month >12 || day < 0 || day > DAYS) {
            return -99999;
        }
        int totalprofit = 0;
        for(int d = 0; d <  COMMS; d++) {
            totalprofit += data[month][day-1][d];
        }
        return totalprofit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 1 || from > DAYS || to < 1 || to > DAYS || from > to) return -99999;

        int commIdx = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(commodity)) {
                commIdx = c;
                break;
            }
        }
        if (commIdx == -1) return -99999;

        int total = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d < to; d++) {
                total += data[m][d][commIdx];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if((month < 0 || month >12)) {
            return -1;
        }
        int[] totalProfitonday = new int[DAYS];
        int best = 0;
        for(int d = 0; d < DAYS; d++) {
            for(int c = 0; c < COMMS; c++) {
                totalProfitonday[d] += data[month][d][c];

            }
        }
        for(int d = 0; d < DAYS; d++) {
            if(totalProfitonday[d] > best) {
                best = totalProfitonday[d];
            }
        }
        return best;

    }

    public static String bestMonthForCommodity(String comm) {
        int indexmonht= 0;
        int indexcomm = 0;
            for(int d = 0; d < COMMS; d++) {
                if(commodities[d].equals(comm));
                indexcomm = d;
                break;
            }
            if(indexcomm == -1) {return "INVALID_COMMODITY";}
            int[] profitcomm = new int[MONTHS];
            int profit = 0;

            for( int i = 0; i < MONTHS; i++ ) {
                for(int d = 0; d < DAYS; d++ ) {
                   profitcomm[i] += data[i][d][indexcomm];
                   indexmonht = i;
                }
                if(profitcomm[i] > profit) {
                    profit = profitcomm[i];

                }

            }

        return "Month name " + months[indexmonht] + " with highest total profit for that commodity";
    }

    public static int consecutiveLossDays(String comm) {

    }

    public static int daysAboveThreshold(String comm, int threshold) {

    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;

        int maxSwing = 0;

        for (int i = 0; i < DAYS - 1; i++) {
            int day1total = 0;
            int day2total = 0;

            for (int j = 0; j < COMMS; j++) {
                day1total += data[month][i][j];
                day2total += data[month][i + 1][j];
            }

            int swing = day1total - day2total;
            if (swing < 0) swing = -swing; // Absolute value

            if (swing > maxSwing) {
                maxSwing = swing;
            }
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int idx1 = -1;
        int idx2 = -1;

        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(c1)) {
                idx1 = c;
            }
            if (commodities[c].equals(c2)) {
                idx2 = c;
            }
        }

        if (idx1 == -1 || idx2 == -1) return "INVALID_COMMODITY";

        int total1 = 0;
        int total2 = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += data[m][d][idx1];
                total2 += data[m][d][idx2];
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

    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        System.out.println( bestWeekOfMonth(2));
    }
}