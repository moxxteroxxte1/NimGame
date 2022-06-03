package de.thm.mni.pis.vw6.nimperfect;

import java.util.Arrays;
import java.util.Random;

public class Nim_PerfectPerfect implements NimGame_Perfect {
    private final Random r = new Random();
    public int[] rows;

    private Nim_PerfectPerfect(int... rows) {
        assert rows.length >= 1;
        assert Arrays.stream(rows).allMatch(n -> n >= 0);
        this.rows = Arrays.copyOf(rows, rows.length);
    }

    public static Nim_PerfectPerfect of(int... rows) {
        return new Nim_PerfectPerfect(rows);
    }

    private Nim_PerfectPerfect play(Move_Perfect m) {
        assert !isGameOver();
        assert m.row < rows.length && m.number <= rows[m.row];
        Nim_PerfectPerfect nim = Nim_PerfectPerfect.of(rows);
        nim.rows[m.row] -= m.number;
        return nim;
    }

    public Nim_PerfectPerfect play(Move_Perfect... moves) {
        Nim_PerfectPerfect nim = this;
        for (Move_Perfect m : moves) nim = play(m);
        return nim;
    }

    public Move_Perfect randomMove() {
        assert !isGameOver();
        int row;
        do {
            row = r.nextInt(rows.length);
        } while (rows[row] == 0);
        int number = r.nextInt(rows[row]) + 1;
        return Move_Perfect.of(row, number);
    }

    public Move_Perfect bestMove() {
        assert !isGameOver();
        if (!NimGame_Perfect.isWinning(rows)) return randomMove();
        Move_Perfect m;
        do {
            m = randomMove();
        } while (NimGame_Perfect.isWinning(play(m).rows));
        return m;
    }

    public boolean isGameOver() {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }

    public String toString() {
        String s = "";
        for (int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }

    boolean isGameOver(int[] rows) {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }
}
