package solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ProblemSolution {
    private static final int GOAL_STATE_REACHED = -1;
    public static State startState;
    public static ArrayList<String> movesToGoal;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int zeroIndex = scanner.nextInt();

        int size = (int) Math.sqrt(N + 1);
        int[][] start = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                start[i][j] = scanner.nextInt();
            }
        }

        int[][] goal = new int[size][size];

        if (zeroIndex == -1 || zeroIndex == N) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    goal[i][j] = i * size + j + 1;
                }
            }
            goal[size - 1][size - 1] = 0;
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (zeroIndex > i * size + j) {
                        goal[i][j] = i * size + j + 1;
                    } else if (zeroIndex == i * size + j) {
                        goal[i][j] = 0;
                    } else {
                        goal[i][j] = i * size + j;
                    }
                }
            }
        }

        GoalState goalState = new GoalState(goal, size);

        startState = new State(start);

        long startTime = System.currentTimeMillis();

        iterativeDeepeningAStar();
        System.out.println("Time: " + ((double) (System.currentTimeMillis() - startTime) / 1000) + " seconds");

        System.out.println(movesToGoal.size());
        for (String move : movesToGoal) {
            System.out.println(move);
        }
    }

    public static void iterativeDeepeningAStar() {
        int threshold = startState.getFScore();

        while (true) {
            int newThreshold = searchForGoal(startState, threshold);

            if (newThreshold == GOAL_STATE_REACHED) {
                return;
            }

            threshold = newThreshold;
        }
    }

    public static int searchForGoal(State state, int threshold) {
        int fScore = state.getFScore();

        if (fScore > threshold) {
            return fScore;
        }

        if (state.isGoal()) {
            getMovesFromGoalToStart(state);
            return GOAL_STATE_REACHED;
        }

        ArrayList<State> successors = new ArrayList<>();
        for (Direction direction : state.getPossibleMoves()) {
            successors.add(new State(state, direction));
        }

        int smallestHigherThreshold = Integer.MAX_VALUE;
        for (State successor : successors) {
            int successorFScore = searchForGoal(successor, threshold);

            if (successorFScore == GOAL_STATE_REACHED) {
                return GOAL_STATE_REACHED;
            }

            if (successorFScore < smallestHigherThreshold) {
                smallestHigherThreshold = successorFScore;
            }
        }

        return smallestHigherThreshold;
    }

    public static void getMovesFromGoalToStart(State state) {
        movesToGoal = new ArrayList<>();
        while (state.getParent() != null) {
            movesToGoal.add(state.getMoveTaken().toString());
            state = state.getParent();
        }

        Collections.reverse(movesToGoal);
    }
}
