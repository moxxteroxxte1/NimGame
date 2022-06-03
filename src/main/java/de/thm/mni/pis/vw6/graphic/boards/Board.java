package de.thm.mni.pis.vw6.graphic.boards;

import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface Board {

    void draw(PGraphics graphics);

    void handleMouseEvent(MouseEvent event);

    void handleKeyEvent(KeyEvent event);

    boolean isNext();
}
