package solution;

import java.util.ArrayList;

public class State {
    private final Direction direction;
    private ArrayList<Direction> possibleMoves;
    private final State parent;
    private final int currentCost;
    private final int size;
    private int[][] blocks;
    private Coordinates zeroPosition;
    private int manhattanDistance;

    public State(int[][] state) {
        direction = null;
        parent = null;
        blocks = state;
        size = state.length;
        currentCost = 0;

        setZeroPosition();
        setManhattanDistance();
        setInitialMoves();
    }

    private void setZeroPosition() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0) {
                    zeroPosition = new Coordinates(i, j);
                    return;
                }
            }
        }
    }

    private void setManhattanDistance() {
        manhattanDistance = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                manhattanDistance += GoalState.distanceTable[blocks[i][j]][i * blocks.length + j];
            }
        }
    }

    private void setInitialMoves() {
        possibleMoves = new ArrayList<>();
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        if (column != size - 1) {
            possibleMoves.add(Direction.LEFT);
        }
        if (column != 0) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (row != size - 1) {
            possibleMoves.add(Direction.UP);
        }
        if (row != 0) {
            possibleMoves.add(Direction.DOWN);
        }
    }

    public State(State parent, Direction direction) {
        this.direction = direction;
        this.parent = parent;
        size = parent.size;
        currentCost = parent.currentCost + 1;
        zeroPosition = parent.zeroPosition;
        manhattanDistance = parent.manhattanDistance;

        setBlocksBeforeMove();
        updateBoard();
        setPossibleMoves();
    }

    private void setBlocksBeforeMove() {
        blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(parent.blocks[i], 0, blocks[i], 0, size);
        }
    }

    private void updateBoard() {
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        switch (direction) {
            case LEFT: {
                updateManhattanDistance(row, column, 0, +1);
                moveBy(row, column, 0, +1);
                updateZeroPosition(row, column + 1);
                break;
            }
            case RIGHT: {
                updateManhattanDistance(row, column, 0, -1);
                moveBy(row, column, 0, -1);
                updateZeroPosition(row, column - 1);
                break;
            }
            case UP: {
                updateManhattanDistance(row, column, +1, 0);
                moveBy(row, column, +1, 0);
                updateZeroPosition(row + 1, column);
                break;
            }
            case DOWN: {
                updateManhattanDistance(row, column, -1, 0);
                moveBy(row, column, -1, 0);
                updateZeroPosition(row - 1, column);
            }
        }
    }

    private void moveBy(int zeroRow, int zeroColumn, int moveRowBy, int moveColumnBy) {
        blocks[zeroRow][zeroColumn] = blocks[zeroRow + moveRowBy][zeroColumn + moveColumnBy];
        blocks[zeroRow + moveRowBy][zeroColumn + moveColumnBy] = 0;
    }

    private void updateManhattanDistance(int zeroRow, int zeroColumn, int moveRowBy, int moveColumnBy) {
        int blockValue = blocks[zeroRow + moveRowBy][zeroColumn + moveColumnBy];

        int oldPosition = (zeroRow + moveRowBy) * size + zeroColumn + moveColumnBy;
        manhattanDistance -= GoalState.distanceTable[blockValue][oldPosition];
        int newPosition = zeroRow * size + zeroColumn;
        manhattanDistance += GoalState.distanceTable[blockValue][newPosition];
    }

    private void updateZeroPosition(int i, int j) {
        zeroPosition = new Coordinates(i, j);
    }

    public ArrayList<Direction> getPossibleMoves() {
        return possibleMoves;
    }

    private void setPossibleMoves() {
        possibleMoves = new ArrayList<>();
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        if (row != size - 1 && direction != Direction.DOWN) {
            possibleMoves.add(Direction.UP);
        }
        if (column != 0 && direction != Direction.LEFT) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (column != size - 1 && direction != Direction.RIGHT) {
            possibleMoves.add(Direction.LEFT);
        }
        if (row != 0 && direction != Direction.UP) {
            possibleMoves.add(Direction.DOWN);
        }
    }

    public int getFScore() {
        return currentCost + manhattanDistance;
    }

    public State getParent() {
        return parent;
    }

    public Direction getMoveTaken() {
        return direction;
    }

    public boolean isGoal() {
        return manhattanDistance == 0;
    }
}
