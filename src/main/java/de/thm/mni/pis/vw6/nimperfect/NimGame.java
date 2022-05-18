package de.thm.mni.pis.vw6.nimperfect;

import java.util.Arrays;

public interface NimGame {
    static boolean isWinning(int... numbers) {
        return Arrays.stream(numbers).reduce(0, (i, j) -> i ^ j) != 0;
        // klassische Variante:
        // int res = 0;
        // for(int number : numbers) res ^= number;
        // return res != 0;
    }

    NimGame play(Move_Perfect... moves);

    Move_Perfect randomMove();

    Move_Perfect bestMove();

    boolean isGameOver();

    String toString();
}