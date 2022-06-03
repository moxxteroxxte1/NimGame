package de.thm.mni.pis.vw6.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NimGame implements Game<Move> {

    private int[] rows;

    public NimGame(int... rows) {
        if (rows.length < 1 || !Arrays.stream(rows).allMatch(n -> n >= 0))
            throw new IllegalArgumentException("ROWS MUST BE A PARAMETER CORRESPONDING TO AN INTEGER ARRAY OF LENGTH GREATER THAN OR EQUAL TO 1 WHOSE VALUES ARE ALL GREATER THAN OR EQUAL TO 0.");
        this.rows = Arrays.copyOf(rows, rows.length);
    }

    public NimGame() {
        this.rows = randomGame();
    }

    @Override
    public Iterable getAllPossibleMoves() {
        List<Move> allPossibleMoves = new ArrayList<>();
        int index = 0;
        for (int row : rows) {
            for (int i = 1; i <= row; i++) {
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
        return (isGameOver() ? -2 : (isWinning() ? 1 : 0));
    }

    @Override
    public void play(Move move) {
        if (move.getRow() >= rows.length || move.getNumber() > rows[move.getRow()])
            throw new IllegalArgumentException("ARGUMENTS OUT OF BOUNDS");

        rows[move.getRow()] -= move.getNumber();
    }

    @Override
    public void undo(Move move) {
        if (move.getRow() > rows.length)
            throw new IllegalArgumentException("ARGUMENTS OUT OF BOUNDS");

        rows[move.getRow()] += move.getNumber();
    }

    public boolean isWinning() {
        return Arrays.stream(rows).reduce(0, (i, j) -> i ^ j) != 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }

    public int[] getRows() {
        return Arrays.copyOf(rows, rows.length);
    }

    private int[] randomGame() {
        //RANDOM ROW COUNT FROM 2 TO 4
        int rowsCount = (2 + new Random().nextInt(2));
        int[] rows = new int[rowsCount];
        //FILL EACH ROW WITH RANDOM NUMBER BETWEEN 1 AND 5
        for (int i = 0; i < rowsCount; i++) {
            rows[i] = (1 + new Random().nextInt(4));
        }
        return rows;
    }
}
