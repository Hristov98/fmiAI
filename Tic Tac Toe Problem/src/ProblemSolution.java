class ProblemSolution {
    private static final int MAX_DEPTH = 10;

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
        Move bestMove = null;

        for (Move move : board.getAvailableMoves()) {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move.getRow(), move.getColumn());

            int score = alphaBetaPruning(currentPlayer, modifiedBoard, alpha, beta, depth);

            if (score > alpha) {
                alpha = score;
                bestMove = move;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != null) {
            board.move(bestMove.getRow(), bestMove.getColumn());
        }
        return alpha;
    }

    private static int minValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        Move bestMove = null;

        for ( Move move  : board.getAvailableMoves()) {
            Board modifiedBoard = new Board(board);
            modifiedBoard.move(move.getRow(), move.getColumn());

            int score = alphaBetaPruning(currentPlayer, modifiedBoard, alpha, beta, depth);

            if (score < beta) {
                beta = score;
                bestMove = move;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != null) {
            board.move(bestMove.getRow(), bestMove.getColumn());
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
