package de.thm.mni.pis.vw6.graphic.boards;

import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SettingsBoard implements Board {

    protected boolean next = false;

    @Override
    public void draw(PGraphics graphics) {

    }

    @Override
    public boolean isNext() {
        return next;
    }

    @Override
    public void handleMouseEvent(MouseEvent event) {

    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }
}
