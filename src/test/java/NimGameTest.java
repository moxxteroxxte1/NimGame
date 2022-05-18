import de.thm.mni.pis.vw6.nimperfect.Move_Perfect;
import de.thm.mni.pis.vw6.nimperfect.Nim;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NimGameTest {

    @Test
    @DisplayName("Generate all possible Moves")
    @SuppressWarnings(value = "unchecked")
    void getAllPossibleMoves() {
        //GENERATE RANDOM GAME
        NimGame game = randomGame();

        //GENERATE LIST OF ALL POSSIBLE MOVES
        List<Move> moves = (List<Move>) game.getAllPossibleMoves();

        //CHECK IF LENGTH OF LIST IS EQUAL TO SUM ALL POSSIBILITIES
        assertEquals(Arrays.stream(game.rows).sum(), moves.toArray().length);
    }

    @Test
    @DisplayName("Throw exception if \"Move\" parameters are out of Bounds")
    void playOutofBounds() {
        //GENERATE RANDOM GAME
        NimGame game = randomGame();

        //CHECK IF RIGHT EXCEPTION IS THROWN WHEN ONE PARAMETER IS OUT OF BOUNDS
        assertThrows(IllegalArgumentException.class, () -> {
            game.play(new Move(game.rows.length, 1));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.play(new Move(game.rows.length - 1, game.rows[game.rows.length - 1] + 1));
        });
    }

    @Test
    @DisplayName("Play move")
    void play() {
        //GENERATE RANDOM GAME AND EXACT COPY IN REFERENCE IMPLEMENTATION
        NimGame game = randomGame();
        Nim ref = Nim.of(game.rows);

        //GENERATE RANDOM MOVE
        Move move = randomMove(game);
        Move_Perfect ref_move = Move_Perfect.of(move.getRow(), move.getNumber());

        //PLAY MOVE IN GAME AND REFERENCE
        game.play(move);
        ref = ref.play(ref_move);

        //CHECK IF GAME AND REFERENCE ARE STILL IDENTICAL AFTER PLAYING
        assertArrayEquals(game.rows, ref.rows);
    }

    @Test
    @DisplayName("Undo move")
    void undo() {
        //GENERATE RANDOM GAME AND COPY OF IT
        NimGame game = randomGame();
        NimGame ref = new NimGame(game.rows);

        //GENERATE RANDOM MOVE
        Move m = randomMove(game);

        //PLAY AND UNDO MOVE
        game.play(m);
        game.undo(m);

        //CHECK IF GAME AND COPY OF ITS ORIGINAL STATE ARE STILL THE SAME
        assertArrayEquals(game.rows, ref.rows);
    }

    @RepeatedTest(100)
    @DisplayName("Generate list of best moves")
    void testGetBestMoves() {
        //GENERATE RANDOM GAME AND EXACT COPY IN REFERENCE IMPLEMENTATION
        NimGame game = randomGame();
        Nim ref = Nim.of(game.rows);

        //GENERATE SORTED LIST OF EVALUATED MOVES
        List<Move> m = game.getEvaluatedMoves(100);
        //FILTER GENERATED MOVES FOR BEST
        m.stream().filter(move -> move.getScore() == m.stream().findFirst().get().getScore());

        //GENERATE BEST MOVE IN REFERENCE IMPLEMENTATION
        Move_Perfect m_ref = ref.bestMove();

        //CHECK IF LIST OF BEST MOVES CONTAINS BEST MOVE FROM REFERENCE IMPLEMENTATION
        assertTrue(m.stream().anyMatch(move -> move.getRow() == m_ref.row && move.getNumber() == m_ref.number));
    }

    NimGame randomGame() {
        //RANDOM ROW COUNT FROM 1 TO 4
        int rowsCount = (1 + new Random().nextInt(3));
        int[] rows = new int[rowsCount];
        //FILL EACH ROW WITH RANDOM NUMBER BETWEEN 1 AND 6
        for (int i = 0; i < rowsCount; i++) {
            rows[i] = (1 + new Random().nextInt(5));
        }
        return new NimGame(rows);
    }

    @SuppressWarnings(value = "unchecked")
    Move randomMove(NimGame game) {
        //GENERATE LIST OF ALL POSSIBLE MOVES
        List<Move> moves = (List<Move>) game.getAllPossibleMoves();
        //SELECT RANDOM ONE AND RETURN
        return moves.get((int) (Math.random() * moves.size()));
    }
}