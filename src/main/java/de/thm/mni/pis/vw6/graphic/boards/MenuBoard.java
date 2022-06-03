package de.thm.mni.pis.vw6.graphic.boards;

import de.thm.mni.pis.vw6.utils.Color;
import de.thm.mni.pis.vw6.utils.Pair;
import de.thm.mni.pis.vw6.Sketch;
import de.thm.mni.pis.vw6.graphic.utils.Button;
import de.thm.mni.pis.vw6.graphic.utils.PlayerMode;
import de.thm.mni.pis.vw6.logic.NimGame;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MenuBoard implements Board {

    private Pair windowSize;
    private final String title = "NimSpiel";
    private ArrayList<Button> buttons;
    private final Sketch sketch;

    public MenuBoard(Sketch sketch) {
        this.sketch = sketch;
    }

    @Override
    public void draw(PGraphics graphics) {
        windowSize = sketch.getWindowSize();
        generateButtons();

        float x = windowSize.x() / 2;
        float y = windowSize.y() / 4;

        graphics.rectMode(PConstants.CENTER);
        graphics.fill(255);
        graphics.textSize(32);
        graphics.text(title, x - graphics.textWidth(title) / 2, y / 2);

        buttons.forEach(button -> button.draw(graphics));
    }

    @Override
    public boolean isNext() {
        return sketch.getPlayerMode() != PlayerMode.DEFAULT;
    }

    @Override
    public void handleMouseEvent(MouseEvent event) {
        if (event.getAction() == MouseEvent.CLICK) {
            buttons.forEach(button -> button.handleMouseEvent(event));
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    protected void generateButtons() {
        ArrayList<Button> buttons = new ArrayList<Button>();
        //SINGLE PLAYER BUTTON
        Button b = new Button(calcButtonLocation(1), new Pair(windowSize.x() / 3, windowSize.y() / 4)).setLable("Einzelspieler").setColor(new Color(61, 99, 138, 100)).setTextColor(new Color(255,255,255,255)).setRadius(10);
        b.setAction(() -> {
            sketch.setPlayerMode(PlayerMode.SINGLE_PLAYER);
        });
        buttons.add(b);

        //MULTI PLAYER BUTTON
        b = new Button(calcButtonLocation(2), new Pair(windowSize.x() / 3, windowSize.y() / 4)).setLable("Mehrspieler").setColor(new Color(61, 99, 138, 100)).setTextColor(new Color(255,255,255,255)).setRadius(10);
        b.setAction(() -> {
            sketch.setPlayerMode(PlayerMode.MULTI_PLAYER);
        });
        buttons.add(b);

        this.buttons = buttons;
    }

    protected Pair calcButtonLocation(float number) {
        return new Pair(windowSize.x() / 2, (int) (windowSize.y() * (number / 3)));
    }
}
