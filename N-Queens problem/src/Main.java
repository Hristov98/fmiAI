import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();

        if (N == 1) {
            System.out.println("*");
        }
        if (N < 1 || N == 2 || N == 3) {
            System.out.println("No solution");
        }
        if (N >= 4) {
            ProblemSolution solution = new ProblemSolution(N);

            long startTime = System.currentTimeMillis();
            solution.solveNQueens();
            double endTime = ((double) System.currentTimeMillis() - startTime) / 1000;

            System.out.println("Time to solve: " + endTime);
            solution.printState();

            //SolutionTester.testSolution(4,100);
            //SolutionTester.testSolution(100,1000);
            //SolutionTester.testSolution(1000,5000);
            //SolutionTester.testSolution(5000,10000);
            //SolutionTester.test10KSolutionNTimes(50);
        }
    }
}
