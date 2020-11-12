import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProblemSolution {
    private final int NO_CONFLICTS_FOUND = -1;
    private int N;
    private int[] queens;
    private int[] queensOnRow;
    private int[] queensOnMainDiagonal;
    private int[] queensOnSecondaryDiagonal;

    ProblemSolution(int N) {
        this.N = N;
    }

    public void solveNQueens() {
        initialise(N);

        int iterator = 0;
        while (iterator++ <= 100) {
            int maxConflictColumn = getColumnWithQueenWithMaxConflicts();

            if (maxConflictColumn == NO_CONFLICTS_FOUND) {
                break;
            }

            int minConflictRow = getRowWithMinConflicts(maxConflictColumn);

            updateConflictTables(maxConflictColumn, -1);
            queens[maxConflictColumn] = minConflictRow;
            updateConflictTables(maxConflictColumn, +1);
        }

        if (hasConflicts()) {
            solveNQueens();
        }
    }

    private void initialise(int N) {
        queens = new int[N];
        queensOnRow = new int[N];
        queensOnMainDiagonal = new int[2 * N - 1];
        queensOnSecondaryDiagonal = new int[2 * N - 1];

        Random random = new Random();
        int randomStartingRow = random.nextInt(N);
        queens[0] = randomStartingRow;
        updateConflictTables(0, +1);

        for (int column = 1; column < N; column++) {
            int minimumRow = getRandomRowWithMinConflicts(column);

            queens[column] = minimumRow;
            updateConflictTables(column, +1);
        }
    }

    private int getRandomRowWithMinConflicts(int column) {
        int minimumConflicts = Integer.MAX_VALUE;

        for (int row = 0; row < N; row++) {
            int conflicts = getConflictsForCell(column, row);

            if (conflicts < minimumConflicts) {
                minimumConflicts = conflicts;
            }
        }

        List<Integer> minimums = new ArrayList<>();
        for (int row = 0; row < N; row++) {
            int conflicts = getConflictsForCell(column, row);

            if (conflicts == minimumConflicts) {
                minimums.add(row);
            }
        }

        Random random = new Random();
        return minimums.get(random.nextInt(minimums.size()));
    }

    private int getConflictsForCell(int column, int row) {
        int conflicts = 0;

        conflicts += queensOnRow[row];
        conflicts += queensOnMainDiagonal[N - 1 + column - row];
        conflicts += queensOnSecondaryDiagonal[column + row];

        return conflicts;
    }

    private int getColumnWithQueenWithMaxConflicts() {
        int maxConflicts = -1;
        int maxQueenColumn = 0;

        for (int column = 0; column < N; column++) {
            int conflicts = getConflictsForQueenOnColumn(column);

            if (conflicts > 0 && conflicts > maxConflicts) {
                maxConflicts = conflicts;
                maxQueenColumn = column;
            }
        }

        if (maxConflicts == -1) {
            return NO_CONFLICTS_FOUND;
        }

        return maxQueenColumn;
    }

    private int getConflictsForQueenOnColumn(int column) {
        int conflicts = 0;
        int row = queens[column];

        conflicts += queensOnRow[row] - 1;
        conflicts += queensOnMainDiagonal[N - 1 + column - row] - 1;
        conflicts += queensOnSecondaryDiagonal[column + row] - 1;

        return conflicts;
    }

    private int getRowWithMinConflicts(int column) {
        int minimumConflicts = Integer.MAX_VALUE;
        int minimumConflictRow = -1;

        for (int row = 0; row < N; row++) {
            int conflicts = getConflictsForCell(column, row);

            if (conflicts < minimumConflicts) {
                minimumConflicts = conflicts;
                minimumConflictRow = row;
            }
        }

        return minimumConflictRow;
    }

    private void updateConflictTables(int column, int updateBy) {
        queensOnRow[queens[column]] += updateBy;
        queensOnMainDiagonal[N - 1 + (column - queens[column])] += updateBy;
        queensOnSecondaryDiagonal[column + queens[column]] += updateBy;
    }

    private boolean hasConflicts() {
        for (int column = 0; column < N; column++) {
            int conflicts = getConflictsForQueenOnColumn(column);

            if (conflicts >= 1) {
                return true;
            }
        }

        return false;
    }

    public void printState() {
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (queens[column] == row) {
                    System.out.print("* ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    public void testSolution(int from, int to) {
        int size = from;

        long totalStartTime = System.currentTimeMillis();
        do {
            N = size;
            long localStartTime = System.currentTimeMillis();
            solveNQueens();
            double endTime = ((double) System.currentTimeMillis() - localStartTime) / 1000;
            System.out.println("Time to solve for " + size + " queens: " + endTime);

            size++;
        } while (size <= to);

        double totalTestTime = ((double) System.currentTimeMillis() - totalStartTime) / 1000;
        System.out.printf("Time to execute all tests from %d to %d: %f", from, to, totalTestTime);
    }
}
