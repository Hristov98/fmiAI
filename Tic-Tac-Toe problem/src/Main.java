import java.util.Scanner;

public class Main {
    private static final int SIZE = 3;
    private static final int PLAYER_VICTORY = 1;
    private static final int DRAW = 0;
    private static final int AI_VICTORY = -1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose who goes first by entering the specific number:");
        System.out.println("1) Player");
        System.out.println("2) AI");

        char[][] board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '_';
            }
        }

        int choice = scanner.nextInt();
        boolean isPlayerTurn = choice == 1;
        while (!boardIsFilled(board)) {
            if (evaluateBoard(board) != DRAW) {
                break;
            }

            printBoard(board);

            if (isPlayerTurn) {
                while (true) {
                    System.out.println("Choose your move by entering a row and column (1-3):");
                    int row = scanner.nextInt();
                    int column = scanner.nextInt();

                    if (1 <= row && row <= SIZE && 1 <= column && column <= SIZE
                            && board[row - 1][column - 1] == '_') {
                        board[row - 1][column - 1] = 'X';
                        break;
                    }
                }
            } else {
                Move bestMove = findBestMove(board);
                board[bestMove.getRow()][bestMove.getColumn()] = 'O';
            }

            isPlayerTurn = !isPlayerTurn;
        }

        System.out.println("The final result is: ");
        printBoard(board);
        int result = evaluateBoard(board);
        if (result == PLAYER_VICTORY) {
            System.out.println("The player wins the game!");
        } else if (result == AI_VICTORY) {
            System.out.println("The AI wins the game!");
        } else {
            System.out.println("The game is a draw!");
        }
    }

    private static Move findBestMove(char[][] board) {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'O';
                    int moveVal = maxValue(board, 0);
                    System.out.println(moveVal);
                    printBoard(board);
                    board[i][j] = '_';



                    if (moveVal > bestVal) {
                        bestMove = new Move(i, j);
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    public static int maxValue(char[][] board, int depth) {
        int evaluation = evaluateBoard(board);
        if (evaluation == AI_VICTORY) {
            return depth - 10;
        }
        if (boardIsFilled(board) && evaluation == DRAW) {
            return 0;
        }

        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'O';
                  //  printBoard(board);
                    maxValue = Math.max(maxValue, minValue(board, depth + 1));
                    board[i][j] = '_';
                 //   System.out.println(maxValue);
                }
            }
        }

        return maxValue;
    }


    public static int minValue(char[][] board, int depth) {
        int evaluation = evaluateBoard(board);
        if (evaluation == PLAYER_VICTORY) {
            return 10 - depth;
        }
        if (boardIsFilled(board) && evaluation == DRAW) {
            return 0;
        }

        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'X';
                    minValue = Math.min(minValue, maxValue(board, depth + 1));
                    board[i][j] = '_';
                }
            }
        }

        return minValue;
    }

    private static int evaluateBoard(char[][] board) {
        if (threeInARowFound(board, 'O')) {
            return AI_VICTORY;
        }
        if (threeInARowFound(board, 'X')) {
            return PLAYER_VICTORY;
        }

        return DRAW;
    }

    private static boolean boardIsFilled(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean threeInARowFound(char[][] board, char symbol) {
        //rows
        if (board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) {
            return true;
        }
        if (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) {
            return true;
        }
        if (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) {
            return true;
        }

        //columns
        if (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) {
            return true;
        }
        if (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) {
            return true;
        }

        //main diagonal
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        //secondary diagonal
        return board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol;
    }


    public static void printBoard(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
