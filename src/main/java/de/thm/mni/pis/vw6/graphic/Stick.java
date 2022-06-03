package de.thm.mni.pis.vw6.graphic;

import de.thm.mni.pis.vw6.utils.Pair;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Stick {

    private final Pair size;
    Runnable action;
    private Pair location;
    public boolean selected = false;

    public Stick(Pair location, Pair size, Pair value) {
        this.location = location;
        this.size = size;
    }

    public void draw(PGraphics graphics) {
        graphics.fill(graphics.color(245, 222, 179, 255));
        graphics.rectMode(PConstants.CENTER);
        graphics.rect(location.x(), location.y(), size.x(), size.y());
    }

    public boolean gotClicked(int x, int y) {
        return (x <= (location.x() + size.x() / 2) && x >= (location.x() - size.x() / 2) && y <= (location.y() + size.y() / 2) && y >= location.y() - size.y() / 2);
    }

    public void handleMouseEvent(MouseEvent event) {
        if (gotClicked(event.getX(), event.getY())) {
            action.run();
        }
    }

    public void select() {
        location = selected ? new Pair((location.x()), location.y()*1.05F) : new Pair(location.x() , location.y() / 1.05F);
        selected = !selected;
    }

    public Stick setAction(Runnable action) {
        this.action = action;
        return this;
    }


    public Pair getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Stick{" + "location=" + location + ", size=" + size + '}';
    }
}
