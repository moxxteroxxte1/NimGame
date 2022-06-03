package de.thm.mni.pis.vw6.graphic.boards;

import de.thm.mni.pis.vw6.utils.Color;
import de.thm.mni.pis.vw6.utils.Pair;
import de.thm.mni.pis.vw6.Sketch;
import de.thm.mni.pis.vw6.graphic.utils.Button;
import de.thm.mni.pis.vw6.graphic.utils.PlayerMode;
import de.thm.mni.pis.vw6.logic.Game;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class EndBoard implements Board {

    protected boolean next = false;
    Pair size;
    Game game;
    Sketch sketch;
    private Button button;

    public EndBoard(Sketch sketch) {
        this.size = sketch.getWindowSize();
        this.game = sketch.getGame();
        this.sketch = sketch;
        addButton();
    }

    void addButton() {
        button = new Button(new Pair(size.x() / 2, (int) (size.y() * 0.333f)), new Pair(size.x() / 3, size.y() / 4)).setLable("nochmal").setColor(new Color(61, 99, 138, 100)).setTextColor(new Color(255,255,255,255)).setRadius(25);
        button.setAction(() -> {
            next = true;
        });
    }

    @Override
    public void draw(PGraphics graphics) {
        String title = "END TITLE (NOT SET)";
        if (sketch.getPlayerMode() == PlayerMode.SINGLE_PLAYER) {
            title = sketch.getPlayer() == 1 ? "DU hast gewonnen! (juhu)" : "DU hast verloren!";
        } else {
            title = "Spieler " + sketch.getPlayer() + " hat gewonnen!";
        }

        float x = size.x() / 2;
        float y = size.y() / 4;

        graphics.rectMode(PConstants.CENTER);
        graphics.fill(255);
        graphics.textSize(32);
        graphics.text(title, x - graphics.textWidth(title) / 2, y / 2);

        button.draw(graphics);
    }

    public boolean isNext() {
        return next;
    }

    @Override
    public void handleMouseEvent(MouseEvent event) {
        if (event.getAction() == MouseEvent.CLICK) {
            button.handleMouseEvent(event);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

}

