public class Fibonacci
{
    private static int counter = 0;

    private static int recursiveSolution(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int result = recursiveSolution(n - 1) + recursiveSolution(n - 2);
        ++counter;
        System.out.println("recursive caculating fib(" + n + "): " + result);
        return result;
    }

    private static int cacheSolution(int n) {
        if (n < 2) return n;
        int[] cache = new int[n + 1];
        cache[1] = 1;

        // fill cache
        for (int i=2; i<n+1; i++) {
            cache[i] = cache[i-1] + cache[i-2];
            counter++;
            System.out.println("cache caculating fib(" + i + "): " + cache[i]);
        }

        return cache[n];
    }

    private static int noCacheSolution(int n) {
        if (n < 2) return n;
        int n0 = 0;
        int n1 = 1;

        // Just save the last 2 numbers, no need to keep all the calculation results
        for (int i=2; i<n; i++) {
            int n2 = n0 + n1;
            n0 = n1;
            n1 = n2;

            counter++;
            System.out.println("no-cache caculating fib(" + i + "): " + n2);
        }

        counter++;
        System.out.println("no-cache caculating fib(" + n + "): " + (n0 + n1));
        return n0 + n1;
    }

    public static void main(String[] args) {
        int n = 10;
        int result = 0;

        // recursivly caculate the result
        counter = 0;
        recursiveSolution(n);
        System.out.println("Recursive solution, caculating " + counter + " times.");

        counter = 0;
        cacheSolution(n);
        System.out.println("Cache solution, caculating " + counter + " times.");

        counter = 0;
        noCacheSolution(n);
        System.out.println("No-Cache solution, caculating " + counter + " times.");
    }
}