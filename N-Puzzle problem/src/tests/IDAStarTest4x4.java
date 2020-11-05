package tests;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import solution.GoalState;
import solution.ProblemSolution;
import solution.State;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IDAStarTest4x4 {
    public int[][] goalState = new int[][]{
            {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}
    };

    @Before
    public void setup() {
        int size = 4;
        GoalState goal = new GoalState(goalState, size);
    }

    @Test
    public void test13() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 8, 13, 14, 15, 12});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 2;
        final String moves = "UU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test14() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 9, 6, 11, 12, 13, 10, 14, 15});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 4;
        final String moves = "UULL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test15() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 6, 2, 4, 5, 0, 3, 8, 9, 10, 7, 11, 13, 14, 15, 12});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 6;
        final String moves = "DLUULU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test16() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 4, 12, 5, 6, 3, 0, 9, 10, 8, 7, 13, 14, 11, 15});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 10;
        final String moves = "DRUULDRUUL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test17() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 8, 3, 5, 11, 6, 4, 0, 10, 7, 12, 9, 13, 14, 15});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 14;
        final String moves = "ULDDLDLURURULL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test18() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 3, 4, 1, 7, 11, 8, 9, 6, 0, 12, 13, 14, 15, 10});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 18;
        final String moves = "DLUURDLDRRDRULULUL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
      //  assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    @Test
    public void test19() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 4, 8, 3, 7, 2, 10, 11, 5, 6, 0, 15, 9, 13, 14, 12});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 22;
        final String moves = "LDDRRURUULLDDLDRURULLU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }


    @Test
    public void test20() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 4, 7, 9, 1, 3, 8, 11, 10, 0, 6, 14, 13, 15, 12});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 28;
        final String moves = "LDDRUURRULDRDLDRULLLUURRDLUL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test21() {
        int[][] initialState = buildBoardFromArray(new int[]{9, 5, 8, 3, 6, 0, 10, 11, 2, 1, 14, 7, 13, 15, 12, 4});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 34;
        final String moves = "ULDLUURRDRDDLUURDDLUULDLURDDLURULU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
      //  assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    @Test
    public void test22() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 6, 4, 8, 1, 10, 7, 3, 5, 13, 11, 15, 12, 14, 9, 0});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 38;
        final String moves = "DDRRDRUULULDRURDLLDLUURRDLULDRDLDRUUUL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
      //  assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    @Test
    public void test23() {
        int[][] initialState = buildBoardFromArray(new int[]{14, 5, 11, 6, 9, 0, 4, 3, 15, 1, 7, 12, 8, 2, 13, 10});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 44;
        final String moves = "UULLDRRRULDRDDLURDLLLURURDDLURDLLUURURDLDLUU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
      //  assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    @Test
    public void test24() {
        int[][] initialState = buildBoardFromArray(new int[]{13, 11, 9, 3, 14, 7, 1, 4, 0, 5, 10, 12, 15, 2, 6, 8});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 46;
        final String moves = "DDLUUURDDLLDRUULURRDDDLULULURRDDRULDLDLUURRULL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
      //  assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    @Test
    public void test25() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 9, 3, 5,
                8, 11, 12, 7, 15, 4, 0, 13, 6, 1, 10, 14});

        setup();
        ProblemSolution.startState = new State(initialState);

        final int steps = 50;
        final String moves = "LDRRUULDLDRRRULULLDDRRRUULDDDLLURDRRULLURRDLUULDLU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
       // assertEquals(moves, getMovesShortened(solution.ProblemSolution.movesToGoal));
    }

    private int[][] buildBoardFromArray(int[] arr) {
        int length = (int) Math.sqrt(arr.length);
        int[][] board = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = arr[i * length + j];
            }
        }

        return board;
    }

    public static String getMovesShortened(ArrayList<String> movesToGoal) {
        StringBuilder moves = new StringBuilder();

        for (String move : movesToGoal) {
            switch (move) {
                case "left": {
                    moves.append("L");
                    break;
                }
                case "right": {
                    moves.append("R");
                    break;
                }
                case "up": {
                    moves.append("U");
                    break;
                }
                case "down": {
                    moves.append("D");
                    break;
                }
            }
        }

        return moves.toString();
    }
}