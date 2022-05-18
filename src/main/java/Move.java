import org.jetbrains.annotations.NotNull;

public class Move implements MoveInterface {

    protected float score;
    int row, number;

    Move(int row, int number) {
        if (row < 0 || number < 1) throw new IllegalArgumentException();
        this.row = row;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getRow() {
        return row;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int compareTo(@NotNull Move o) {
        return MoveInterface.super.compareTo(MoveInterface.class.cast(o));
    }

    @Override
    public String toString() {
        return "Move{" + "score=" + score + ", row=" + row + ", number=" + number + '}';
    }
}
