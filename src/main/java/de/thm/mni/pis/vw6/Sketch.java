package de.thm.mni.pis.vw6;

import de.thm.mni.pis.vw6.graphic.boards.Board;
import de.thm.mni.pis.vw6.graphic.utils.GameState;
import de.thm.mni.pis.vw6.graphic.utils.PlayerMode;
import de.thm.mni.pis.vw6.logic.Game;
import de.thm.mni.pis.vw6.utils.Color;
import de.thm.mni.pis.vw6.utils.Pair;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class Sketch<G extends Game> extends PApplet {

    G game;
    GameState state = GameState.DEFAULT;
    PlayerMode playerMode = PlayerMode.DEFAULT;
    Board board;
    int windowWidth, windowHeight, player = 1;
    boolean fullscreen = false, loop = true, hasBackgroundImage = false;
    PImage backgroundImage;
    Color backgroundColor;

    public Sketch(G game) {
        this.game = game;
    }

    public G getGame() {
        return game;
    }

    public void setGame(G game) {
        this.game = game;
    }

    public GameState getState() {
        return GameState.valueOf(state.name());
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public PlayerMode getPlayerMode() {
        return PlayerMode.valueOf(playerMode.name());
    }

    public void setPlayerMode(PlayerMode playerMode) {
        this.playerMode = playerMode;
    }

    public void settings() {
        if (fullscreen) {
            fullScreen();
        } else {
            size(windowWidth, windowHeight);
        }
    }

    public void setup() {

    }

    @Override
    public void draw() {
        if (hasBackgroundImage) {
            background(backgroundImage);
        } else {
            background(color(backgroundColor.r(), backgroundColor.g(), backgroundColor.b(), backgroundColor.alpha()));
        }

        if (board == null) throw new NullPointerException("no board found");
        next();
        board.draw(super.g);
    }

    void next() {
        if (board.isNext()) {
            this.board = getNextBoard();
        }
    }

    public void nextPlayer() {
        player = -player;
    }

    public int getPlayer() {
        return player;
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        board.handleKeyEvent(event);
    }

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        board.handleMouseEvent(event);
    }

    public Pair getWindowSize() {
        return new Pair(width, height);
    }

    public Sketch setLoop(boolean loop) {
        if (loop) {
            loop();
        } else {
            noLoop();
        }
        this.loop = loop;
        return this;
    }

    public Sketch setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }

    public Sketch setBackgorundImage(PImage image) {
        this.hasBackgroundImage = true;
        this.backgroundImage = image;
        return this;
    }

    public Sketch setBackgroundColor(Color color) {
        this.backgroundColor = color;
        this.hasBackgroundImage = false;
        return this;
    }

    public Sketch setWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        return this;
    }

    abstract Board getNextBoard();
}
