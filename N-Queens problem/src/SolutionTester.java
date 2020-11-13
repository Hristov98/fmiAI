public class SolutionTester {
    public static void testSolution(int from, int to) {
        int size = from;
        ProblemSolution solution = new ProblemSolution(size);

        long totalStartTime = System.currentTimeMillis();
        do {
            solution.setN(size);
            long localStartTime = System.currentTimeMillis();
            solution.solveNQueens();
            double endTime = ((double) System.currentTimeMillis() - localStartTime) / 1000;
            System.out.println("Time to solve for " + size + " queens: " + endTime);

            size++;
        } while (size <= to);

        double totalTestTime = ((double) System.currentTimeMillis() - totalStartTime) / 1000;
        System.out.printf("Time to execute all tests from %d to %d: %f", from, to, totalTestTime);
    }

    public static void test10KSolutionNTimes(int cycles) {
        ProblemSolution solution = new ProblemSolution(10000);

        long totalStartTime = System.currentTimeMillis();
        int counter = 0;
        double worstTime = Integer.MIN_VALUE;
        double bestTime = Integer.MAX_VALUE;
        do {
            long localStartTime = System.currentTimeMillis();
            solution.solveNQueens();
            double endTime = ((double) System.currentTimeMillis() - localStartTime) / 1000;
            System.out.println("Time to solve test " + counter + " for 10000 queens: " + endTime);
            counter++;

            if (worstTime < endTime) {
                worstTime = endTime;
            }
            if (bestTime > endTime) {
                bestTime = endTime;
            }
        } while (counter < cycles);

        double totalTestTime = ((double) System.currentTimeMillis() - totalStartTime) / 1000;
        System.out.printf("Time to execute %d tests: %f\n", cycles, totalTestTime);
        System.out.println("Worst time: " + worstTime);
        System.out.println("Best time: " + bestTime);
        System.out.printf("Average time to execute: %.4f\n", totalTestTime / cycles);
    }
}
