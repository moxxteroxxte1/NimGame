package de.thm.mni.pis.vw6.graphic.boards;

import de.thm.mni.pis.vw6.utils.Color;
import de.thm.mni.pis.vw6.utils.Pair;
import de.thm.mni.pis.vw6.Sketch;
import de.thm.mni.pis.vw6.graphic.Stick;
import de.thm.mni.pis.vw6.graphic.utils.Button;
import de.thm.mni.pis.vw6.graphic.utils.PlayerMode;
import de.thm.mni.pis.vw6.logic.Move;
import de.thm.mni.pis.vw6.logic.NimGame;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class GameBoard implements Board {

    private static boolean needsUpdate = true;
    public final int SPACE_X = 25, SPACE_Y = 75;
    private final Pair windowSize;
    private final Sketch sketch;
    NimGame game;
    ArrayList<Stick> sticks;
    Button button;
    int selectedRow = -1;
    int value = 0;

    public GameBoard(Sketch sketch) {
        this.sketch = sketch;
        windowSize = sketch.getWindowSize();

        if(sketch.getGame().isGameOver() || sketch.getGame() == null) {
            NimGame game = new NimGame();
            sketch.setGame(game);
            this.game = game;
        } else {
            game = (NimGame) sketch.getGame();
        }
    }

    public void draw(PGraphics graphics) {
        if (sketch.getPlayerMode() == PlayerMode.SINGLE_PLAYER && sketch.getPlayer() == -1 && !GameBoard.needsUpdate) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.play(game.getBestMove(100));
            needsUpdate = true;
            if(!game.isGameOver()) sketch.nextPlayer();
        }

        refresh();
        if(sticks != null) sticks.forEach(stick -> stick.draw(graphics));
        button.draw(graphics);
        GameBoard.needsUpdate = false;
    }

    @Override
    public boolean isNext() {
        return game.isGameOver();
    }

    public void handleMouseEvent(MouseEvent event) {
        if (event.getAction() == MouseEvent.CLICK) {
            if(sticks != null) sticks.forEach(stick -> stick.handleMouseEvent(event));
            button.handleMouseEvent(event);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    protected void generateSticks() {
        ArrayList<Stick> sticks = new ArrayList<>();
        int[] rows = game.getRows();
        int index = 0;
        for (int row = 1; row < rows.length + 1; row++) {
            for (int number = 1; number <= rows[row - 1]; number++) {
                int finalRow = row;
                int finalIndex = index;
                sticks.add(new Stick(calcStickLocation(row, number), new Pair(SPACE_X / 2, SPACE_Y * 0.7f), new Pair(row - 1, 1)).setAction(() -> {
                    if (selectedRow == -1) selectedRow = (finalRow - 1);
                    Stick s = sticks.get(finalIndex);
                    if (selectedRow == (finalRow - 1) && !s.selected) {
                        value += 1;
                        s.select();
                    } else if (selectedRow == (finalRow - 1) && s.selected) {
                        value -= 1;
                        if (value == 0) selectedRow = -1;
                        s.select();
                    }
                }));
                index++;
            }
        }
        this.sticks = sticks;
    }

    protected Pair calcStickLocation(int row, int number) {
        int[] rows = game.getRows();
        float x = ((windowSize.x() - (rows[row - 1] * SPACE_X)) / 2) + (number * SPACE_X) - (SPACE_X / 2);
        float y = ((windowSize.y() - rows.length * SPACE_Y) / 3) + (row * SPACE_Y);
        return new Pair(x, y);
    }

    protected void generateButton() {
        Button b = new Button(new Pair(0,0), new Pair(0,0));
        if(sticks != null && !game.isGameOver()) {
            float y = (windowSize.y() + sticks.get(sticks.size() - 1).getLocation().y());
            b = new Button(new Pair(windowSize.x() / 2, y / 2), new Pair(windowSize.x() / 3, (windowSize.y() - sticks.get(sticks.size() - 1).getLocation().y()) / 3)).setLable("Zug beenden!").setColor(new Color(61, 99, 138, selectedRow > -1 && value > 0 ? 200 : 100)).setTextColor(new Color(255, 255, 255, 255)).setRadius(25).setAction(() -> {
                if (selectedRow > -1 && value > 0) {
                    game.play(new Move(selectedRow, value));
                    if (!game.isGameOver()) sketch.nextPlayer();
                    if (sketch.getPlayerMode() == PlayerMode.SINGLE_PLAYER) GameBoard.needsUpdate = true;
                    selectedRow = -1;
                    value = 0;
                }
            });
        }
        this.button = b;
    }


    protected void refresh() {
        if (needsUpdate) {
            generateSticks();
        }
        generateButton();
    }
}
