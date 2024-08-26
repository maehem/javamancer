/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
 *
 * Portions of this software are Copyright (c) 2018 Henadzi Matuts and are
 * derived from their project: https://github.com/HenadziMatuts/Reuromancer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.maehem.javamancer.neuro.view.cyberspace;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.view.ResourceManager;
import com.maehem.javamancer.neuro.view.popup.CyberspacePopup.State;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GridBasePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;
    private static final int HORIZON = 100;

    private final ImageView cspace;
    private final ImageView[] gridLR;
    private final ImageView[] gridFB;
    private final ImageView[] dbThing;
    private final ImageView[] shotsLive;
    private final ImageView[] shotsExplode;
    private final ImageView[] iceRear;
    private final ImageView[] iceFront;
    private final ImageView[] iceVirusRear;
    private final ImageView[] iceVirusFront;
    private final ImageView[] iceVirusRotRear;
    private final ImageView[] iceVirusRotFront;
    private final ResourceManager resourceManager;
    private final ImageView gridbase;
    private final ImageView battleGrid;
    private final GridSequence gridRight;
    private final GridSequence gridLeft;
    private final GridSequence gridForward;
    private final GridSequence gridBackward;
    private final FrameSequence shotExplodeSequence;
    private final FrameSequence shotLiveSequence;
    private final FrameSequence iceRearSequence;
    private final FrameSequence iceFrontSequence;
    private final FrameSequence iceVirusRearSequence;
    private final FrameSequence iceVirusFrontSequence;
    private final ImageStack database;
    private final ImageStack gridLRPane;
    private final ImageStack gridFBPane;
    private final ImageStack shotsLivePane;
    private final ImageStack shotsExplodePane;
    private final ImageStack iceRearPane;
    private final ImageStack iceFrontPane;
    private final ImageStack iceVirusRearPane;
    private final ImageStack iceVirusFrontPane;
    private final GameState gameState;

    public enum Direction {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public GridBasePane(GameState gs) {
        this.gameState = gs;
        this.resourceManager = gs.resourceManager;
        this.cspace = new ImageView(resourceManager.getSprite("CSPACE_1"));
        this.gridbase = new ImageView(resourceManager.getSprite("GRIDBASE_1"));
        gridbase.setLayoutY(HORIZON);

        this.battleGrid = new ImageView(resourceManager.getSprite("CSDB_1"));

        this.iceRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("ICE_1")),
            new ImageView(resourceManager.getSprite("ICE_3")),
            new ImageView(resourceManager.getSprite("ICE_5")),
            new ImageView(resourceManager.getSprite("ICE_7"))};
        this.iceFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("ICE_2")),
            new ImageView(resourceManager.getSprite("ICE_4")),
            new ImageView(resourceManager.getSprite("ICE_6")),
            new ImageView(resourceManager.getSprite("ICE_8"))};
        this.iceVirusRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSICE_1")),
            new ImageView(resourceManager.getSprite("VIRUSICE_3")),
            new ImageView(resourceManager.getSprite("VIRUSICE_5")),
            new ImageView(resourceManager.getSprite("VIRUSICE_7")),
            new ImageView(resourceManager.getSprite("VIRUSICE_9")),
            new ImageView(resourceManager.getSprite("VIRUSICE_11")),
            new ImageView(resourceManager.getSprite("VIRUSICE_13")),
            new ImageView(resourceManager.getSprite("VIRUSICE_15"))};
        this.iceVirusFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSICE_2")),
            new ImageView(resourceManager.getSprite("VIRUSICE_4")),
            new ImageView(resourceManager.getSprite("VIRUSICE_6")),
            new ImageView(resourceManager.getSprite("VIRUSICE_8")),
            new ImageView(resourceManager.getSprite("VIRUSICE_10")),
            new ImageView(resourceManager.getSprite("VIRUSICE_12")),
            new ImageView(resourceManager.getSprite("VIRUSICE_14")),
            new ImageView(resourceManager.getSprite("VIRUSICE_16"))};
        this.iceVirusRotRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSROT_1")),
            new ImageView(resourceManager.getSprite("VIRUSROT_3")),
            new ImageView(resourceManager.getSprite("VIRUSROT_5")),
            new ImageView(resourceManager.getSprite("VIRUSROT_7"))};
        this.iceVirusRotFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSROT_2")),
            new ImageView(resourceManager.getSprite("VIRUSROT_4")),
            new ImageView(resourceManager.getSprite("VIRUSROT_6")),
            new ImageView(resourceManager.getSprite("VIRUSROT_8"))};
        this.dbThing = new ImageView[]{
            new ImageView(resourceManager.getSprite("DBSPR_1")), // Close
            new ImageView(resourceManager.getSprite("DBSPR_2")), // Mid
            new ImageView(resourceManager.getSprite("DBSPR_3"))};// Far
        this.shotsLive = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_1")), // Live 1
            new ImageView(resourceManager.getSprite("SHOTS_2"))};// Explode 5
        this.shotsExplode = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_3")), // Explode 1
            new ImageView(resourceManager.getSprite("SHOTS_4")), // Explode 2
            new ImageView(resourceManager.getSprite("SHOTS_5")), // Explode 3
            new ImageView(resourceManager.getSprite("SHOTS_6")), // Explode 4
            new ImageView(resourceManager.getSprite("SHOTS_7"))};// Explode 5
        this.gridLR = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_1")),
            new ImageView(resourceManager.getSprite("GRIDS_2")),
            new ImageView(resourceManager.getSprite("GRIDS_3"))};
        this.gridFB = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_4")),
            new ImageView(resourceManager.getSprite("GRIDS_5")),
            new ImageView(resourceManager.getSprite("GRIDS_6"))};

        gridLRPane = new ImageStack(0, HORIZON, this.gridLR);
        gridFBPane = new ImageStack(0, HORIZON, this.gridFB);
        shotsLivePane = new ImageStack(305, 130, this.shotsLive);
        shotsExplodePane = new ImageStack(280, 80, this.shotsExplode);
        iceRearPane = new ImageStack(206, 30, this.iceRear);
        iceFrontPane = new ImageStack(206, 62, this.iceFront);
        iceVirusRearPane = new ImageStack(396, 30, this.iceVirusRear);
        iceVirusFrontPane = new ImageStack(396, 62, this.iceVirusFront);

        database = new ImageStack(274, 20, dbThing);
        database.show(2);

        getChildren().addAll(cspace,
                gridbase, battleGrid,
                gridLRPane, gridFBPane,
                iceRearPane, iceVirusRearPane,
                database,
                iceFrontPane, iceVirusFrontPane,
                shotsLivePane, shotsExplodePane
        );

        gridRight = new GridSequence(gridLR, false, true);
        gridLeft = new GridSequence(gridLR, true, true);
        gridForward = new GridSequence(gridFB, true, false);
        gridBackward = new GridSequence(gridFB, false, false);

        shotExplodeSequence = new FrameSequence(shotsExplode, false, true);
        shotLiveSequence = new FrameSequence(shotsLive, false, true);

        iceRearSequence = new FrameSequence(iceRear, false, true);
        iceFrontSequence = new FrameSequence(iceFront, false, true);
        iceVirusRearSequence = new FrameSequence(iceVirusRear, false, true);
        iceVirusFrontSequence = new FrameSequence(iceVirusFront, false, true);

