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
        System.out.println("Starting a new game.");

        while (true) {
            printGameStatus();
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
            AlphaBetaPruning.run(board.getTurn(), board, 10);
        }
    }

    private void printGameStatus() {
        System.out.println("\n" + board + "\n");
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
        CellState winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == CellState.BLANK) {
            System.out.println("The game is a draw.");
        } else if (winner == CellState.X) {
            System.out.println("The player wins!");
        } else {
            System.out.println("The AI wins!");
        }
    }
}
