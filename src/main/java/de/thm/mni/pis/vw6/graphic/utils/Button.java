package de.thm.mni.pis.vw6.graphic.utils;

import de.thm.mni.pis.vw6.utils.Color;
import de.thm.mni.pis.vw6.utils.Pair;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Button {

    private final Pair location;
    private final Pair size;
    private String lable = "";
    private Color color = new Color(255, 255, 255, 255);
    private Color textColor = new Color(0, 0, 0, 255);
    private int radius = 0;
    private Runnable action;


    public Button(Pair location, Pair size) {
        this.location = location;
        this.size = size;
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CENTER);
        graphics.fill(color.r(), color.g(), color.b(), color.alpha());
        graphics.rect(location.x(), location.y(), size.x(), size.y(), radius);
        graphics.fill(textColor.r(), textColor.g(), textColor.b(), textColor.alpha());
        graphics.text(lable, location.x() - graphics.textWidth(lable) / 2, location.y() + graphics.textAscent() / 3);
    }

    public boolean gotClicked(int x, int y) {
        return (x <= (this.location.x() + this.size.x() / 2) && x >= (this.location.x() - this.size.x() / 2) && y <= (this.location.y() + this.size.y() / 2) && y >= (this.location.y() - this.size.y() / 2));
    }

    public Button setAction(Runnable action) {
        this.action = action;
        return this;
    }

    public void handleMouseEvent(MouseEvent event) {
        if (gotClicked(event.getX(), event.getY())) {
            this.action.run();
        }
    }

    @Override
    public String toString() {
        return "Button{" + "location=" + location + ", size=" + size + ", lable='" + lable + '\'' + ", color=" + color + ", textColor=" + textColor + ", radius=" + radius + ", action=" + action + '}';
    }

    public Button setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public Button setTextColor(Color color) {
        textColor = color;
        return this;
    }

    public Button setColor(Color color) {
        this.color = color;
        return this;
    }

    public Button setLable(String lable) {
        this.lable = lable;
        return this;
    }
}

