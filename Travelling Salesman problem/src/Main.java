import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();

        long startTime = System.currentTimeMillis();
        if (N < 1) {
            System.out.println("No solution");
        }
        if (N == 1) {
            System.out.println(0);
        }
        if (N >= 2) {
            ProblemSolution solution = new ProblemSolution(N);
            solution.findCheapestPathThroughNCities();

            System.out.println("\nBest path is: ");
            System.out.println(solution.getBestPath());
        }

        System.out.println("Time taken: " + ((double)
                (System.currentTimeMillis() - startTime) / 1000) + " seconds");
    }
}
