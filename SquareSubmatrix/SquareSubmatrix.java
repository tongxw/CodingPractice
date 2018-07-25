import java.lang.*;

public class SquareSubmatrix {
    private static boolean arr[][] = {
        {false, true, false, false},
        {true, true, true, true},
        {false, true, true, true},
        {false, true, true, true}
    };

    private static int call_count = 0;

    private static int getMaxSqr(boolean arr[][]) {
        // for every element in the array, find the sub matrix of "true" which includes this element in the top-left.
        // then get the max size for all these sub matrix
        int max = 0;
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[0].length; j++) {
                if (arr[i][j]) {
                    max = Math.max(max, getSubSqr(arr, i, j));
                }
            }
        }

        System.out.println("the size of the sub matrix is: " + max);
        return max;
    }


    private static int getSubSqr(boolean arr[][], int i, int j) {
        if (i == arr.length) {
            return 0;
        }

        if (arr.length >= 1 && j == arr[0].length) {
            return 0;
        }

        if (!arr[i][j]) {
            return 0;
        }

        // this element is "true", so the size is 1 at least
        int result = 1;

        /* If our current cell is arr[0][0], we find that the cells arr[0][1], arr[1][0], and arr[1][1] are each
         * the upper left-hand corner of their own respective 3x3 subarrays. With that knowledge, we can see our
         * current cell, arr[0][0], is the only cell missing in a subarray of the next size larger.
         * We can generalize based on this realization. If a given cell is true, then it is the upper lefthand
         * corner of the minimum size of the three subarrays to the bottom, right, and bottom-right. It is,
         * therefore,possible to implement this recursively. This can be done by iterating through each cell and
         * recursively finding the size of the largest square subarrays to the bottom, right, and bottom-right,
         * and combine it to get our solution.
         */
        result += Math.min(Math.min(getSubSqr(arr, i+1, j), getSubSqr(arr, i, j+1)), getSubSqr(arr, i+1, j+1));
        ++call_count;
        //System.out.println("sub sqr for (" + i + ", " + j + ") :" + result);
        return result;
    }

    // improve the previous functions by adding a cache
    private static int getMaxSqrWithCache(boolean arr[][]) {
        // for every element in the array, find the sub matrix of "true" which includes this element in the top-left.
        // then get the max size for all these sub matrix
        int[][] cache = new int[arr.length][arr[0].length];
        int max = 0;
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[0].length; j++) {
                if (arr[i][j]) {
                    max = Math.max(max, getSubSqrWithCache(arr, i, j, cache));
                }
            }
        }

        System.out.println("using cache: the size of the sub matrix is: " + max);
        return max;
    }

    private static int getSubSqrWithCache(boolean arr[][], int i, int j, int[][] cache) {
        if (i == arr.length) {
            return 0;
        }

        if (arr.length >= 1 && j == arr[0].length) {
            return 0;
        }

        if (!arr[i][j]) {
            return 0;
        }

        // check cache first
        if (cache[i][j] > 0) {
            return cache[i][j];
        }

        // caculate the result and keep it in cache
        cache[i][j] = 1 + Math.min(Math.min(getSubSqr(arr, i+1, j), getSubSqr(arr, i, j+1)), getSubSqr(arr, i+1, j+1));
        ++call_count;
        //System.out.println("save to cache: sub sqr for (" + i + ", " + j + ") :" + cache[i][j]);
        return cache[i][j];
    }

    // bottom up
    private static int getMaxSqrBest(boolean[][] arr) {
        int max = 0;
        int[][] cache = new int[arr.length][arr[0].length];

        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[0].length; j++) {
                if (i == 0 || j == 0) {
                    // first column / row, the max size of sub square with all "true" is 1 or 0, depending on self value
                    cache[i][j] = arr[i][j] ? 1 : 0;
                } else if (arr[i][j]) {
                    // for other element, if it is a "true", then add the size of sub square from top, left and top-left
                    cache[i][j] = 1 + Math.min(Math.min(cache[i][j-1], cache[i-1][j]), cache[i-1][j-1]);
                    ++call_count;
                }

                if (cache[i][j] > max) {
                    max = cache[i][j];
                }
            }
        }

        System.out.println("bottom up: the size of the sub matrix is: " + max);
        return max;
    }



    public static void main(String[] args) {
        call_count = 0;
        getMaxSqr(arr);
        System.out.println("get max squire, calculate " + call_count + " times");
        call_count = 0;
        getMaxSqrWithCache(arr);
        System.out.println("get max squire with cache, calculate " + call_count + " times");
        call_count = 0;
        getMaxSqrBest(arr);
        System.out.println("bottom up: get max squire, calculate " + call_count + " times");
    }
}