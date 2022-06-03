import de.thm.mni.pis.vw6.logic.Move;
import de.thm.mni.pis.vw6.logic.NimGame;
import de.thm.mni.pis.vw6.nimperfect.Move_Perfect;
import de.thm.mni.pis.vw6.nimperfect.Nim_PerfectPerfect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NimGamePerfectTest {

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
        if (Arrays.stream(game.getRows()).sum() != moves.toArray().length) {
            fail(getTime() + "Not all possible moves were found.");
        }
        System.out.println(SEPERATOR);
    }

    @Test
    @Order(1)
    @DisplayName("Throw exception if \"de.thm.mni.pis.vw6.logic.Move\" parameters are out of Bounds")
    void playOutofBounds() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "playOutofBounds()");
        //GENERATE RANDOM GAME
        NimGame game = randomGame();
        //CHECK IF RIGHT EXCEPTION IS THROWN WHEN ONE PARAMETER IS OUT OF BOUNDS
        try {
            game.play(new Move(game.getRows().length, 1));
            fail(getTime() + "No exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println(getTime() + "Right exception thrown (1)");
        } catch (Exception e) {
            fail(getTime() + e.getMessage());
        }

        try {
            game.play(new Move(game.getRows().length - 1, game.getRows()[game.getRows().length - 1] + 1));
            fail(getTime() + "No exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println(getTime() + "Right exception thrown (2)");
        } catch (Exception e) {
            fail(getTime() + e.getMessage());
        }

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
        Nim_PerfectPerfect ref = Nim_PerfectPerfect.of(game.getRows());

        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game: " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE RANDOM MOVE
        Move move = randomMove(game);
        Move_Perfect ref_move = Move_Perfect.of(move.getRow(), move.getNumber());

        System.out.println(getTime() + move);
        System.out.println(getTime() + "Reference de.thm.mni.pis.vw6.logic.Move " + ref_move);

        //PLAY MOVE IN GAME AND REFERENCE
        game.play(move);
        ref = ref.play(ref_move);

        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //CHECK IF GAME AND REFERENCE ARE STILL IDENTICAL AFTER PLAYING
        if (!Arrays.equals(game.getRows(), ref.rows))
            fail(getTime() + "de.thm.mni.pis.vw6.logic.Game: " + Arrays.toString(game.getRows()) + "\nReference: " + Arrays.toString(ref.rows));
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
        NimGame ref = new NimGame(game.getRows());

        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE RANDOM MOVE
        Move move = randomMove(game);

        System.out.println(getTime() + move);

        //PLAY AND UNDO MOVE
        game.play(move);
        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Move played " + game);
        game.undo(move);

        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Move undone " + game);
        System.out.println(getTime() + "Referemce " + ref);

        //CHECK IF GAME AND COPY OF ITS ORIGINAL STATE ARE STILL THE SAME
        assertArrayEquals(game.getRows(), ref.getRows());
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
        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game " + game);

        //GENERATE SORTED LIST OF EVALUATED MOVES
        List<Move> m = game.getBestMoves(100);
        //FILTER GENERATED MOVES FOR BEST
        m.stream().filter(move -> move.getScore() == m.stream().findFirst().get().getScore());

        System.out.println(getTime() + "List of best moves " + m);
        System.out.println(SEPERATOR);
    }

    @Test
    @Order(4)
    @DisplayName("Is game over when all numbers are 0")
    void testIsGameOver() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "testIsGameOver()");
        NimGame game = new NimGame(1);
        game.play(new Move(0, 1));
        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game " + game);
        System.out.println(getTime() + "isGameOver: " + game.isGameOver());
        assertTrue(game.isGameOver());
        System.out.println(SEPERATOR);
    }

    @RepeatedTest(100)
    @Order(5)
    @DisplayName("Generate list of all moves and then evaluate")
    void testGetBestMoves() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "testGetBestMoves()");
        //GENERATE RANDOM GAME AND EXACT COPY IN REFERENCE IMPLEMENTATION
        NimGame game = randomGame();
        Nim_PerfectPerfect ref = Nim_PerfectPerfect.of(game.getRows());

        System.out.println(getTime() + "de.thm.mni.pis.vw6.logic.Game " + game);
        System.out.println(getTime() + "Reference " + ref);

        //GENERATE SORTED LIST OF EVALUATED MOVES
        List<Move> m = game.getBestMoves(100);

        //GENERATE BEST MOVE IN REFERENCE IMPLEMENTATION
        Move_Perfect m_ref = ref.bestMove();

        System.out.println(getTime() + "List of best moves " + m);
        System.out.println(getTime() + "Reference best move" + m_ref);

        //CHECK IF LIST OF BEST MOVES CONTAINS BEST MOVE FROM REFERENCE IMPLEMENTATION
        assertTrue(m.stream().anyMatch(move -> move.getRow() == m_ref.row && move.getNumber() == m_ref.number));
        System.out.println(SEPERATOR);
    }

    @RepeatedTest(1)
    @DisplayName("Play against reference implementation")
    void testMinimax() {
        System.out.println(SEPERATOR);
        System.out.println(getTime() + "testGetBestMoves()");

        NimGame game = randomGame();
        Nim_PerfectPerfect nim = Nim_PerfectPerfect.of(game.getRows());

        boolean isMiniMaxWinning = game.isWinning();
        boolean isMiniMax = true;

        System.out.println(getTime() + (isMiniMaxWinning ? "MiniMax" : "Reference") + " should win");
        System.out.println(getTime() + game);

        while (!game.isGameOver()) {
            if (isMiniMax) {
                Move move = game.getBestMove(100);
                game.play(move);
                nim = nim.play(Move_Perfect.of(move.getRow(), move.getNumber()));
                System.out.println(getTime() + move);
            } else {
                Move_Perfect move_perfect = nim.bestMove();
                nim = nim.play(move_perfect);
                game.play(new Move(move_perfect.row, move_perfect.number));
                System.out.println(getTime() + move_perfect);
            }
            isMiniMax = !isMiniMax;
        }

        if (isMiniMaxWinning && isMiniMax) {
            fail(getTime() + "MiniMax should have won, but lost");
        }

        System.out.println(getTime() + (isMiniMax ? "Reference" : "MiniMax") + " has won");
        System.out.println(SEPERATOR);
    }

    NimGame randomGame() {
        //RANDOM ROW COUNT FROM 1 TO 4
        int rowsCount = (1 + new Random().nextInt(4));
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