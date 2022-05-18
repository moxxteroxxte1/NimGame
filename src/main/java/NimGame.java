import java.util.*;

public class NimGame implements Game<Move> {

    int[] rows;
    boolean isMaxPlayer = true;

    NimGame(int... rows) {
        if (rows.length < 1 || !Arrays.stream(rows).allMatch(n -> n >= 0))
            throw new IllegalArgumentException("ROWS MUST BE A PARAMETER CORRESPONDING TO AN INTEGER ARRAY OF LENGTH GREATER THAN OR EQUAL TO 1 WHOSE VALUES ARE ALL GREATER THAN OR EQUAL TO 0.");
        this.rows = Arrays.copyOf(rows, rows.length);
    }

    @Override
    public Iterable getAllPossibleMoves() {
        List<Move> allPossibleMoves = new ArrayList<>();
        int index = 0;
        for (int row : rows){
            for(int i = 1; i <= row; i++){
                allPossibleMoves.add(new Move(index, i));
            }
            index++;
        }
        return allPossibleMoves;
    }

    @Override
    public boolean isGameOver() {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }

    @Override
    public int evaluate() {
        return (isGameOver() ? (isMaxPlayer ? -2 : 2) : (isWinning() ? (isMaxPlayer ? 1 : -1) : 0));
    }

    @Override
    public void play(Move move) {
        if (move.getRow() >= rows.length || move.getNumber() > rows[move.getRow()])
            throw new IllegalArgumentException("ARGUMENTS OUT OF BOUNDS");

        isMaxPlayer = !isMaxPlayer;
        rows[move.getRow()] -= move.getNumber();
    }

    @Override
    public void undo(Move move) {
        if (move.getRow() > rows.length)
            throw new IllegalArgumentException("ARGUMENTS OUT OF BOUNDS");

        isMaxPlayer = !isMaxPlayer;
        rows[move.getRow()] += move.getNumber();
    }

    @Override
    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    boolean isWinning() {
        return Arrays.stream(rows).reduce(0, (i, j) -> i ^ j) != 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }
}
