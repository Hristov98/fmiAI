class ProblemSolution {
    public static int alphaBetaPruning(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        if (board.getTurn() == currentPlayer) {
            return maxValue(currentPlayer, board, alpha, beta, depth);
        } else {
            return minValue(currentPlayer, board, alpha, beta, depth);
        }
    }

    private static int maxValue(CellState currentPlayer, Board board, int alpha, int beta, int depth) {
        if (board.gameIsOver()) {
            return evaluateMove(currentPlayer, board, depth);
        }

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
        if (board.gameIsOver()) {
            return evaluateMove(currentPlayer, board, depth);
        }


        Move bestMove = null;
        for (Move move : board.getAvailableMoves()) {
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

    private static int evaluateMove(CellState player, Board board, int depth) {
        if (player == CellState.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        CellState opponent = (player == CellState.X) ? CellState.O : CellState.X;

        if (board.gameIsOver() && board.getWinner() == player) {
            return 10 - depth;
        } else if (board.gameIsOver() && board.getWinner() == opponent) {
            return -10 + depth;
        } else {
            return 0;
        }
    }
}
