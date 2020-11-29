import java.util.HashSet;

public class Board {
    private static final int SIZE = 3;
    private final CellState[][] board;
    private CellState currentPlayer;
    private CellState winner;
    private HashSet<Integer> movesAvailable;
    private int moveCount;
    private boolean gameIsOver;

    public Board() {
        board = new CellState[SIZE][SIZE];
        movesAvailable = new HashSet<>();
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

        for (int i = 0; i < SIZE * SIZE; i++) {
            movesAvailable.add(i);
        }
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

    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    /**
     * Get a deep copy of the Tic Tac Toe board.
     *
     * @return an identical copy of the board
     */
    public Board getDeepCopy() {
        Board board = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.currentPlayer = this.currentPlayer;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount = this.moveCount;
        board.gameIsOver = this.gameIsOver;
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {

                if (board[y][x] == CellState.BLANK) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != SIZE - 1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}
