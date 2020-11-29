class AlphaBetaPruning {
    private static final int MAX_DEPTH = 10;

    private AlphaBetaPruning() {
    }

    /**
     * The meat of the algorithm.
     *
     * @param player the player that the AI will identify as
     * @param board  the Tic Tac Toe board to play on
     * @param alpha  the alpha value
     * @param beta   the beta value
     * @param depth  the current depth
     * @return the score of the board
     */
    public static int alphaBetaPruning(CellState player, Board board, int alpha, int beta, int depth) {
        if (depth++ == MAX_DEPTH || board.gameIsOver()) {
            return evaluateMove(player, board);
        }

        if (board.getTurn() == player) {
            return maxValue(player, board, alpha, beta, depth);
        } else {
            return minValue(player, board, alpha, beta, depth);
        }
    }

    private static int maxValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = new Board(board);
            int row = theMove / 3;
            int column = theMove % 3;
            modifiedBoard.move(row, column);
            int score = alphaBetaPruning(currentPlayer, modifiedBoard, alpha, beta, depth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            int row = indexOfBestMove / 3;
            int column = indexOfBestMove % 3;
            board.move(row, column);
        }
        return alpha;
    }

    private static int minValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = new Board(board);
            int row = theMove / 3;
            int column = theMove % 3;
            modifiedBoard.move(row, column);

            int score = alphaBetaPruning(currentPlayer, modifiedBoard, alpha, beta, depth);

            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            int row = indexOfBestMove / 3;
            int column = indexOfBestMove % 3;
            board.move(row, column);
        }
        return beta;
    }

    private static int evaluateMove(CellState player, Board board) {
        if (player == CellState.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        CellState opponent = (player == CellState.X) ? CellState.O : CellState.X;

        if (board.gameIsOver() && board.getWinner() == player) {
            return 10;
        } else if (board.gameIsOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }
}
