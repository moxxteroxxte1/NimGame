package de.thm.mni.pis.vw6;

import de.thm.mni.pis.vw6.graphic.boards.Board;
import de.thm.mni.pis.vw6.graphic.boards.EndBoard;
import de.thm.mni.pis.vw6.graphic.boards.GameBoard;
import de.thm.mni.pis.vw6.graphic.boards.MenuBoard;
import de.thm.mni.pis.vw6.graphic.utils.GameState;
import de.thm.mni.pis.vw6.graphic.utils.PlayerMode;
import de.thm.mni.pis.vw6.logic.Game;
import de.thm.mni.pis.vw6.logic.NimGame;
import de.thm.mni.pis.vw6.utils.Color;
import processing.core.PApplet;

public class NimSketch2<G extends Game> extends Sketch {

    public static void main(String[] args) {
        NimSketch2<NimGame> sketch = (NimSketch2<NimGame>) new NimSketch2<>(new NimGame()).setBackgroundColor(new Color(0,0,0,1)).setWindowSize(600,600);
        PApplet.runSketch(new String[]{"Nim"}, sketch);
    }

    public NimSketch2(G game) {
        super(game);
        board = new MenuBoard(this);
    }

    @Override
    Board getNextBoard() {
        Board b;

        switch (state) {
            case MAIN_MENU -> {
                b = new GameBoard(this);
                state = GameState.IN_GAME;
                break;
            }
            case IN_GAME -> {
                b = new EndBoard(this);
                state = GameState.END;
                break;
            }
            default ->{
                state = GameState.MAIN_MENU;
                playerMode = PlayerMode.DEFAULT;
                player = 1;
                b = new MenuBoard(this);
                break;
            }
        }

        return b;
    }


}