//        Platform.runLater(() -> {
//            shotLiveSequence.start();
//            shotExplodeSequence.start();
//            iceRearSequence.start();
//            iceFrontSequence.start();
//            iceVirusRearSequence.start();
//            iceVirusFrontSequence.start();
//        });

        configState(State.EXPLORE);
    }

    public final void configState(State state) {
        switch (state) {
            case EXPLORE -> {
                iceFrontPane.setVisible(false);
                iceRearPane.setVisible(false);
                iceVirusFrontPane.setVisible(false);
                iceVirusRearPane.setVisible(false);
                shotsExplodePane.setVisible(false);
                shotsLivePane.setVisible(false);
                battleGrid.setVisible(false);
            }
            case BATTLE -> {
                iceFrontPane.setVisible(true);
                iceRearPane.setVisible(true);
                iceVirusFrontPane.setVisible(true);
                iceVirusRearPane.setVisible(true);
                battleGrid.setVisible(true);
            }
        }
    }

    public final void animateTravel(Direction dir) {
        switch (dir) {
            case FORWARD -> {
                gridForward.start();
            }
            case BACKWARD -> {
                gridBackward.start();
            }
            case LEFT -> {
                gridLeft.start();
            }
            case RIGHT -> {
                gridRight.start();
            }
        }
    }

    private class FrameSequence extends AnimationTimer {

        int index = 0;
        long last = 0;
        ImageView frames[];
        private final boolean reverse;
        private final boolean repeat;

        public FrameSequence(ImageView[] frames, boolean reverse, boolean repeat) {
            this.frames = frames;
            this.reverse = reverse;
            this.repeat = repeat;
            for (ImageView frame : frames) {
                frame.setVisible(false);
            }
        }

        @Override
        public void handle(long now) {
            if (now - 100000000 > last) { // Fire - 100000000nS == 100mS
                last = now;
                for (ImageView frame : frames) {
                    frame.setVisible(false);
                }
                if (index < frames.length) {
                    frames[reverse ? frames.length - 1 - index : index].setVisible(true);
                    index++;
                    if (repeat && index >= frames.length) {
                        index = 0;
                    }
                    indexChanged(index);
                } else { // Blank everything and stop on one-shot.
                    index = 0;
                    if (!repeat) {
                        stop();
                    }
                }
            }
        }

        public void indexChanged(int index) {

        }
    }

    private class GridSequence extends FrameSequence {

        private final int GRID = 16;
        private final boolean axisX;

        public GridSequence(ImageView[] frames, boolean reverse, boolean axisX) {
            super(frames, reverse, false);
            this.axisX = axisX;
        }

        @Override
        public void indexChanged(int index) {
            DeckItem deck = gameState.usingDeck;
            int amount = GRID / 4;

            if (axisX) {
                //LOGGER.log(Level.SEVERE, "Change {0} to axis X. Reverse = {1}", new Object[]{amount, super.reverse ? "REVERSE" : "NORMAL"});
                deck.setCordX(deck.getCordX() + (super.reverse ? amount : -amount));
            } else {
                //LOGGER.log(Level.SEVERE, "Change {0} to axis Y. Reverse = {1}", new Object[]{amount, super.reverse ? "REVERSE" : "NORMAL"});
                deck.setCordY(deck.getCordY() + (super.reverse ? amount : -amount));
            }
        }

    }

    private class ImageStack extends StackPane {

        private int current = 0;

        public ImageStack(double x, double y, Node... nodes) {
            super(nodes);

            setLayoutX(x);
            setLayoutY(y);
            show(current);
        }

        public final void show(int i) {
            current = i % getChildren().size();
            for (Node n : getChildren()) {
                n.setVisible(false);
            }
            getChildren().get(current).setVisible(true);
        }

        public int next() {
            show(current + 1);
            return current;
        }

    }

}
