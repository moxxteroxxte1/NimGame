package de.thm.mni.pis.vw6.logic;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.StreamSupport;

public interface Game<M extends MoveInterface> {

    default M getBestMove(int depth) {
        return getEvaluatedMoves(depth).stream().findFirst().get();
    }

    default List<M> getBestMoves(int depth) {
        List<M> list = getEvaluatedMoves(depth);
        return list.stream().filter(m -> m.getScore() == list.stream().findFirst().get().getScore()).toList();
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
        int score = Integer.MIN_VALUE;
        for (M move : moves) {
            play(move);
            int res = -minimax(getAllPossibleMoves(), depth--);
            score = Math.max(res, score);
            move.setScore(score);
            undo(move);
        }
        return score;
    }

    Iterable<M> getAllPossibleMoves();

    boolean isGameOver();

    int evaluate();

    void play(M move);

    void undo(M move);
}
