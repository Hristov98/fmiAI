package old;

public class Action {
    private final char symbol;
    private final int row;
    private final int column;

    public Action(char symbol, int row, int column) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "old.Action{" +
                "symbol=" + symbol +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
