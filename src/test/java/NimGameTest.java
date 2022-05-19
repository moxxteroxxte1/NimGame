import de.thm.mni.pis.vw6.nimperfect.Move_Perfect;
import de.thm.mni.pis.vw6.nimperfect.Nim;
import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NimGameTest {

    final String SEPERATOR = "\n------------------------------------------";

    @Test
    @Order(0)
    @DisplayName("Generate all possible Moves")
    @SuppressWarnings(value = "unchecked")
    void getAllPossibleMoves() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "getAllPossibleMoves()");
        //GENERATE RANDOM GAME
        NimGame game = randomGame();
        System.out.println(getTime() + game);
        //GENERATE LIST OF ALL POSSIBLE MOVES
        List<Move> moves = (List<Move>) game.getAllPossibleMoves();
        System.out.println(getTime() + moves);
        //CHECK IF LENGTH OF LIST IS EQUAL TO SUM ALL POSSIBILITIES
        assertEquals(Arrays.stream(game.rows).sum(), moves.toArray().length);
        System.out.println(SEPERATOR);
    }

    @Test
    @Order(1)
    @DisplayName("Throw exception if \"Move\" parameters are out of Bounds")
    void playOutofBounds() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "playOutofBounds()");
        //GENERATE RANDOM GAME
        NimGame game = randomGame();
        //CHECK IF RIGHT EXCEPTION IS THROWN WHEN ONE PARAMETER IS OUT OF BOUNDS
        assertThrows(IllegalArgumentException.class, () -> {
            game.play(new Move(game.rows.length, 1));
        });
        System.out.println(getTime() + "PASSED FIRST");

        assertThrows(IllegalArgumentException.class, () -> {
            game.play(new Move(game.rows.length - 1, game.rows[game.rows.length - 1] + 1));
        });
        System.out.println(getTime() + "PASSED SECOND");

        System.out.println(SEPERATOR);
    }

    @Test
    @Order(2)
    @DisplayName("Play move")
    void play() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "play()");
        //GENERATE RANDOM GAME AND EXACT COPY IN REFERENCE IMPLEMENTATION
        NimGame game = randomGame();
        Nim ref = Nim.of(game.rows);

        System.out.println(getTime() + "Game: " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE RANDOM MOVE
        Move move = randomMove(game);
        Move_Perfect ref_move = Move_Perfect.of(move.getRow(), move.getNumber());

        System.out.println(getTime() + move);
        System.out.println(getTime() + "Reference Move " + ref_move);

        //PLAY MOVE IN GAME AND REFERENCE
        game.play(move);
        ref = ref.play(ref_move);

        System.out.println(getTime() + "Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //CHECK IF GAME AND REFERENCE ARE STILL IDENTICAL AFTER PLAYING
        assertArrayEquals(game.rows, ref.rows);
        System.out.println(SEPERATOR);
    }

    @Test
    @Order(3)
    @DisplayName("Undo move")
    void undo() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "undo()");
        //GENERATE RANDOM GAME AND COPY OF IT
        NimGame game = randomGame();
        NimGame ref = new NimGame(game.rows);

        System.out.println(getTime() + "Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE RANDOM MOVE
        Move move = randomMove(game);

        System.out.println(getTime() + move);

        //PLAY AND UNDO MOVE
        game.play(move);
        System.out.println(getTime() + "Move played " + game);
        game.undo(move);

        System.out.println(getTime() + "Move undone " + game);
        System.out.println(getTime() + "Referemce " + ref);

        //CHECK IF GAME AND COPY OF ITS ORIGINAL STATE ARE STILL THE SAME
        assertArrayEquals(game.rows, ref.rows);
        System.out.println(SEPERATOR);
    }

    @Test
    @Order(3)
    @DisplayName("Generate list of all moves and then evaluate")
    void testGetEvaluatedMoves() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "testGetEvaluatedMoves()");
        //GENERATE RANDOM GAME
        NimGame game = randomGame();
        System.out.println(getTime() + "Game " + game);

        //GENERATE SORTED LIST OF EVALUATED MOVES
        List<Move> m = game.getBestMoves(100, true);
        //FILTER GENERATED MOVES FOR BEST
        m.stream().filter(move -> move.getScore() == m.stream().findFirst().get().getScore());

        System.out.println(getTime() + "List of best moves " + m);
        System.out.println(SEPERATOR);
    }

    @RepeatedTest(100)
    @Order(4)
    @DisplayName("Generate list of all moves and then evaluate")
    void testGetBestMoves() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "testGetBestMoves()");
        //GENERATE RANDOM GAME AND EXACT COPY IN REFERENCE IMPLEMENTATION
        NimGame game = randomGame();
        Nim ref = Nim.of(game.rows);

        System.out.println(getTime() + "Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE SORTED LIST OF EVALUATED MOVES
        List<Move> m = game.getBestMoves(100, true);
        //FILTER GENERATED MOVES FOR BEST
        m.stream().filter(move -> move.getScore() == m.stream().findFirst().get().getScore());

        //GENERATE BEST MOVE IN REFERENCE IMPLEMENTATION
        Move_Perfect m_ref = ref.bestMove();

        System.out.println(getTime() + "List of best moves " + m);
        System.out.println(getTime() + "Reference best move" + m_ref);

        //CHECK IF LIST OF BEST MOVES CONTAINS BEST MOVE FROM REFERENCE IMPLEMENTATION
        assertTrue(m.stream().anyMatch(move -> move.getRow() == m_ref.row && move.getNumber() == m_ref.number));
        System.out.println(SEPERATOR);
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

    String getTime() {
        return ("\n" + new SimpleDateFormat().format(new Date()) + ": ");
    }
}