package solution;

public class GoalState {
    private final int size;
    public static Coordinates[] goalCoordinates;
    public static int[][] distanceTable;

    public GoalState(int[][] goal, int size) {
        this.size = size;

        goalCoordinates = new Coordinates[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goalCoordinates[goal[i][j]] = new Coordinates(i, j);
            }
        }

      setDistanceTable();

    }

    public void setDistanceTable() {
        distanceTable = new int[size * size][size * size];

        for (int blockValue = 1; blockValue < distanceTable.length; blockValue++) {
            for (int currentPosition = 0; currentPosition < distanceTable[0].length; currentPosition++) {

                int correctRow = goalCoordinates[blockValue].getRow();
                int correctColumn = goalCoordinates[blockValue].getColumn();

                int currentRow = currentPosition / size;
                int currentColumn = currentPosition % size;

                distanceTable[blockValue][currentPosition] = Math.abs(correctRow - currentRow) + Math.abs(correctColumn - currentColumn);
            }
        }
    }

    public void printTable() {
        for (int[] ints : distanceTable) {
            for (int j = 0; j < distanceTable[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}
