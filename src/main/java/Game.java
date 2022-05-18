import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.StreamSupport;

public interface Game<M extends MoveInterface> {

    default M getBestMove(int depth) {
        return getEvaluatedMoves(depth).stream().findFirst().get();
    }

    default List<M> getEvaluatedMoves(int depth) {
        return getEvaluatedMoves(depth, getAllPossibleMoves());
    }

    default List<M> getEvaluatedMoves(int depth, Iterable<M> moves) {
        if (depth <= 0) throw new IllegalArgumentException("SEARCHING DEPTH MUST BE GREATER THAN 0.");
        minimax(moves, depth);
        return StreamSupport.stream(moves.spliterator(), false).sorted().toList();
    }

    default int minimax(@NotNull Iterable<M> moves, @NotNull int depth) {
        if (isGameOver() || depth <= 0) return evaluate();
        int score = isMaxPlayer() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(M move : moves) {
            play(move);
            score = isMaxPlayer() ? Math.max(minimax(getAllPossibleMoves(), depth--), score) : Math.min(minimax(getAllPossibleMoves(), depth), score);
            undo(move);
            move.setScore(score);
        }
        return score;
    }

    Iterable<M> getAllPossibleMoves();

    boolean isGameOver();

    int evaluate();

    void play(M move);

    void undo(M move);

    boolean isMaxPlayer();
}
