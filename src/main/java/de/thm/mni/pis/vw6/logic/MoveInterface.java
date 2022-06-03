package de.thm.mni.pis.vw6.logic;

import org.jetbrains.annotations.NotNull;

import static java.lang.Float.isNaN;

public interface MoveInterface extends Comparable<Move> {

    float getScore();
    void setScore(float score);

    default <T extends MoveInterface> int compareTo(@NotNull T other) {
        if (isNaN(getScore()) && isNaN(other.getScore())) {
            return 0;
        } else if (isNaN(other.getScore())) {
            return 1;
        } else if (isNaN(getScore())) {
            return -1;
        }
        return (int) Math.signum(other.getScore() - getScore());
    }
}
