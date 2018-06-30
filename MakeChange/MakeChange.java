public class MakeChange {
    private static int[] coins = new int[] {10, 6, 1};
    private static int count = 0;


    private static int firstSolution(int total) {
        if (total == 0) return 0;
        int minCoins = Integer.MAX_VALUE;
        for (int coin : coins) {
            // check every posiible solution
            if (total - coin >= 0) {
                int currMinCoins = firstSolution(total - coin);
                if (currMinCoins < minCoins) {
                    minCoins = currMinCoins;
                }
            }
        }

        int result = minCoins + 1;
        System.out.println("(" + (++count) + ")First solution: making change for " + total + ": " + result);
        return result;
    }

    private static int topDownSolution(int total) {
        int[] cache = new int[total + 1];
        for (int i=1; i<total+1; i++) {
            cache[i] = -1;
        }

        return topDown(total, cache);
    }

    // still check every possible solution, but only caculate the result the first time,
    // then save it in cache. return it directly later
    private static int topDown(int total, int[] cache) {
        if (cache[total] >= 0) return cache[total];
        
        int minCoins = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (total - coin >= 0) {
                int currMinCoins = topDown(total - coin, cache);
                if (currMinCoins < minCoins) {
                    minCoins = currMinCoins;
                }
            }
        }

        // save it
        cache[total] = minCoins + 1;
        System.out.println("(" + (++count) + ")Top down solution: making change for " + total + ": " + cache[total]);
        return cache[total];
    }

    private static int botUpSolution(int total) {
        int[] cache = new int[total + 1];
        for (int i=1; i<total+1; i++) {
            // cacluate from "1" to "total" amount
            int minCoins = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0) {
                    int curCoins = cache[i - coin] + 1;
                    if (curCoins < minCoins) {
                        minCoins = curCoins;
                    }
                }

            }

            cache[i] = minCoins;
            System.out.println("(" + (++count) + ")Bot up solution: making change for " + i + ": " + cache[i]);
        }

        return cache[total];
    }

    public static void main(String[] args) {
        count = 0;
        firstSolution(12);
        count = 0;
        topDownSolution(12);
        count = 0;
        botUpSolution(12);
    }

}