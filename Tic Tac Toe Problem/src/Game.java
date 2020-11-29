import java.util.Scanner;

public class Game {
    private static final int SIZE = 3;
    private final Board board;
    private final Scanner scanner;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
    }

    public void playGamePlayerGoesFirst() {
        System.out.println("Start of game: ");

        while (true) {
            board.printBoard();
            playMovePlayerGoesFirst();

            if (board.gameIsOver()) {
                printWinner();
                break;
            }
        }
    }

    private void playMovePlayerGoesFirst() {
        if (board.getTurn() == CellState.X) {
            getPlayerMove();
        } else {
            System.out.println("AI move:");
            AlphaBetaPruning.alphaBetaPruning(board.getTurn(), board,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    public void playGameAIGoesFirst() {
        throw new UnsupportedOperationException("TBD");
    }

    private void playMoveAIGoesFirst() {
        throw new UnsupportedOperationException("TBD");
    }

    private void getPlayerMove() {
        while (true) {
            System.out.println("Choose your move by entering a row and column (1-3):");
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            if (1 <= row && row <= SIZE && 1 <= column && column <= SIZE) {
                if (board.move(row - 1, column - 1)) {
                    break;
                } else {
                    System.out.println("The selected cell must be blank.");
                }
            } else {
                System.out.println("The selected cell must be valid (1-3).");
            }
        }
    }

    private void printWinner() {
        System.out.println("Final result:");
        board.printBoard();

        CellState winner = board.getWinner();
        if (winner == CellState.BLANK) {
            System.out.println("The game is a draw.");
        } else if (winner == CellState.X) {
            System.out.println("The player wins!");
        } else {
            System.out.println("The AI wins!");
        }
    }
}
