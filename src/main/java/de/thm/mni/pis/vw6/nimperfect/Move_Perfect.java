package de.thm.mni.pis.vw6.nimperfect;

public class Move_Perfect {
    public final int row, number;

    private Move_Perfect(int row, int number) {
        if (row < 0 || number < 1) throw new IllegalArgumentException();
        this.row = row;
        this.number = number;
    }

    public static Move_Perfect of(int row, int number) {
        return new Move_Perfect(row, number);
    }

    public String toString() {
        return "(" + row + ", " + number + ")";
    }

}
