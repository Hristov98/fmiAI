import java.util.Collection;
import java.util.HashMap;

public class Board {
    private static final int SIZE = 3;
    private final CellState[][] board;
    private CellState currentPlayer;
    private CellState winner;
    private HashMap<Integer, Move> movesAvailable;
    private int moveCount;
    private boolean gameIsOver;

    public Board() {
        board = new CellState[SIZE][SIZE];
        movesAvailable = new HashMap<>();
        moveCount = 0;
        gameIsOver = false;
        currentPlayer = CellState.X;
        winner = CellState.BLANK;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = CellState.BLANK;
            }
        }

        movesAvailable.clear();
        for (int cellIndex = 0; cellIndex < SIZE * SIZE; cellIndex++) {
            movesAvailable.put(cellIndex, new Move(cellIndex / SIZE, cellIndex % SIZE));
        }
    }

    public Board(Board boardToCopy) {
        this.board = new CellState[SIZE][SIZE];
        for (int i = 0; i < boardToCopy.board.length; i++) {
            this.board[i] = boardToCopy.board[i].clone();
        }

        this.currentPlayer = boardToCopy.currentPlayer;
        this.winner = boardToCopy.winner;
        this.movesAvailable = new HashMap<>(boardToCopy.movesAvailable);
        this.moveCount = boardToCopy.moveCount;
        this.gameIsOver = boardToCopy.gameIsOver;
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    public CellState getTurn() {
        return currentPlayer;
    }

    public CellState getWinner() {
        if (!gameIsOver) {
            throw new IllegalStateException("The game is not over yet.");
        }
        return winner;
    }

    public Collection<Move> getAvailableMoves() {
        return movesAvailable.values();
    }

    public boolean move(int row, int column) {
        if (gameIsOver) {
            throw new IllegalStateException("Game is already over. No moves can be played.");
        }

        if (board[row][column] == CellState.BLANK) {
            board[row][column] = currentPlayer;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(row * SIZE + column);
        checkForTerminalState(row, column);
        currentPlayer = (currentPlayer == CellState.X) ? CellState.O : CellState.X;

        return true;
    }

    private void checkForTerminalState(int row, int column) {
        if (moveCount == SIZE * SIZE) {
            winner = CellState.BLANK;
            gameIsOver = true;
        }

        checkRowForWinner(row);
        checkColumnForWinner(column);
        checkMainDiagonalForWinner(row, column);
        checkSecondaryDiagonalForWinner(row, column);
    }

    private void checkRowForWinner(int row) {
        for (int column = 1; column < SIZE; column++) {
            if (board[row][column] != board[row][column - 1]) {
                return;
            }
            if (column == SIZE - 1) {
                winner = currentPlayer;
                gameIsOver = true;
            }
        }
    }

    private void checkColumnForWinner(int column) {
        for (int row = 1; row < SIZE; row++) {
            if (board[row][column] != board[row - 1][column]) {
                break;
            }

            if (row == SIZE - 1) {
                winner = currentPlayer;
                gameIsOver = true;
            }
        }
    }

    private void checkMainDiagonalForWinner(int row, int column) {
        if (row == column) {
            for (int i = 1; i < SIZE; i++) {
                if (board[i][i] != board[i - 1][i - 1]) {
                    break;
                }
                if (i == SIZE - 1) {
                    winner = currentPlayer;
                    gameIsOver = true;
                }
            }
        }
    }

    private void checkSecondaryDiagonalForWinner(int row, int column) {
        if (SIZE - 1 - column == row) {
            for (int i = 1; i < SIZE; i++) {
                if (board[SIZE - 1 - i][i] != board[SIZE - i][i - 1]) {
                    break;
                }
                if (i == SIZE - 1) {
                    winner = currentPlayer;
                    gameIsOver = true;
                }
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (board[row][column] == CellState.BLANK) {
                    System.out.print("_ ");
                } else if (board[row][column] == CellState.X) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
