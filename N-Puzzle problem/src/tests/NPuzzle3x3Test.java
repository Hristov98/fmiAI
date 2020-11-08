package tests;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import solution.GoalState;
import solution.ProblemSolution;
import solution.State;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NPuzzle3x3Test {
    public int[][] goalState = new int[][]{
            {1, 2, 3}, {4, 5, 6}, {7, 8, 0}
    };

    @Before
    public void setup() {
        int size = 3;
        GoalState goal = new GoalState(goalState, size);
    }

    @Test
    public void test1() {
        setup();
        int[][] initialState = new int[][]{
                {1, 2, 3}, {4, 5, 6}, {0, 7, 8}
        };

        ProblemSolution.startState = new State(initialState);

        final int steps = 2;
        final String moves = "LL";

        ProblemSolution.iterativeDeepeningAStar();
        Collections.reverse(ProblemSolution.movesToGoal);

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test2() {
        setup();
        int[][] initialState = buildBoardFromString("123745086");

        ProblemSolution.startState = new State(initialState);

        final int steps = 4;
        final String moves = "DLLU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test3() {
        setup();
        int[][] initialState = buildBoardFromString("123480765");

        ProblemSolution.startState = new State(initialState);

        final int steps = 5;
        final String moves = "URDLU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test4() {
        setup();
        int[][] initialState = buildBoardFromString("413726580");

        ProblemSolution.startState = new State(initialState);

        final int steps = 8;
        final String moves = "RRDDLUUL";


        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test5() {
        setup();
        int[][] initialState = buildBoardFromString("162530478");

        ProblemSolution.startState = new State(initialState);

        final int steps = 9;
        final String moves = "RDLURRULL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test6() {
        setup();
        int[][] initialState = buildBoardFromString("512630478");

        ProblemSolution.startState = new State(initialState);

        final int steps = 11;
        final String moves = "RRDLLURRULL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test7() {
        setup();
        int[][] initialState = buildBoardFromString("126350478");

        ProblemSolution.startState = new State(initialState);

        final int steps = 13;
        final String moves = "DRURULLDRDLUU";
        final String moves2 = "RRDLULDRRUULL";


        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves2, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test8() {
        setup();
        int[][] initialState = buildBoardFromString("436871052");

        ProblemSolution.startState = new State(initialState);

        final int steps = 18;
        final String moves = "DLLDRUULDRURDDLULU";


        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test9() {
        setup();
        int[][] initialState = buildBoardFromString("503284671");

        ProblemSolution.startState = new State(initialState);

        final int steps = 23;
        final String moves = "RUULLDRRULLDRRULDDRUULL";


        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test10() {
        setup();
        int[][] initialState = buildBoardFromString("874320651");

        ProblemSolution.startState = new State(initialState);

        final int steps = 25;
        final String moves = "URDRDLLURURDDLLUURRDDLULU";
        final String moves2 = "URDRDLULDRUURDDLULURRDLLU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test11() {
        setup();
        int[][] initialState = buildBoardFromString("876543021");

        ProblemSolution.startState = new State(initialState);

        final int steps = 28;
        final String moves1 = "DDLURULDLURRDDLULDRUURDDLUUL";
        final String moves = "DDLULURRDDLULDRUULDRURDDLULU";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves1, getMovesShortened(ProblemSolution.movesToGoal));
    }

    @Test
    public void test12() {
        // has 2 solutions
        setup();
        int[][] initialState = buildBoardFromString("876543210");

        ProblemSolution.startState = new State(initialState);

        final int steps = 30;
        final String moves = "DRDRUULDDRUULDDLUURDDLURDRULUL";
        final String moves2 = "RRDDLURULDLURRDDLULDRUURDDLUUL";

        ProblemSolution.iterativeDeepeningAStar();

        assertEquals(steps, ProblemSolution.movesToGoal.size());
        assertEquals(moves2, getMovesShortened(ProblemSolution.movesToGoal));
    }

    private int[][] buildBoardFromString(String boardAsString) {
        int length = (int) Math.sqrt(boardAsString.length());
        int[][] board = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = Character.getNumericValue(boardAsString.charAt(i * length + j));
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