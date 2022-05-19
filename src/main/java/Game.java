import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.StreamSupport;

public interface Game<M extends MoveInterface> {

    default M getBestMove(int depth, boolean max) {
        return getEvaluatedMoves(depth, max).stream().findFirst().get();
    }

    default List<M> getBestMoves(int depth, boolean max){
        List<M> list = getEvaluatedMoves(depth, max);
        return list.stream().filter(m->m.getScore() == list.stream().findFirst().get().getScore()).toList();
    }

    default List<M> getEvaluatedMoves(int depth, boolean max) {
        return getEvaluatedMoves(depth, getAllPossibleMoves(), max);
    }

    default List<M> getEvaluatedMoves(int depth, Iterable<M> moves, boolean max) {
        if (depth <= 0) throw new IllegalArgumentException("SEARCHING DEPTH MUST BE GREATER THAN 0.");
        minimax(moves, depth, max);
        return StreamSupport.stream(moves.spliterator(), false).sorted().toList();
    }

    default int minimax(@NotNull Iterable<M> moves, @NotNull int depth, boolean max) {
        if (isGameOver() || depth <= 0) return evaluate();
        int score = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(M move : moves) {
            play(move);
            score = max ? Math.max(score, minimax(getAllPossibleMoves(), depth--, !max)) : Math.min(score, minimax(getAllPossibleMoves(), depth--, !max));
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
