package old;

import java.util.ArrayList;
import java.util.List;

public class State {
    private static final int SIZE = 3;
    private final Action action;
    private final boolean isPlayerTurn;
    private char[][] board;

    public State() {
        action = new Action('_',0,0);
        isPlayerTurn = true;
        initialiseBoard();
    }

    private void initialiseBoard() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '_';
            }
        }
    }

    public Action getAction() {
        return action;
    }

    public char[][] getBoard() {
        return board;
    }

    public State(Action parentAction, char[][] board) {
        this.action = parentAction;
        isPlayerTurn = parentAction.getSymbol() == 'O';
        copyBoardFromParent(board);
        executeAction();
    }

    private void copyBoardFromParent(char[][] board) {
        this.board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, SIZE);
        }
    }

    private void executeAction() {
        board[action.getRow()][action.getColumn()] = action.getSymbol();
    }

    public List<State> getSuccessors() {
        List<State> successors = new ArrayList<>();

        if (isPlayerTurn) {
            successors.addAll(getAllPossibleMovesInTurnForPlayer('X'));
        } else {
            successors.addAll(getAllPossibleMovesInTurnForPlayer('O'));
        }

        return successors;
    }

    private List<State> getAllPossibleMovesInTurnForPlayer(char symbol) {
        List<State> successors = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                     Action newAction = new Action(symbol, i, j);
                    successors.add(new State(newAction, board));
                }
            }
        }

        return successors;
    }

    public boolean isTerminalState() {
        if (boardIsFilled()) {
            return true;
        } else if (isPlayerTurn) {
            return threeInARowFound('O');
        } else {
            return threeInARowFound('X');
        }
    }

    public int evaluateTerminalState() {
        if (isPlayerTurn) {
            if (threeInARowFound('O')) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (threeInARowFound('X')) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private boolean boardIsFilled() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean threeInARowFound(char symbol) {
        if (board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) {
            return true;
        }
        if (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) {
            return true;
        }
        if (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) {
            return true;
        }
        if (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        return board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol;
    }

    public void printState() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
